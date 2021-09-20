package bibliotheque.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AppPackage.AnimationClass;
import bibliotheque.dao.LivreDao;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Librairie {
	
	/**
	 * Swing components declarations
	 */
	
	protected Utilisateur user;
	protected LivreDao livreDao;
	
	private JFrame frame;
	private JPanel container;
	private AnimationClass AC = new AnimationClass();
	private JLabel labelbg2;
	private JLabel labelbg1;
	private JLabel lblMeilleurLabel;
	private JPanel topEmpruntedPanel;
	private JLabel auteurLabel;
	private final JScrollPane scrollPane = new JScrollPane();
	private JLabel lblNouveautDuMois;
	private JScrollPane scrollPane_2;
	private JPanel nouveautePanel;
	private JLabel seeAllLivresLabel;
	private JLabel seeAllNewsLabel;
	private JLabel seeAllMeilleurLabel;
	private JLabel lblAllLivres;
	private JScrollPane scrollPane_0;
	private JPanel allLivresPanel;

	/**
	 * Create the application.
	 */
	public Librairie(Utilisateur user) {
		 livreDao = new LivreDao();
		 this.user = user;
		 initialize();
		showSlider();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.WHITE);
		frame.setTitle("Bibliotheque Scolaire");
		frame.getContentPane().setBackground(Color.WHITE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		frame.setBounds((int) (width/8)-30, 0, 1044, 695);
		//frame.setBounds(125, 0, 1044, 695);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		scrollPane.setViewportBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBounds(0, 0, 1044, 695);
		frame.getContentPane().add(scrollPane);
		
		container = new JPanel(); 
		container.setBorder(null);
		container.setBackground(Color.WHITE);
		container.setLayout(null);
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		container.setPreferredSize(new Dimension(1044,1500));
		scrollPane.setViewportView(container);
		/**
		 * Animated Slider
		 */
		JPanel sliderPanel = new JPanel();
		sliderPanel.setBackground(Color.WHITE);
		sliderPanel.setLayout(null);
		sliderPanel.setBounds(0, 0, 1044, 246);
		container.add(sliderPanel);
		
		labelbg1 = new JLabel("");
		labelbg1.setBounds(0, 0, 1044, 246);
		labelbg1.setIcon(new ImageIcon(Librairie.class.getResource("/livresImages/bg1.png")));
		sliderPanel.add(labelbg1);
		
		/**
		 * All available library books field
		 */
		 labelbg2 = new JLabel("");
			labelbg2.setBounds(1044, 0, 1044, 246);
			labelbg2.setIcon(new ImageIcon(Librairie.class.getResource("/livresImages/bg2.png")));
			sliderPanel.add(labelbg2);
			
			
			lblAllLivres = new JLabel("Tous nos livres");
			lblAllLivres.setForeground(new Color(75, 75, 75));
			lblAllLivres.setFont(new Font("AppleGothic", Font.PLAIN, 16));
			lblAllLivres.setBounds(27, 282, 246, 28);
			container.add(lblAllLivres);
			
			
			 seeAllLivresLabel = new JLabel("<html><body><a href=\"#\"style=\"color: rbg(164,164,168);\">See All <span style=\"font-size: 12\">></span></a></body></html>");
			 seeAllLivresLabel.setFont(new Font("AppleGothic", Font.PLAIN, 12));
			 seeAllLivresLabel.setBounds(952, 282, 61, 16);
			 seeAllLivresLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						BookList bookList = new BookList(1, Librairie.this);
						bookList.getFrame().setVisible(true);
						frame.setVisible(false);
						
					}
				});
			container.add(seeAllLivresLabel);
			
			scrollPane_0 = new JScrollPane();
			scrollPane_0.setBounds(27, 315, 950, 257);
			scrollPane_0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane_0.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane_0.setBorder(null);
			container.add(scrollPane_0);
			
			allLivresPanel = new JPanel(); // contient les parking resultants
			allLivresPanel.setBorder(null);
			allLivresPanel.setBackground(Color.WHITE);
			FlowLayout fl_All_livre = new FlowLayout(FlowLayout.LEFT);
			allLivresPanel.setLayout(fl_All_livre);
			allLivresPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			allLivresPanel.setPreferredSize(new Dimension(2010,245));
			scrollPane_0.setViewportView(allLivresPanel);

			addAllLivres();
			
			/**
			 * Trendy library books field
			 */			
			lblMeilleurLabel = new JLabel("Les plus empruntés  du moment\n");
			lblMeilleurLabel.setForeground(new Color(75,75,75));
			lblMeilleurLabel.setFont(new Font("AppleGothic", Font.PLAIN, 16));
			lblMeilleurLabel.setBounds(27, 582, 246, 28);
			container.add(lblMeilleurLabel);	
			
			seeAllMeilleurLabel = new JLabel("<html><body><a href=\"#\"style=\"color: rbg(164,164,168);\">See All <span style=\"font-size: 12\">></span></a></body></html>");
			seeAllMeilleurLabel.setFont(new Font("AppleGothic", Font.PLAIN, 12));
			seeAllMeilleurLabel.setBounds(952, 588, 61, 16);
			seeAllMeilleurLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					BookList bookList = new BookList(2, Librairie.this);
					bookList.getFrame().setVisible(true);
					frame.setVisible(false);
					
				}
			});
			container.add(seeAllMeilleurLabel);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBorder(null);
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane_1.setBounds(27, 618, 950, 257);
			container.add(scrollPane_1);
			
			topEmpruntedPanel = new JPanel(); 
			topEmpruntedPanel.setBorder(null);
			topEmpruntedPanel.setBackground(Color.WHITE);
			FlowLayout fl_contenu = new FlowLayout(FlowLayout.LEFT);
	        topEmpruntedPanel.setLayout(fl_contenu);
			topEmpruntedPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			topEmpruntedPanel.setPreferredSize(new Dimension(2010,245));
			scrollPane_1.setViewportView(topEmpruntedPanel);
			
			addMeilleursLivres();		

			/**
			 * Newest library books field
			 */
			lblNouveautDuMois = new JLabel("Nouveauté de cette année");
			lblNouveautDuMois.setForeground(new Color(75, 75, 75));
			lblNouveautDuMois.setFont(new Font("AppleGothic", Font.PLAIN, 16));
			lblNouveautDuMois.setBounds(27, 880, 246, 28);
			container.add(lblNouveautDuMois);
			
			seeAllNewsLabel = new JLabel("<html><body><a href=\"#\"style=\"color: rbg(164,164,168);\">See All <span style=\"font-size: 12\">></span></a></body></html>");
			seeAllNewsLabel.setFont(new Font("AppleGothic", Font.PLAIN, 12));
			seeAllNewsLabel.setBounds(952, 895, 61, 16);
			seeAllNewsLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					BookList bookList = new BookList(3, Librairie.this);
					bookList.getFrame().setVisible(true);
					frame.setVisible(false);
				}
			});
			container.add(seeAllNewsLabel);
			
			
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(27, 920, 950, 257);
			scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane_2.setBorder(null);
			container.add(scrollPane_2);
			
			nouveautePanel = new JPanel(); // contient les parking resultants
			nouveautePanel.setBorder(null);
			nouveautePanel.setBackground(Color.WHITE);
			FlowLayout fl_nouveaute = new FlowLayout(FlowLayout.LEFT);
			nouveautePanel.setLayout(fl_nouveaute);
			nouveautePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			nouveautePanel.setPreferredSize(new Dimension(2010,245));
			scrollPane_2.setViewportView(nouveautePanel);

			addNouveauxLivres();
			
			
		
	}
	
	/**
	 * Call for all books in database
	 */
	private void addAllLivres()
	{
		List<Livre> bookList = livreDao.getAll();
		for(int i = 10; i >= 0; i--)
        {
        	int indexLivre = i;
        	JPanel allPanel;
        	allPanel = new JPanel();
        	allPanel.setBackground(Color.WHITE);
        	allPanel.setBorder(null);
        	allPanel.setPreferredSize(new Dimension(155, 254));
        	allPanel.setLayout(null);
	        
	        JLabel cover = new JLabel("<html><body><img width=\"138\" height=\"186\" src=\""+Librairie.class.getResource(bookList.get(i).getCover_imageUrl())+"\"/></body></html>");
	        cover.setBounds(0, 0, 142, 186);
	        cover.addMouseListener(new MouseAdapter() {
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(bookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        
	        allPanel.add(cover);
	        
	        JLabel titreLabel = new JLabel(bookList.get(i).getTitre());
	        titreLabel.setBounds(0, 187, 142, 29);
	        titreLabel.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(bookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        allPanel.add(titreLabel);
	        
	        JLabel auteurLabel = new JLabel(bookList.get(i).getAuteur());
	        auteurLabel.setForeground(Color.GRAY);
	        auteurLabel.setFont(new Font("Arial", Font.PLAIN, 11));
	        auteurLabel.setBounds(0, 215, 142, 16);
	        allPanel.add(auteurLabel);
	        
	        allLivresPanel.add(allPanel);
        }
	}
	
	/**
	 * Call for trendy books
	 */
	private void addMeilleursLivres()
	{
		 List<Livre> bookList = livreDao.getAll();
		 
		for(int i = 14; i >= 4; i--) 
        {
        	int indexLivre = i;
        	JPanel livrePanel;
        	livrePanel = new JPanel();
        	livrePanel.setBackground(Color.WHITE);
        	livrePanel.setBorder(null);
        	livrePanel.setPreferredSize(new Dimension(155, 254));
	        livrePanel.setLayout(null);
	        
	        JLabel cover = new JLabel("<html><body><img width=\"138\" height=\"186\" src=\""+Librairie.class.getResource(bookList.get(i).getCover_imageUrl())+"\"/></body></html>");
	        cover.setBounds(0, 0, 142, 186);
	        cover.addMouseListener(new MouseAdapter() {
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(bookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        
	        livrePanel.add(cover);
	        
	        JLabel titreLabel = new JLabel(bookList.get(i).getTitre());
	        titreLabel.setBounds(0, 187, 142, 29);
	        titreLabel.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(bookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        livrePanel.add(titreLabel);
	        
	        auteurLabel = new JLabel(bookList.get(i).getAuteur());
	        auteurLabel.setForeground(Color.GRAY);
	        auteurLabel.setFont(new Font("Arial", Font.PLAIN, 11));
	        auteurLabel.setBounds(0, 215, 142, 16);
	        livrePanel.add(auteurLabel);
	        
	        topEmpruntedPanel.add(livrePanel);
        }
	}
	
	/**
	 * Call for the newest books
	 */
	private void addNouveauxLivres()
	{
		 List<Livre> nvBookList = livreDao.getAll();
		 
		 for(int i = 0; i < 10; i++)
        {
        	int indexLivre = i;
        	JPanel nvLivrePanel;
        	nvLivrePanel = new JPanel();
        	nvLivrePanel.setBackground(Color.WHITE);
        	nvLivrePanel.setBorder(null);
        	nvLivrePanel.setPreferredSize(new Dimension(155, 254));
        	nvLivrePanel.setLayout(null);
	        JLabel cover = new JLabel("<html><body><img width=\"138\" height=\"186\" src=\""+Librairie.class.getResource(nvBookList.get(i).getCover_imageUrl())+"\"/></body></html>");
	        cover.setBounds(0, 0, 142, 186);
	        cover.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(nvBookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        nvLivrePanel.add(cover);
	        
	        JLabel titreLabel = new JLabel(nvBookList.get(i).getTitre());
	        titreLabel.setBounds(0, 187, 142, 29);
	        titreLabel.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		DetailLivre details = new DetailLivre(nvBookList.get(indexLivre), Librairie.this);
	        		details.visible();
	        		frame.setVisible(false);
	        	}
			});
	        nvLivrePanel.add(titreLabel);
	        
	        JLabel auteurLabel = new JLabel(nvBookList.get(i).getAuteur());
	        auteurLabel.setForeground(Color.GRAY);
	        auteurLabel.setFont(new Font("Arial", Font.PLAIN, 11));
	        auteurLabel.setBounds(0, 215, 142, 16);
	        nvLivrePanel.add(auteurLabel);
	        
			nouveautePanel.add(nvLivrePanel);
        }
	}
	
	/**
	 * Animated Slider
	 */
	public void showSlider()
	{
		new Thread()
		{
			// contains 0 by default
			int count;
			@Override
			public void run() {
				try {
					/**
					 * Its an infinite loop so the images slides non stop
					 */
						while(true)
						{
							switch(count)
							{
							/**
							 * Slide animation took 3s to change the image
							 */
								case 0:
									ImageIcon II = new ImageIcon(Librairie.class.getResource("/livresImages/bg2.png"));
									labelbg1.setIcon(II);
									
									Thread.sleep(3000);
									
									AC.jLabelXLeft(0, -1044, 20, 9,labelbg1);
									AC.jLabelXLeft(1044, 0, 20, 9, labelbg2);
									count = 1;
									break;
								case 1:
									ImageIcon II2 = new ImageIcon(Librairie.class.getResource("/livresImages/bg1.png"));
									labelbg2.setIcon(II2);
									
									Thread.sleep(3000);
									
									AC.jLabelXRight(-1044, 0, 20, 9,labelbg1);
									AC.jLabelXRight(0, 1044, 20, 9, labelbg2);
									count = 2;
									break;
								case 2:
									ImageIcon II3 = new ImageIcon(Librairie.class.getResource("/livresImages/bg3.png"));
									labelbg1.setIcon(II3);
									
									Thread.sleep(3000);
									
									AC.jLabelXLeft(0, -1044, 20, 9,labelbg1);
									AC.jLabelXLeft(1044, 0, 20, 9, labelbg2);
									count = 3;
									break;
								case 3:
									ImageIcon II4 = new ImageIcon(Librairie.class.getResource("/livresImages/bg4.png"));
									labelbg2.setIcon(II4);
									
									Thread.sleep(3000);
									
									AC.jLabelXRight(-1044, 0, 20, 9,labelbg1);
									AC.jLabelXRight(0, 1044, 20, 9, labelbg2);
									count = 0;
									break;
								case 4:
									ImageIcon II5 = new ImageIcon(Librairie.class.getResource("/livresImages/bg5.png"));
									labelbg1.setIcon(II5);
									
									Thread.sleep(3000);
									
									AC.jLabelXLeft(0, -1044, 20, 9,labelbg1);
									AC.jLabelXLeft(1044, 0, 20, 9, labelbg2);
									count = 3;
									break;
								
							}
						}	
				}
				catch(Exception e)
				{
					
				}
			}
		}.start();
	}
	
	/**
	 * Access the frame from outside the class
	 */
	public JFrame getFrame()
	{
		return this.frame;
	}
}
