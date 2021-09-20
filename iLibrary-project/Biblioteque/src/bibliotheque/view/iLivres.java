package bibliotheque.view;

import java.awt.EventQueue;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bibliotheque.dao.AdherentDao;
import bibliotheque.model.Utilisateur;
import bibliotheque.qrcode.QrScanner;

import javax.swing.JSeparator;
import javax.swing.JPasswordField;

/** 
* 
* @author AGHNAJ Mostafa
*  @author MATROUH aymen
*/


/** 
* 
* This is the entry to the application , the class which contains the main method
* its a frame for the authentication
*/

public class iLivres {

	/** 
	*  Swing components declarations
	*/
	private JFrame frame;
	private JTextField pseudoInput;
	private JPasswordField passwordField;
	private Signup signup = new Signup(this);
	private AdherentDao dao;
	private QrScanner wsc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					iLivres window = new iLivres();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public iLivres() {
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
				signup.visible(false);
				frame.setVisible(true);
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
				signup.visible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(senregistreOption);
		
		JLabel optionLabel = new JLabel("");
		optionLabel.setIcon(new ImageIcon(iLivres.class.getResource("/login/ConnectTab.png")));
		optionLabel.setBounds(666, 21, 184, 46);
		
		frame.getContentPane().add(optionLabel);

		
		pseudoInput = new JTextField();
		pseudoInput.setForeground(Color.WHITE);
		pseudoInput.setBackground(new Color(0,0,0,0));
		pseudoInput.setBounds(418, 144, 394, 46);
		pseudoInput.setBorder(new LineBorder(new Color(0,0,0,0)));
		
		frame.getContentPane().add(pseudoInput);
		pseudoInput.setColumns(10);
		
		JLabel lblPseudo = new JLabel("Pseudo");
		lblPseudo.setForeground(Color.WHITE);
		lblPseudo.setBounds(415, 116, 61, 16);
		frame.getContentPane().add(lblPseudo);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setForeground(Color.WHITE);
		lblMotDePasse.setBounds(415, 238, 117, 16);
		frame.getContentPane().add(lblMotDePasse);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(418, 183, 391, 12);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(418, 305, 391, 12);
		frame.getContentPane().add(separator_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(418, 266, 394, 46);
		passwordField.setBackground(new Color(0,0,0,0));
		passwordField.setForeground(Color.WHITE);
		passwordField.setBorder(new LineBorder(new Color(0,0,0,0)));

		frame.getContentPane().add(passwordField);
		
		JLabel closeCtrl = new JLabel("");
		closeCtrl.setBounds(21, 21, 18, 17);
		/**
		 * close the entire application after clicking on the close btn (label)
		 */
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
		
		JLabel connecteBtn = new JLabel("");
		connecteBtn.setBounds(430, 382, 117, 28);
		/**
		 * the authentication whole process
		 * start from verifying the validation of user inputs
		 * Check if there is an existing user with the entered pseudo
		 * if so , we came with the user infos from the database then we compare between the hashed stocked password and the entered one
		 */
		connecteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				String pseudo = pseudoInput.getText();
				String mot_de_passe = passwordField.getText();
				
				if(pseudo == null || pseudo.length() < 5 || pseudo.equals("") )
				{
					JOptionPane.showMessageDialog(null, "Votre Pseudo doit contenir au moin 5 character.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else if(mot_de_passe == null ||  mot_de_passe.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Mot de passe ne peut pas etre vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else {
					String fetchedCne = dao.chercheParPseudo(pseudo);
					if(fetchedCne != null)
					{
						Utilisateur user = null;
						try {
							user = dao.chercheParId(fetchedCne);
							/**
							 * Hashage du mot de passe
							 */
							byte[] bytesOfPassword = null;
							try {
								bytesOfPassword = mot_de_passe.getBytes("UTF-8");
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							MessageDigest md;
							try {
								md = MessageDigest.getInstance("MD5");
								byte[] thedigest = md.digest(bytesOfPassword);
								BigInteger bigInt = new BigInteger(1,thedigest);
								String hashedPassword = bigInt.toString(16);
								/**|Hashage du mot de passe*/
								if(user.getMot_de_pass().equals(hashedPassword))
								{
									frame.setVisible(false);
									frame = null;
									System.gc();
									if(user.getIsAdmin() == 1)
									{
										JOptionPane.showMessageDialog(frame, "Bon retour Admin!!", "Salut", JOptionPane.INFORMATION_MESSAGE);
										AdminPanel window = new AdminPanel();
										window.frmGestionIlivre.setVisible(true);
									}
									else
										new Progress(user);
								}else {
									JOptionPane.showMessageDialog(null, "Mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
							} catch (NoSuchAlgorithmException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Aucun compte avec ce pseudo.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		frame.getContentPane().add(connecteBtn);
		
		JLabel qrCodeBtn = new JLabel("");
		qrCodeBtn.setBounds(740, 368, 61, 52);
		/**
		 * In case the user choose to connect by his subscription card , a Qr Code scanner will be opened
		 */
		qrCodeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				 wsc = new QrScanner(iLivres.this);
					wsc.visible(true);
			}
		});
		frame.getContentPane().add(qrCodeBtn);
		
	
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel.setIcon(new ImageIcon(iLivres.class.getResource("/login/signin.png")));
		backgroundLabel.setBounds(0, 0, 850, 450);
		frame.getContentPane().add(backgroundLabel);
	}
	
	/**
	 * method to set the visibility of the frame outside of the class.
	 */
	public void visible(boolean b)
	{
		this.frame.setVisible(b);
	}
	public JFrame getFrame()
	{
		return this.frame;
	}
}
