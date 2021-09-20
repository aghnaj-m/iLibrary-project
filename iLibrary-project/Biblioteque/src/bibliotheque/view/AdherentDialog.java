package bibliotheque.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import bibliotheque.dao.AdherentDao;
import bibliotheque.model.Utilisateur;
import bibliotheque.qrcode.createQR;

import java.awt.Component;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;

/**
 * Class For adding a new Adherent or modifying existing one
 */

public class AdherentDialog extends JDialog {
	
	private AdherentDao adherentDao;
	private AdminPanel adminPanel;
	private Utilisateur previousAdherent;
	private boolean updateMode = false;

	private final JPanel contentPanel = new JPanel();
	
	private JTextField cneInput;
	private JTextField nomCompletInput;
	private JTextField pseudoInput;
	private JTextField paswordInput;
	private JXDatePicker dateNaissPicker;
	private JTextField passwordConfirmationInput;

	/**
	 * Create the dialog.
	 */
	public AdherentDialog(AdminPanel adminPanel,
			AdherentDao theAdherentDao, Utilisateur previousAdherent, boolean theUpdateMode) {
		this();
		adherentDao = theAdherentDao;
		this.adminPanel = adminPanel;

		this.previousAdherent = previousAdherent;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Adherent");
			
			populateGui(previousAdherent);
		}
	}
	
	private void populateGui(Utilisateur theAdherent) {

		cneInput.setText(theAdherent.getCne());
		nomCompletInput.setText(theAdherent.getNomComplet());
		pseudoInput.setText(theAdherent.getPseudo());
		dateNaissPicker.setDate(theAdherent.getDate_naiss());
		
	}	
	
	public AdherentDialog() {
		setResizable(false);
		setBounds(100, 100, 470, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel cneLabel = new JLabel("Cne");
		cneLabel.setBounds(6, 21, 49, 28);
		contentPanel.add(cneLabel);
		
		{
			cneInput = new JTextField();
			cneInput.setBounds(78, 22, 130, 26);
			contentPanel.add(cneInput);
			cneInput.setColumns(11);
		}
		{
			nomCompletInput = new JTextField();
			nomCompletInput.setColumns(10);
			nomCompletInput.setBounds(334, 22, 130, 26);
			contentPanel.add(nomCompletInput);
		}
		{
			JLabel nomCompletLabel = new JLabel("Nom complet");
			nomCompletLabel.setBounds(220, 27, 95, 16);
			contentPanel.add(nomCompletLabel);
		}
		{
			JLabel pseudoLabel = new JLabel("Pseudo");
			pseudoLabel.setBounds(6, 75, 49, 28);
			contentPanel.add(pseudoLabel);
		}
		{
			pseudoInput = new JTextField();
			pseudoInput.setColumns(10);
			pseudoInput.setBounds(78, 76, 130, 26);
			contentPanel.add(pseudoInput);
		}
		{
			JLabel date_naissLabel = new JLabel("Date naissance");
			date_naissLabel.setBounds(220, 81, 95, 16);
			contentPanel.add(date_naissLabel);
		}
		{
			dateNaissPicker = new JXDatePicker();
			dateNaissPicker.setBounds(334, 76, 130, 26);
			dateNaissPicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
			contentPanel.add(dateNaissPicker);
		}
		{
			JLabel passwordLabel = new JLabel("Password");
			passwordLabel.setBounds(6, 132, 71, 28);
			contentPanel.add(passwordLabel);
		}
		{
			paswordInput = new JTextField();
			paswordInput.setColumns(10);
			paswordInput.setBounds(78, 133, 130, 26);
			contentPanel.add(paswordInput);
		}
		{
			JLabel lblConfirmPassword = new JLabel("Confirm Password");
			lblConfirmPassword.setBounds(220, 132, 114, 28);
			contentPanel.add(lblConfirmPassword);
		}
		
		passwordConfirmationInput = new JTextField();
		passwordConfirmationInput.setColumns(10);
		passwordConfirmationInput.setBounds(334, 133, 130, 26);
		contentPanel.add(passwordConfirmationInput);
		
		
		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cneLabel}));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("S'auvegarder");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveAdherent();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	protected void saveAdherent() {

		// get the employee info from gui
		String cne = cneInput.getText();
		String nomComplet = nomCompletInput.getText();
		String pseudo = pseudoInput.getText();
		Date date_naissance = dateNaissPicker.getDate();
		String password  = paswordInput.getText();
		String passwordConfirmation = passwordConfirmationInput.getText();
		
		Utilisateur tempAdherent = null;
		
		Pattern cnePattern = Pattern.compile("[A-Z]{1}\\d{9}");
		
		if( cne.equals("") || !cnePattern.matcher(cne).matches() || nomComplet.equals("") || 
			pseudo.equals("") || password.equals("") || passwordConfirmation.equals("") ||
			 date_naissance == null || !password.equals(passwordConfirmation)
			) {
		JOptionPane.showMessageDialog(null, "Champs invalid", "Erreur", JOptionPane.ERROR_MESSAGE);
		}else {
			if (updateMode) {
				tempAdherent = previousAdherent;
				
				tempAdherent.setCne(cne);
				tempAdherent.setNomComplet(nomComplet);
				tempAdherent.setPseudo(pseudo);
				tempAdherent.setDate_naiss(date_naissance);
				tempAdherent.setMot_de_pass(password);
				
				
			} else {
				tempAdherent = new Utilisateur(cne, nomComplet, date_naissance, pseudo, password,(byte) 0);
			}

			try {
				// save to the database
				if (updateMode) {
					adherentDao.modifier(tempAdherent);
					
				} else {
					adherentDao.ajouter(tempAdherent);
					try {
						String hashedPassword=adherentDao.chercheParId(cne).getMot_de_pass();
						new createQR(hashedPassword,cne);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				adminPanel.tableAdherentRefresh();

				// show success message
				JOptionPane.showMessageDialog(null,"Adhérent est bien enregistré.", "Adhérent Enregistré",
						JOptionPane.INFORMATION_MESSAGE);
				
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}
}
