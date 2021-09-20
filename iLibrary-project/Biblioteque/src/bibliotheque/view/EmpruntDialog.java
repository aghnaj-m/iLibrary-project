package bibliotheque.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import bibliotheque.dao.AdherentDao;
import bibliotheque.dao.EmpruntDao;
import bibliotheque.dao.ExemplaireDao;
import bibliotheque.dao.LivreDao;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Exemplaire;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;
import bibliotheque.qrcode.QrScanner;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class For adding a new loan or modifying existing one
 */

public class EmpruntDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private AdminPanel adminPanel;
	private LivreDao livredao;
	private ExemplaireDao exempDao;
	private EmpruntDao empruntDao;
	private Emprunt previousEmprunt;
	private boolean updateMode;
	
	private JButton btnBeneficiare;
	private JTextField textFieldBenefic;
	private JComboBox livreComboBox;
	private JComboBox comboBox;
	JXDatePicker date_emprunt;
	private JXDatePicker datePicker;
	private JComboBox comboBox_1;
	private JLabel lblEtat;
	private JLabel cover;
	

	/**
	 * Create the dialog.
	 */
	public EmpruntDialog(AdminPanel adminPanel,
			LivreDao livredao,ExemplaireDao exempDao,EmpruntDao empruntDao,
				Emprunt previousEmprunt, boolean theUpdateMode) {
		
		this.livredao = livredao;
		this.exempDao = exempDao;
		this.empruntDao = empruntDao;
		this.adminPanel = adminPanel;
		this.previousEmprunt = previousEmprunt;
		this.updateMode = theUpdateMode;
		initialize();
		if (updateMode) {
			setTitle("Update Emprunt");
			populateGui(this.previousEmprunt);
		}
	}
	
	private void populateGui(Emprunt theEmprunt) {
		textFieldBenefic.setText(theEmprunt.getIdAdherent());
		livreComboBox.setModel(new DefaultComboBoxModel(new Long[] {theEmprunt.getEmprunt().getIsbn()}));
		livreComboBox.setEditable(false);
		livreComboBox.setEnabled(false);
		cover.setText("<html><body><img width=\"236\" height=\"417\" src=\""+DetailLivre.class.getResource(theEmprunt.getEmprunt().getCover_imageUrl())+"\"/></body></html>");
		comboBox.setModel(new DefaultComboBoxModel(new Integer[] {theEmprunt.getIdExemplaire()}));
		comboBox.setEditable(false);
		comboBox.setEnabled(false);
		date_emprunt.setDate(theEmprunt.getDate_emprunt());
		datePicker.setDate(theEmprunt.getDate_retour());
		datePicker.setEditable(false);
		lblEtat.setVisible(true);
		comboBox_1.setVisible(true);
		Exemplaire exemp;
		try {
			exemp = exempDao.exemplaireParId(theEmprunt.getIdExemplaire());
			comboBox_1.setSelectedItem(exemp.getEtat());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnBeneficiare.setVisible(false);

	}	
	
	public void initialize() {
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		cover = new JLabel("");
		cover.setBounds(6, 29, 236, 417);
		contentPanel.add(cover);
		
		btnBeneficiare = new JButton("Beneficiaire");
		btnBeneficiare.setBounds(303, 29, 117, 29);
		btnBeneficiare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				QrScanner wsc = new QrScanner(EmpruntDialog.this);
				wsc.visible(true);
			}
		});
		contentPanel.add(btnBeneficiare);
		
		textFieldBenefic = new JTextField();
		textFieldBenefic.setEditable(false);
		textFieldBenefic.setBounds(254, 82, 221, 26);
		contentPanel.add(textFieldBenefic);
		textFieldBenefic.setColumns(10);
		
		livreComboBox = new JComboBox();
		List<Livre> livres = livredao.getAll();
		ArrayList<Long> ISBNs = new ArrayList<>();
		for(Livre l : livres)
		{
			ISBNs.add(l.getIsbn());
		}
		Object[] ISBNsArray = ISBNs.toArray();
		livreComboBox.setModel(new DefaultComboBoxModel(ISBNsArray));
		livreComboBox.setBounds(254, 154, 221, 27);
		livreComboBox.addActionListener(e->{
			long choix = (long)livreComboBox.getSelectedItem();
			try {
				Livre selectedLivre = livredao.chercheLivre(choix);
				cover.setText("<html><body><img width=\"236\" height=\"417\" src=\""+DetailLivre.class.getResource(selectedLivre.getCover_imageUrl())+"\"/></body></html>");
				List<Exemplaire> exemps = exempDao.disponibleExemplaires(choix);
				ArrayList<Integer> ids = new ArrayList<>();
				comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
				for (Exemplaire ex : exemps)
				{
					ids.add(ex.getId());
				}
				comboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				cover.setText("Can't load cover");
				e1.printStackTrace();
			}
			
		});
		long choix = (long)livreComboBox.getSelectedItem();
		try {
			Livre selectedLivre = livredao.chercheLivre(choix);
			cover.setText("<html><body><img width=\"236\" height=\"417\" src=\""+DetailLivre.class.getResource(selectedLivre.getCover_imageUrl())+"\"/></body></html>");
		} catch (Exception e1) {
			cover.setText("Can't load cover");
			e1.printStackTrace();
		}
		contentPanel.add(livreComboBox);
		
		JLabel lblLivre = new JLabel("Livre");
		lblLivre.setForeground(Color.DARK_GRAY);
		lblLivre.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblLivre.setBounds(264, 126, 78, 16);
		contentPanel.add(lblLivre);
		
		JLabel lblExemplaireN = new JLabel("Exemplaire n°");
		lblExemplaireN.setForeground(Color.DARK_GRAY);
		lblExemplaireN.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblExemplaireN.setBounds(264, 197, 122, 16);
		contentPanel.add(lblExemplaireN);
		
		comboBox = new JComboBox();
		comboBox.setBounds(254, 225, 221, 27);
		contentPanel.add(comboBox);
		
		JLabel lblDateEmprunt = new JLabel("Date emprunt");
		lblDateEmprunt.setForeground(Color.DARK_GRAY);
		lblDateEmprunt.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblDateEmprunt.setBounds(264, 264, 122, 16);
		contentPanel.add(lblDateEmprunt);
		
		date_emprunt = new JXDatePicker();
		date_emprunt.setBounds(254, 292, 221, 27);
		date_emprunt.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
		date_emprunt.setDate(Calendar.getInstance().getTime());
		date_emprunt.setEditable(false);
		contentPanel.add(date_emprunt);
		
		JLabel lblDateRetour = new JLabel("Date retour");
		lblDateRetour.setForeground(Color.DARK_GRAY);
		lblDateRetour.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblDateRetour.setBounds(264, 331, 122, 16);
		contentPanel.add(lblDateRetour);
		
		datePicker = new JXDatePicker();
		datePicker.setBounds(254, 359, 221, 27);
		datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
		datePicker.addActionListener(e->{
        	Date pickedDate = datePicker.getDate(); 
        	Date currentDate = new Date();
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String enteredDate = oDateFormat.format(pickedDate);
            String curr =   oDateFormat.format(currentDate);
            String[] currTab = curr.split("-");
            String[] tab = enteredDate.split("-");
            
            if(pickedDate.getYear() < currentDate.getYear() )
            {
            	datePicker.setDate(Calendar.getInstance().getTime());
            }else {
            	if(pickedDate.getYear() == currentDate.getYear()){
            		if(pickedDate.getMonth() < currentDate.getMonth())
            			datePicker.setDate(Calendar.getInstance().getTime());
            		if(pickedDate.getMonth() == currentDate.getMonth())
            		{
            			if(Integer.parseInt(tab[0]) - Integer.parseInt(currTab[0]) < 0)
            			{
            			datePicker.setDate(Calendar.getInstance().getTime());
            			}
            		}
            	
            	}
            }
		});
		contentPanel.add(datePicker);
		
		lblEtat = new JLabel("Etat");
		lblEtat.setForeground(Color.DARK_GRAY);
		lblEtat.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		lblEtat.setBounds(264, 397, 78, 16);
		lblEtat.setVisible(false);
		contentPanel.add(lblEtat);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"En stock", "Sortant"}));
		comboBox_1.setBounds(254, 419, 221, 27);
		comboBox_1.setVisible(false);
		contentPanel.add(comboBox_1);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							saveEmprunt();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
	
	protected void saveEmprunt() throws Exception {

		// get the employee info from gui
		String beneficiare = textFieldBenefic.getText();
		long isbn =  (long) livreComboBox.getSelectedItem();
		Livre livre = livredao.chercheLivre(isbn);
		int exempId = 0;
		try {
			exempId = (int) comboBox.getSelectedItem();
		}catch(NullPointerException ne)
		{
			exempId = 0;
		}
		
		Date date_retour = null;
		try {
			date_retour = datePicker.getDate();
		}catch(NullPointerException ne)
		{
			date_retour = null;
		}
		
		Emprunt tempEmpr = null;
		
		Pattern cnePattern = Pattern.compile("[A-Z]{1}\\d{9}");
		
		if( beneficiare.equals("") || !cnePattern.matcher(beneficiare).matches()  || date_retour == null
				|| exempId == 0)
		{
		JOptionPane.showMessageDialog(EmpruntDialog.this, "Champs invalid", "Erreur", JOptionPane.ERROR_MESSAGE);
		}else {
			if (updateMode) {
				tempEmpr = previousEmprunt;
				
				tempEmpr.setIdExemplaire(exempId);
				tempEmpr.setEmprunt(livre);
				tempEmpr.setDate_emprunt(new Date());
				tempEmpr.setDate_retour(date_retour);
				tempEmpr.setIdAdherent(beneficiare);
				
			} else {
				tempEmpr = new Emprunt(new Date(), date_retour, beneficiare, exempId, livre);
			}

			try {
				// save to the database
				if (updateMode) {
					//empruntDao.modifier(tempEmpr);
					String selectedEtat = (String)comboBox_1.getSelectedItem();
					String etat = "";
					if(selectedEtat.equals("Sortant"))
						etat = "reserve";
					else
						etat = "dispo";
					exempDao.modifier(new Exemplaire(exempId,etat,livre.getIsbn()));
				} else {
					empruntDao.ajouter(tempEmpr);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				adminPanel.tableEmpruntRefresh();

				// show success message
				JOptionPane.showMessageDialog(null,"Emprunt est bien enregistré.", "Emprunt Enregistré",
						JOptionPane.INFORMATION_MESSAGE);
				
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}

	
	public JTextField getChampBeneficiaire()
	{
		return textFieldBenefic;
	}
}
