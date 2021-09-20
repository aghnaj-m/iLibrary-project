package bibliotheque.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import bibliotheque.dao.LivreDao;
import bibliotheque.model.Livre;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 * This class render a list of books
 * in the form of a big scrollpane contains multiple panels represent the books
 */

public class BookList {

	private JFrame frame;
	private JPanel list;
	protected LivreDao livreDao;
	protected Librairie parent;
	private int option;
	private JPanel container;
	private JScrollPane scrollPane;
	private JTextField txtSomething;
	private List<Livre> bookList;


	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public BookList(int option,Librairie parent) {
		this.parent = parent;
		livreDao = new LivreDao();
		this.option = option;
		bookList = livreDao.getAll();
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
		
		JLabel backButton = new JLabel("<html><body><a><img width=\"30\" height=\"20\" src=\""+DetailLivre.class.getResource("/DetailLivre/backButton.png")+"\" /></a></body></html>");
		backButton.setBounds(119, 6, 32, 22);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				parent.getFrame().setVisible(true);
			}
		});
		container.add(backButton);
		
		JLabel forwardButton = new JLabel("<html><body><img width=\"30\" height=\"20\" src=\""+DetailLivre.class.getResource("/DetailLivre/forwardButton.png")+"\"/></body></html>");
		forwardButton.setBounds(152, 6, 32, 22);
		container.add(forwardButton);
		
		JButton btnMyLibrairie = new JButton("My Librairie");
		btnMyLibrairie.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		btnMyLibrairie.setBounds(16, 6, 91, 29);
		btnMyLibrairie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MonEspace mySpace = new MonEspace(parent, parent.user.getCne());
				mySpace.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		container.add(btnMyLibrairie);
		
		JLabel lblTousNosLivres = new JLabel("");
		switch(option)
		{
			case 1:
				lblTousNosLivres.setText("Tous nos livres");
				break;
			case 2:
				lblTousNosLivres.setText("Les plus empruntés du moment");
				break;
			case 3:
				lblTousNosLivres.setText("Nouveauté de cette anneé");
				break;
		}
		
		lblTousNosLivres.setForeground(Color.DARK_GRAY);
		lblTousNosLivres.setFont(new Font("AppleGothic", Font.PLAIN, 20));
		lblTousNosLivres.setBackground(Color.WHITE);
		lblTousNosLivres.setBounds(31, 40, 401, 34);
		container.add(lblTousNosLivres);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 76, 969, 12);
		container.add(separator);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		comboBox.setForeground(SystemColor.scrollbar);
		switch(option)
		{
			case 1:
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tous"}));
				break;
			case 2:
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Meilleurs"}));
				break;
			case 3:
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nouveauté"}));
				break;
		}
		
		comboBox.setBounds(32, 89, 100, 27);
		container.add(comboBox);
		
		list = new JPanel();
		list.setForeground(Color.DARK_GRAY);
		list.setBounds(32, 118, 969, 1400);
		list.setBorder(null);
		list.setBackground(Color.WHITE);
		FlowLayout fl_contenu = new FlowLayout(FlowLayout.LEFT);
		fl_contenu.setHgap(14);
        list.setLayout(fl_contenu);
		list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		container.add(list);
		
		txtSomething = new JTextField();
		txtSomething.setForeground(Color.DARK_GRAY);
		txtSomething.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		txtSomething.setText("cherche livre/auteur");
		txtSomething.setBorder(null);
		txtSomething.setBounds(782, 32, 175, 34);
		txtSomething.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				txtSomething.setText("");
			}
		});
		container.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				txtSomething.setText("cherche livre/auteur");				
			}
		});
		
		txtSomething.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				
				bookList.clear();
				list.removeAll();
				chercheLivre(txtSomething.getText());
				list.revalidate();
				list.repaint();
			}
		});
		container.add(txtSomething);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(BookList.class.getResource("/LivresImages/search.png")));
		lblNewLabel.setBounds(763, 27, 230, 47);
		container.add(lblNewLabel);
		
		getLivresList();
		
	}
	
	/**
	 * Adding all books panel into the container panel(list)
	 */
	public void getLivresList()
	{
		
		for(int i = 0; i < bookList.size(); i++)
		{
			int indexLivre = i;
			if(i % 6 == 0)
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
		
			JLabel cover = new JLabel("<html><body><img width=\"135\" height=\"205\" src=\""+Librairie.class.getResource(bookList.get(i).getCover_imageUrl())+"\"/></body></html>");
			cover.setBounds(5, 5, 135, 205);
			cover.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					DetailLivre details = new DetailLivre(bookList.get(indexLivre), BookList.this);
	        		details.visible();
					frame.setVisible(false);
				}
			});
			panel.add(cover);
        
			JLabel titreLabel = new JLabel(bookList.get(i).getTitre());
			titreLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 14));
			titreLabel.setBounds(17, 215, 110, 18);
			titreLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					DetailLivre details = new DetailLivre(bookList.get(indexLivre), BookList.this);
					details.visible();
					frame.setVisible(false);
				}
			});
			panel.add(titreLabel);
			
			JLabel auteurLabel = new JLabel(bookList.get(i).getAuteur());
			auteurLabel.setForeground(Color.LIGHT_GRAY);
			auteurLabel.setFont(new Font("Arial", Font.PLAIN, 11));
			auteurLabel.setBounds(30, 233, 100, 13);
			panel.add(auteurLabel);
			JLabel etatLabel = new JLabel("");
			
			int etat = livreDao.nbrDispoExemplaire(bookList.get(indexLivre).getIsbn()+"");
			if(etat > 0)
			{
				etatLabel.setText("EN STOCK");
				etatLabel.setForeground(new Color(34,146,75));
			}else {
				etatLabel.setText("EPUISÉ");
				etatLabel.setForeground(new Color(210,56,52));
			}
			
			etatLabel.setFont(new Font("AppleMyungjo", Font.PLAIN, 10));
			etatLabel.setBounds(43, 251, 57, 16);
			panel.add(etatLabel);
        
			list.add(panel);
        
		}
	}
	
	/**
	 * Looking for a book by its author or its title
	 */
	public void chercheLivre(String keyword)
	{
		
		try {
			/**
			 * if the search field is empty we call for all book if not we search by the keyword entered
			 */
			if(!keyword.equals(""))
				bookList = livreDao.chercheLivre(keyword);
			else
				bookList =	livreDao.getAll();
			for(int i = 0; i < bookList.size(); i++)
			{
				int indexLivre = i;
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setPreferredSize(new Dimension(145,273));
				panel.setLayout(null);
				panel.setBackground(Color.white);
			
				JLabel cover = new JLabel("<html><body><img width=\"135\" height=\"205\" src=\""+Librairie.class.getResource(bookList.get(i).getCover_imageUrl())+"\"/></body></html>");
				cover.setBounds(5, 5, 135, 205);
				cover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						DetailLivre details = new DetailLivre(bookList.get(indexLivre), BookList.this);
		        		details.visible();
						frame.setVisible(false);
					}
				});
				panel.add(cover);
	        
				JLabel titreLabel = new JLabel(bookList.get(i).getTitre());
				titreLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 14));
				titreLabel.setBounds(17, 215, 110, 18);
				titreLabel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						DetailLivre details = new DetailLivre(bookList.get(indexLivre), BookList.this);
						details.visible();
						frame.setVisible(false);
					}
				});
				panel.add(titreLabel);
				
				JLabel auteurLabel = new JLabel(bookList.get(i).getAuteur());
				auteurLabel.setForeground(Color.LIGHT_GRAY);
				auteurLabel.setFont(new Font("Arial", Font.PLAIN, 11));
				auteurLabel.setBounds(30, 233, 100, 13);
				panel.add(auteurLabel);
				JLabel etatLabel = new JLabel("");
				
				int etat = livreDao.nbrDispoExemplaire(bookList.get(indexLivre).getIsbn()+"");
				if(etat > 0)
				{
					etatLabel.setText("EN STOCK");
					etatLabel.setForeground(new Color(34,146,75));
				}else {
					etatLabel.setText("EPUISÉ");
					etatLabel.setForeground(new Color(210,56,52));
				}
				
				etatLabel.setFont(new Font("AppleMyungjo", Font.PLAIN, 10));
				etatLabel.setBounds(43, 251, 57, 16);
				panel.add(etatLabel);
	        
				list.add(panel);
	        
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	/**
	 * Access the frame from outside the class
	 */
	public JFrame getFrame()
	{
		return this.frame;
	}
}
