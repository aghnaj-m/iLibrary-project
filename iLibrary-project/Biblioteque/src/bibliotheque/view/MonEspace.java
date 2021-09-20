package bibliotheque.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import bibliotheque.dao.EmpruntDao;
import bibliotheque.dao.LivreDao;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Livre;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;

public class MonEspace {

	private JFrame frame;
	private JPanel list;
	protected LivreDao livreDao;
	protected EmpruntDao empruntDao;
	private Librairie parent;
	private JPanel container;
	private JScrollPane scrollPane;
	private JTextField txtSomething;
	private List<Livre> bookList;
	private List<Emprunt> empruntesList;

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	
	/**
	 * Takes in parameter the parent frame and the user's (cne) so we can get his borrowed books 
	 */
	public MonEspace(Librairie parent,String cne) {
		this.parent = parent;
		livreDao = new LivreDao();
		empruntDao = new EmpruntDao(livreDao);
		bookList = new ArrayList<Livre>();
		empruntesList = empruntDao.userEmpruntes(cne);
		for(Emprunt e : empruntesList)
		{
			bookList.add(e.getEmprunt());
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		frame.setBounds((int) (width/8)-30, 0, 1044, 695);
		//frame.setBounds(125, 0, 1044, 695);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1044, 695);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);
		
		container = new JPanel();
		container.setBorder(null);
		container.setBackground(Color.WHITE);
		container.setLayout(null);
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		container.setPreferredSize(new Dimension(1044,400));
		scrollPane.setViewportView(container);
		
		JButton btnMyLibrairie = new JButton("iLivres store");
		btnMyLibrairie.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		btnMyLibrairie.setBounds(16, 6, 97, 29);
		btnMyLibrairie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		container.add(btnMyLibrairie);
		
		JLabel lblTousNosLivres = new JLabel("Mes Livres");
		
		lblTousNosLivres.setForeground(Color.DARK_GRAY);
		lblTousNosLivres.setFont(new Font("AppleGothic", Font.PLAIN, 20));
		lblTousNosLivres.setBackground(Color.WHITE);
		lblTousNosLivres.setBounds(31, 40, 401, 34);
		container.add(lblTousNosLivres);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 76, 969, 12);
		container.add(separator);
		
		
		
		list = new JPanel();
		list.setForeground(Color.DARK_GRAY);
		list.setBounds(5, 118, 1030, 1400);
		list.setBorder(null);
		list.setBackground(Color.WHITE);
		FlowLayout fl_contenu = new FlowLayout(FlowLayout.LEFT);
		fl_contenu.setHgap(50);
        list.setLayout(fl_contenu);
		list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		container.add(list);
		
		getLivresList();
		
		
		
	}
	
	/**
	 * Lists the borrowed books by the connected user
	 */
	public void getLivresList()
	{
		
		for(int i = 0; i < empruntesList.size(); i++)
		{
			int indexLivre = i;
			/**
			 * For making sure to have 5 books per line
			 * after every 5 books listed we add 250px to the panel container's height
			 */
			if(i % 5 == 0)
			{
				int containerWidth = scrollPane.getPreferredSize().width;
				int containerHeight = scrollPane.getPreferredSize().height;
				
				container.setPreferredSize(new Dimension(containerWidth,containerHeight+250));
				container.revalidate();
			}
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setPreferredSize(new Dimension(145,273));
			panel.setLayout(null);
			panel.setBackground(Color.white);
		
			JLabel cover = new JLabel("<html><body><img width=\"135\" height=\"205\" src=\""+Librairie.class.getResource(empruntesList.get(i).getEmprunt().getCover_imageUrl())+"\"/></body></html>");
			cover.setBounds(5, 5, 135, 205);
			
			panel.add(cover);
			
			JLabel titreLabel = new JLabel(empruntesList.get(i).getEmprunt().getTitre());
			titreLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 14));
			titreLabel.setBounds(17, 215, 110, 18);
			
			panel.add(titreLabel);
			
			
			long resultat = empruntesList.get(i).getDate_retour().getTime() - new Date().getTime();
			long days = resultat/86400000;
		    //System.out.println(resultat/86400000+" jours");
		    JLabel etatLabel = new JLabel("");
		    if(days > 0) {
		    	etatLabel.setText("Il vous reste "+days+" jours");
		    	etatLabel.setBounds(17, 233, 115, 13);
				etatLabel.setForeground(new Color(34,146,75));
		    }
		    else if(days == 0) {
		    	etatLabel.setText("À rendre aujourd'hui");
		    	etatLabel.setBounds(16, 233, 115, 13);
		    	etatLabel.setForeground(new Color(210,56,52));
		    }
		    else {
		    	etatLabel.setText("Vous êtes "+Math.abs(days)+" jours en retard");
		    	etatLabel.setForeground(new Color(210,56,52));
		    	etatLabel.setBounds(5, 233, 130, 13);

		    }
			
			etatLabel.setFont(new Font("AppleMyungjo", Font.PLAIN, 10));
			panel.add(etatLabel);
			        
			list.add(panel);
        
		}
	}
	
	/**
	 * Access frame from outside
	 */
	public JFrame getFrame()
	{
		return this.frame;
	}
}
