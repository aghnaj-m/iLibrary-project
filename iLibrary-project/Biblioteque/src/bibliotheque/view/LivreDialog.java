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
import java.util.Calendar;
import java.util.regex.Pattern;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import bibliotheque.dao.LivreDao;
import bibliotheque.model.Livre;

import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Class For adding a new Book or modifying existing one
 */

public class LivreDialog extends JDialog {
	
	private LivreDao livreDao;
	private AdminPanel adminPanel;
	private Livre previousLivre;
	private boolean updateMode = false;

	private final JPanel contentPanel = new JPanel();
	private final JLabel IsbnLabel = new JLabel("ISBN");
	private JTextField isbnInput;
	private JTextField titreInput;
	private JTextField auteurInput;
	private JTextField coverInput;
	private JTextField languageInput;
	private JTextField genreInput;
	private JTextField date_editionInput;
	private JTextField pagesInput;
	private JTextArea descriptionArea;

	/**
	 * Create the dialog.
	 */
	public LivreDialog(AdminPanel adminPanel,
			LivreDao theLivreDao, Livre thePreviousBook, boolean theUpdateMode) {
		this();
		livreDao = theLivreDao;
		this.adminPanel = adminPanel;

		previousLivre = thePreviousBook;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Livre");
			
			populateGui(previousLivre);
		}
	}
	
	private void populateGui(Livre theBook) {

		isbnInput.setText(theBook.getIsbn()+"");
		titreInput.setText(theBook.getTitre());		
		auteurInput.setText(theBook.getAuteur());
		coverInput.setText(theBook.getCover_imageUrl());
		languageInput.setText(theBook.getLanguage());
		genreInput.setText(theBook.getGenre());
		date_editionInput.setText(theBook.getDate_edition()+"");
		pagesInput.setText(theBook.getPages()+"");
		descriptionArea.setText(theBook.getDescription());
		
	}	
	
	public LivreDialog() {
		setResizable(false);
		setBounds(100, 100, 450, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		IsbnLabel.setBounds(6, 21, 49, 28);
		contentPanel.add(IsbnLabel);
		descriptionArea = new JTextArea();
		
		{
			isbnInput = new JTextField();
			isbnInput.setBounds(78, 22, 130, 26);
			contentPanel.add(isbnInput);
			isbnInput.setColumns(10);
		}
		{
			titreInput = new JTextField();
			titreInput.setColumns(10);
			titreInput.setBounds(314, 22, 130, 26);
			contentPanel.add(titreInput);
		}
		{
			JLabel titreLabel = new JLabel("Titre");
			titreLabel.setBounds(237, 27, 78, 16);
			contentPanel.add(titreLabel);
		}
		{
			JLabel lblAuteur = new JLabel("Auteur");
			lblAuteur.setBounds(6, 75, 49, 28);
			contentPanel.add(lblAuteur);
		}
		{
			auteurInput = new JTextField();
			auteurInput.setColumns(10);
			auteurInput.setBounds(78, 76, 130, 26);
			contentPanel.add(auteurInput);
		}
		{
			JLabel coverLabel = new JLabel("Image cover");
			coverLabel.setBounds(237, 81, 78, 16);
			contentPanel.add(coverLabel);
		}
		{
			coverInput = new JTextField();
			coverInput.setColumns(10);
			coverInput.setBounds(314, 76, 130, 26);
			contentPanel.add(coverInput);
		}
		{
			JLabel lblLanguage = new JLabel("Language");
			lblLanguage.setBounds(6, 132, 60, 28);
			contentPanel.add(lblLanguage);
		}
		{
			languageInput = new JTextField();
			languageInput.setColumns(10);
			languageInput.setBounds(78, 133, 130, 26);
			contentPanel.add(languageInput);
		}
		{
			JLabel lblGenre = new JLabel("Genre");
			lblGenre.setBounds(237, 138, 78, 16);
			contentPanel.add(lblGenre);
		}
		{
			genreInput = new JTextField();
			genreInput.setColumns(10);
			genreInput.setBounds(314, 133, 130, 26);
			contentPanel.add(genreInput);
		}
		{
			date_editionInput = new JTextField();
			date_editionInput.setColumns(10);
			date_editionInput.setBounds(92, 198, 130, 26);
			contentPanel.add(date_editionInput);
		}
		{
			JLabel lblDateEdition = new JLabel("Date edition");
			lblDateEdition.setBounds(6, 197, 84, 28);
			contentPanel.add(lblDateEdition);
		}
		{
			JLabel lblPages = new JLabel("pages");
			lblPages.setBounds(237, 203, 78, 16);
			contentPanel.add(lblPages);
		}
		{
			pagesInput = new JTextField();
			pagesInput.setColumns(10);
			pagesInput.setBounds(314, 198, 130, 26);
			contentPanel.add(pagesInput);
		}
		{
			JLabel lblDescription = new JLabel("Description");
			lblDescription.setBounds(6, 261, 84, 28);
			contentPanel.add(lblDescription);
		}
		{
			JScrollPane scrollPane = new JScrollPane(descriptionArea);
			scrollPane.setBounds(101, 274, 343, 143);
			contentPanel.add(scrollPane);
		}
		
		
		
		
		
		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{IsbnLabel}));
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
						saveBook();
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
	
	protected void saveBook() {

		// get the employee info from gui
		
		String titre = titreInput.getText();
		String auteur = auteurInput.getText();
		String cover = coverInput.getText();
		String language  = languageInput.getText();
		String genre = genreInput.getText();
		String description = descriptionArea.getText();
		long isbn = 0;
		int date_edition = 0;
		int pages = 0;
		try {
			isbn = Long.parseLong(isbnInput.getText());
			date_edition = Integer.parseInt(date_editionInput.getText());
			pages = Integer.parseInt(pagesInput.getText());
		}catch(NumberFormatException e)
		{
			
		}
		
		Livre tempBook = null;
		
		Pattern isbnPattern = Pattern.compile("\\d{13}");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		if( isbn == 0 || !isbnPattern.matcher(isbn+"").matches() || titre.equals("") || auteur.equals("") ||
			cover.equals("") || language.equals("") || genre.equals("") || date_editionInput.getText().equals("")
			|| pagesInput.getText().equals("") || pages == 0 || description.equals("") || 
			date_editionInput.getText().length() > 4 || (date_edition > year || date_edition == 0)) {
		JOptionPane.showMessageDialog(null, "Champs invalid", "Erreur", JOptionPane.ERROR_MESSAGE);
		}else {
			if (updateMode) {
				tempBook = previousLivre;
				
				tempBook.setIsbn(isbn);
				tempBook.setTitre(titre);
				tempBook.setAuteur(auteur);
				tempBook.setCover_imageUrl(cover);
				tempBook.setLanguage(language);
				tempBook.setGenre(genre);
				tempBook.setDate_edition(date_edition);
				tempBook.setPages(pages);
				tempBook.setDescription(description);
				
			} else {
				tempBook = new Livre(isbn, titre, description, cover, language, auteur, genre, date_edition, pages);
			}

			try {
				// save to the database
				if (updateMode) {
					livreDao.modifier(tempBook);
				} else {
					livreDao.ajouter(tempBook);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				adminPanel.tableLivreRefresh();

				// show success message
				JOptionPane.showMessageDialog(null,"Livre est bien enregistré.", "Livre Enregistré",
						JOptionPane.INFORMATION_MESSAGE);
				
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}

}
