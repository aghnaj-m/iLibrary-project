package bibliotheque.view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bibliotheque.dao.AdherentDao;
import bibliotheque.model.Utilisateur;
import bibliotheque.qrcode.createQR;

import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

public class Signup {

	private iLivres signin;
	private JFrame frame;
	private JTextField pseudoInput;
	private JPasswordField passwordField;
	private JTextField cneInput;
	private AdherentDao dao;

	/**
	 * Create the application.
	 * @param signin 
	 */
	public Signup(iLivres signin) {
		this.signin = signin;
		initialize();
		dao = new AdherentDao();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setBackground(new Color(0,0,0,0));
		
		JLabel sidentifierOption = new JLabel("");
		sidentifierOption.setBounds(666, 22, 79, 45);
		/**
		 * Toggle from sign up frame to sign in frame
		 */
		sidentifierOption.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				signin.visible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(sidentifierOption);
		
		JLabel senregistreOption = new JLabel("");
		senregistreOption.setBounds(757, 22, 76, 45);
		/**
		 * Toggle from sign in frame to sign up frame
		 */
		senregistreOption.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				frame.setVisible(true);
				signin.visible(false);
			}
		});
		frame.getContentPane().add(senregistreOption);
		
		
		JLabel optionLabel = new JLabel("");
		optionLabel.setIcon(new ImageIcon(iLivres.class.getResource("/login/InscriptionTab.png")));
		optionLabel.setBounds(666, 21, 184, 46);
		
		frame.getContentPane().add(optionLabel);
		
		JSeparator pseudoSeparator = new JSeparator();
		pseudoSeparator.setBackground(SystemColor.windowBorder);
		pseudoSeparator.setForeground(SystemColor.windowBorder);
		pseudoSeparator.setBounds(415, 155, 391, 12);
		frame.getContentPane().add(pseudoSeparator);
		
		pseudoInput = new JTextField();
		pseudoInput.setForeground(Color.WHITE);
		pseudoInput.setBackground(new Color(0,0,0,0));
		pseudoInput.setBounds(425, 117, 394, 46);
		pseudoInput.setBorder(new LineBorder(new Color(0,0,0,0)));
		
		frame.getContentPane().add(pseudoInput);
		pseudoInput.setColumns(10);
		
		JLabel lblPseudo = new JLabel("Pseudo");
		lblPseudo.setForeground(Color.WHITE);
		lblPseudo.setBounds(415, 89, 61, 16);
		frame.getContentPane().add(lblPseudo);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setForeground(Color.WHITE);
		lblMotDePasse.setBounds(415, 278, 117, 16);
		frame.getContentPane().add(lblMotDePasse);
		
		JSeparator passSeparator = new JSeparator();
		passSeparator.setBackground(SystemColor.windowBorder);
		passSeparator.setForeground(SystemColor.windowBorder);
		passSeparator.setBounds(415, 341, 391, 12);
		frame.getContentPane().add(passSeparator);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(425, 302, 394, 46);
		passwordField.setBackground(new Color(0,0,0,0));
		passwordField.setForeground(Color.WHITE);
		passwordField.setBorder(new LineBorder(new Color(0,0,0,0)));

		frame.getContentPane().add(passwordField);
		
		JLabel closeCtrl = new JLabel("");
		closeCtrl.setBounds(21, 21, 18, 17);
		closeCtrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				frame.dispose();
				System.exit(1);
			}
		});
		frame.getContentPane().add(closeCtrl);
		
		JLabel minimiezeCtrl = new JLabel("");
		minimiezeCtrl.setBounds(48, 17, 18, 17);
		minimiezeCtrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				frame.setState(java.awt.Frame.ICONIFIED);
			}
		});
		frame.getContentPane().add(minimiezeCtrl);
		
		JLabel windowCtrlLabel = new JLabel("");
		windowCtrlLabel.setBounds(21, 17, 70, 21);
		windowCtrlLabel.setIcon(new ImageIcon(iLivres.class.getResource("/windowCtrl.png")));
		frame.getContentPane().add(windowCtrlLabel);
		
		JLabel lblCne = new JLabel("Cne");
		lblCne.setForeground(Color.WHITE);
		lblCne.setBounds(415, 179, 61, 16);
		frame.getContentPane().add(lblCne);
		
		cneInput = new JTextField();
		cneInput.setForeground(Color.WHITE);
		cneInput.setColumns(10);
		cneInput.setBorder(new LineBorder(new Color(0,0,0,0)));
		cneInput.setBackground(new Color(0, 0, 0, 0));
		cneInput.setBounds(425, 207, 394, 46);
		frame.getContentPane().add(cneInput);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(SystemColor.windowBorder);
		separator_3.setForeground(SystemColor.windowBorder);
		separator_3.setBounds(415, 241, 391, 12);
		frame.getContentPane().add(separator_3);
		
		JLabel sinscrireBtn = new JLabel("");
		sinscrireBtn.setBounds(546, 385, 162, 45);
		/**
		 * Signing up process
		 * starting by getting user inputs the check for its validation
		 * if so a new account will be created and a Qr code will be generated for the new user so it will be user in the card subscriptio.
		 */
		sinscrireBtn.addMouseListener(new MouseAdapter() {
		@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				Pattern cnePattern = Pattern.compile("[A-Z]{1}\\d{9}");  
				String pseudo = pseudoInput.getText();
				String cne = cneInput.getText();
				String mot_de_passe = passwordField.getText();
				if(cne == null || cne.length() < 10 || cne.equals("") || !cnePattern.matcher(cne).matches())
				{
					JOptionPane.showMessageDialog(null, "Votre CNE est invalid", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else if(pseudo == null || pseudo.length() < 5 || pseudo.equals("") )
				{
					JOptionPane.showMessageDialog(null, "Votre Pseudo doit contenir au moin 5 character.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else if(mot_de_passe == null ||  mot_de_passe.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Mot de passe ne peut pas etre vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else {
					dao.ajouter(new Utilisateur(cne, "", null, pseudo, mot_de_passe,(byte)0));
					 pseudoInput.setText("");
					 cneInput.setText("");
					 passwordField.setText("");
					 String hashedPassword = null;
					 try {
						hashedPassword=dao.chercheParId(cne).getMot_de_pass();
						new createQR(hashedPassword,cne);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					JOptionPane.showMessageDialog(null, "Vous êtes bien inscrit!\n Essayer de vous connecter maintenant.", "Succées", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(sinscrireBtn);
		
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel.setIcon(new ImageIcon(iLivres.class.getResource("/login/signup.png")));
		backgroundLabel.setBounds(0, 0, 850, 450);
		frame.getContentPane().add(backgroundLabel);
	}
	/**
	 * method to set the visibility of the sign up frame outside class.
	 */
	public void visible(boolean b)
	{
		this.frame.setVisible(b);
	}
}
