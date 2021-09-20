package bibliotheque.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

import bibliotheque.model.Livre;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetailLivre {

	private JFrame frame;
	private Livre livre;
	private Librairie parent = null;
	private BookList parent2 = null;

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	
	/**
	 * We see two constructors because a book's details window can be called from a book list or also from the library main page
	 */
	
	public DetailLivre(Livre livre,Librairie parent) {
		this.livre = livre;
		this.parent = parent;
		initialize();
	}
	
	public DetailLivre(Livre livre,BookList parent) {
		this.livre = livre;
		this.parent2 = parent;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(Color.WHITE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		frame.setBounds((int) (width/8)-30, 0, 1044, 695);

		//frame.setBounds(125, 0, 1044, 695);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel navLink = new JLabel("<html><body><a style=\"font-size: 17;"
				+ "font-family: arial; \">"+livre.getGenre()+" <span style=\"font-size: 12\"> > </span>"+livre.getAuteur()+"</a></body></html>");
		navLink.setBackground(new Color(255, 255, 255));
		navLink.setForeground(new Color(80,80,80));
		navLink.setBounds(23, 38, 438, 35);
		frame.getContentPane().add(navLink);
		
		JLabel cover = new JLabel("<html><body><img width=\"234\" height=\"383\" src=\""+DetailLivre.class.getResource(livre.getCover_imageUrl())+"\"/></body></html>");
		cover.setBounds(35, 85, 234, 383);
		frame.getContentPane().add(cover);
		
		JLabel titleLabel = new JLabel(livre.getTitre());
		titleLabel.setBackground(new Color(255, 255, 255));
		titleLabel.setForeground(new Color(60,60,60));
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 20));
		titleLabel.setBounds(302, 85, 515, 35);
		frame.getContentPane().add(titleLabel);
		
		JLabel auteurLabel = new JLabel("<html><body>"+livre.getAuteur()+" <span style=\"font-size: 18;\">></span></html></body>");
		auteurLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		auteurLabel.setForeground(new Color(80,80,80));
		auteurLabel.setBounds(302, 122, 295, 25);
		frame.getContentPane().add(auteurLabel);
		
		JLabel DetailBtn = new JLabel("<html><body><img width=\"69\" height=\"25\" src=\""+DetailLivre.class.getResource("/DetailLivre/detailBtn.png")+"\"/></body></html>");
		DetailBtn.setBounds(302, 185, 69, 25);
		frame.getContentPane().add(DetailBtn);
		
		JLabel lblNewLabel = new JLabel("Rating and Reviews");
		lblNewLabel.setFont(new Font("AppleMyungjo", Font.PLAIN, 13));
		lblNewLabel.setForeground(new Color(95,95,95));
		lblNewLabel.setBounds(389, 185, 126, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblRelated = new JLabel("Related");
		lblRelated.setForeground(new Color(95, 95, 95));
		lblRelated.setFont(new Font("AppleMyungjo", Font.PLAIN, 13));
		lblRelated.setBounds(524, 185, 134, 25);
		frame.getContentPane().add(lblRelated);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(302, 222, 711, 12);
		frame.getContentPane().add(separator);
		
		JLabel lblAproposDeLivre = new JLabel("A propos le Livre");
		lblAproposDeLivre.setForeground(new Color(80, 80, 80));
		lblAproposDeLivre.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblAproposDeLivre.setBounds(302, 248, 134, 25);
		frame.getContentPane().add(lblAproposDeLivre);
		
		JTextPane txtpnQscsqc = new JTextPane();
		txtpnQscsqc.setBackground(Color.WHITE);
		txtpnQscsqc.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnQscsqc.setForeground(SystemColor.scrollbar);
		txtpnQscsqc.setText(livre.getDescription());
		txtpnQscsqc.setBounds(302, 285, 711, 209);
		txtpnQscsqc.setEditable(false);
		frame.getContentPane().add(txtpnQscsqc);
		
		JLabel lblInformation = new JLabel("Information");
		lblInformation.setForeground(new Color(80, 80, 80));
		lblInformation.setFont(new Font("AppleGothic", Font.PLAIN, 18));
		lblInformation.setBounds(290, 506, 134, 25);
		frame.getContentPane().add(lblInformation);
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblLanguage.setForeground(Color.GRAY);
		lblLanguage.setBounds(589, 603, 69, 16);
		frame.getContentPane().add(lblLanguage);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblGenre.setForeground(Color.GRAY);
		lblGenre.setBounds(612, 543, 61, 16);
		frame.getContentPane().add(lblGenre);
		
		JLabel lblEditeur = new JLabel("Editeur");
		lblEditeur.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblEditeur.setForeground(Color.GRAY);
		lblEditeur.setBounds(322, 571, 61, 16);
		frame.getContentPane().add(lblEditeur);
		
		JLabel lblDateEdition = new JLabel("Date Edition");
		lblDateEdition.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblDateEdition.setForeground(Color.GRAY);
		lblDateEdition.setBounds(292, 603, 91, 16);
		frame.getContentPane().add(lblDateEdition);
		
		JLabel lblPages = new JLabel("Pages");
		lblPages.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPages.setForeground(Color.GRAY);
		lblPages.setBounds(612, 571, 61, 16);
		frame.getContentPane().add(lblPages);
		
		JLabel languageLabel = new JLabel(livre.getLanguage());
		languageLabel.setForeground(SystemColor.windowBorder);
		languageLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		languageLabel.setBounds(704, 604, 110, 16);
		frame.getContentPane().add(languageLabel);
		
		JLabel EditeurLabel = new JLabel(livre.getAuteur());
		EditeurLabel.setForeground(SystemColor.windowBorder);
		EditeurLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		EditeurLabel.setBounds(402, 572, 178, 16);
		frame.getContentPane().add(EditeurLabel);
		
		JLabel date_editionLabel = new JLabel(livre.getDate_edition()+"");
		date_editionLabel.setForeground(SystemColor.windowBorder);
		date_editionLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		date_editionLabel.setBounds(402, 603, 113, 16);
		frame.getContentPane().add(date_editionLabel);
		
		JLabel genreLabel = new JLabel(livre.getGenre());
		genreLabel.setForeground(SystemColor.windowBorder);
		genreLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		genreLabel.setBounds(704, 543, 178, 16);
		frame.getContentPane().add(genreLabel);
		
		JLabel pagesLabel = new JLabel(livre.getPages()+"");
		pagesLabel.setForeground(SystemColor.windowBorder);
		pagesLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		pagesLabel.setBounds(704, 571, 110, 16);
		frame.getContentPane().add(pagesLabel);
		
		/**
		 * redirect to user space
		 */
		JButton btnMyLibrairie = new JButton("My Librairie");
		btnMyLibrairie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(parent != null)
				{
					MonEspace mySpace = new MonEspace(parent, parent.user.getCne());
					mySpace.getFrame().setVisible(true);
					frame.dispose();
				}else {
					MonEspace mySpace = new MonEspace(parent2.parent, parent2.parent.user.getCne());
					mySpace.getFrame().setVisible(true);
					frame.dispose();
				}
				
			}
		});
		btnMyLibrairie.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		btnMyLibrairie.setBounds(16, 6, 91, 29);
		frame.getContentPane().add(btnMyLibrairie);
		
		JLabel backButton = new JLabel("<html><body><a><img width=\"30\" height=\"20\" src=\""+DetailLivre.class.getResource("/DetailLivre/backButton.png")+"\" /></a></body></html>");
		backButton.setBounds(119, 6, 32, 22);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				if(parent != null)
					parent.getFrame().setVisible(true);
				if(parent2 != null)
					parent2.getFrame().setVisible(true);
			}
		});
		frame.getContentPane().add(backButton);
		
		JLabel forwardButton = new JLabel("<html><body><img width=\"30\" height=\"20\" src=\""+DetailLivre.class.getResource("/DetailLivre/forwardButton.png")+"\"/></body></html>");
		forwardButton.setBounds(152, 6, 32, 22);
		frame.getContentPane().add(forwardButton);
		
		JButton btnNewButton = new JButton("");
		if(parent != null)
		 btnNewButton.setText("<html><body><span>"+parent.livreDao.nbrDispoExemplaire(livre.getIsbn()+"")+"</span> Exemplaires Disponible</body></html>");
		if(parent2 != null)
			btnNewButton.setText("<html><body><span>"+parent2.livreDao.nbrDispoExemplaire(livre.getIsbn()+"")+"</span> Exemplaires Disponible</body></html>");
		btnNewButton.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setBounds(30, 490, 239, 41);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setForeground(Color.GRAY);
		lblIsbn.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblIsbn.setBounds(340, 543, 32, 16);
		frame.getContentPane().add(lblIsbn);
		
		JLabel ISBNLabel = new JLabel(livre.getIsbn()+"");
		ISBNLabel.setForeground(SystemColor.windowBorder);
		ISBNLabel.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		ISBNLabel.setBounds(402, 543, 178, 16);
		frame.getContentPane().add(ISBNLabel);
		
	}
	
	/**
	 * Access frame from outside the class
	 */
	public void visible()
	{
		this.frame.setVisible(true);
	}
}
