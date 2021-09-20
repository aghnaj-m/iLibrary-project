package bibliotheque.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bibliotheque.dao.AdherentDao;
import bibliotheque.dao.EmpruntDao;
import bibliotheque.dao.ExemplaireDao;
import bibliotheque.dao.LivreDao;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Exemplaire;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class AdminPanel {

	protected JFrame frmGestionIlivre;
	
	private LivreDao livreDao;
	private AdherentDao adherentDao;
	private EmpruntDao empruntDao;
	private ExemplaireDao exemplaireDao;
	private DefaultTableModel livreModel;
	private DefaultTableModel adherentModel;
	private DefaultTableModel empruntModel;
	private JTable livreTable;
	private JTable adherentTable;
	private JTable empruntTable;
	private JScrollPane scrollPaneAdherent;
	private JScrollPane scrollPaneLivre;
	private JScrollPane scrollPaneEmprunt;
	private JTextField titreSearch;
	private JTextField cneSearch;
	private JTextField auteurSearch;
	private JTextField empruntSearch;
	private JPanel empruntPanel;
	private JPanel adherentPanel;
	private JLabel updateBookSup;
	private JLabel suppBookSup;
	private JLabel adherentTab;
	private JLabel livreTab;
	private JLabel livreBtn;
	private JLabel adherentBtn;
	private JLabel addBookSup;
	private JLabel addEmpruntSup;
	private JLabel empruntTab;
	private JLabel addAdherentSup;
	private JLabel modifAdherentSup;
	private JLabel suppAdherentSup;
	private JLabel modEmpruntSup;


	/**
	 * Create the application.
	 */
	public AdminPanel() {
		livreDao = new LivreDao();
		adherentDao = new AdherentDao();
		empruntDao = new EmpruntDao(livreDao);
		exemplaireDao = new ExemplaireDao();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGestionIlivre = new JFrame();
		frmGestionIlivre.setTitle("Gestion iLivre");
		frmGestionIlivre.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		frmGestionIlivre.setBounds((int) (width/8)-30, 0, 1044, 695);
		frmGestionIlivre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGestionIlivre.getContentPane().setLayout(null);
		
		/** <ALL ABOUT SIDE MENU > */
		 livreBtn = new JLabel("<html><body><img width=\"255\" height=\"55\" src=\""+AdminPanel.class.getResource("/adminPanel/livrebtn.png")+"\"/></body></html>");
			livreBtn.setBounds(-11, 102, 232, 50);
			frmGestionIlivre.getContentPane().add(livreBtn);
		
			 updateBookSup = new JLabel("");
				updateBookSup.setBounds(22, 215, 33, 35);
				updateBookSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/updateBookSup.png")));
				updateBookSup.setVisible(false);
				updateBookSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						long isbn = Long.parseLong(livreTable.getValueAt(livreTable.getSelectedRow(), 0).toString());
						try {
							Livre selectedLivre = livreDao.chercheLivre(isbn);
							LivreDialog livreDialog = new LivreDialog(AdminPanel.this, livreDao, selectedLivre, true);
							livreDialog.setVisible(true);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				frmGestionIlivre.getContentPane().add(updateBookSup);
				
				 suppBookSup = new JLabel("");
				suppBookSup.setBounds(23, 260, 33, 35);
				suppBookSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/dellBookSup.png")));
				suppBookSup.setVisible(false);
				suppBookSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						 long selectedBook = (long) livreTable.getValueAt(livreTable.getSelectedRow(), 0);
						 int permission = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer ce livre avec"
						 		+ " isbn "+selectedBook+" ?" , "Attention", JOptionPane.YES_NO_OPTION );
							if(permission == JOptionPane.YES_OPTION)
							{
								livreDao.supprimer(selectedBook);
								JOptionPane.showMessageDialog(null,"Book deleted succesfully.", "Book Deleted",
										JOptionPane.INFORMATION_MESSAGE);

								tableLivreRefresh();
							}
								
					}
				});

				frmGestionIlivre.getContentPane().add(suppBookSup);
				
				 addBookSup = new JLabel("");
				addBookSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/addBookSup.png")));
				addBookSup.setBounds(22, 170, 33, 35);
				addBookSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						LivreDialog livreDialog = new LivreDialog(AdminPanel.this, livreDao, null, false);
						livreDialog.setVisible(true);

					}
				});
				frmGestionIlivre.getContentPane().add(addBookSup);

				 modifAdherentSup = new JLabel("");
				 modifAdherentSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/modiffAdherentSupp.png")));
				modifAdherentSup.setBounds(25, 428, 40, 35);
				modifAdherentSup.setVisible(false);
				modifAdherentSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String cne = adherentTable.getValueAt(adherentTable.getSelectedRow(), 0).toString();
						try {
							Utilisateur selectedAdherent = adherentDao.chercheParId(cne);
							AdherentDialog adherentDialog = new AdherentDialog(AdminPanel.this, adherentDao, selectedAdherent, true);
							adherentDialog.setVisible(true);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				frmGestionIlivre.getContentPane().add(modifAdherentSup);
				
				suppAdherentSup = new JLabel("");
				suppAdherentSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/suppAdherentSup.png")));
				suppAdherentSup.setBounds(26, 470, 40, 35);
				suppAdherentSup.setVisible(false);
				suppAdherentSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String selectedAdherent = adherentTable.getValueAt(adherentTable.getSelectedRow(), 0).toString();
						 int permission = JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer cet Adherent avec"
						 		+ " cne "+selectedAdherent+" ?" , "Attention", JOptionPane.YES_NO_OPTION );
							if(permission == JOptionPane.YES_OPTION)
							{
								adherentDao.supprimer(selectedAdherent);
								JOptionPane.showMessageDialog(null,"Adherent bien supprimé.", "Adhérent Supprimé",
										JOptionPane.INFORMATION_MESSAGE);

								tableAdherentRefresh();
							}

					}
				});
				frmGestionIlivre.getContentPane().add(suppAdherentSup);
				
				modEmpruntSup = new JLabel("");
				modEmpruntSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/modEmpruntSup.png")));
				modEmpruntSup.setBounds(22, 594, 40, 35);
				modEmpruntSup.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int idExemplaire = Integer.parseInt(empruntTable.getValueAt(empruntTable.getSelectedRow(), 1).toString());
						String beneficiaire = empruntTable.getValueAt(empruntTable.getSelectedRow(), 2).toString();
						try {
							Emprunt selectedEmprunt = empruntDao.cherchereEmprunt(beneficiaire, idExemplaire);
							EmpruntDialog empDialog = new EmpruntDialog(AdminPanel.this, livreDao, exemplaireDao, empruntDao, selectedEmprunt, true);
							empDialog.setVisible(true);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				modEmpruntSup.setVisible(false);
				frmGestionIlivre.getContentPane().add(modEmpruntSup);
		
		/** <ALL ABOUT TABS > */
		 livreTab = new JLabel("<html><body><img width=\"240\" height=\"78\" src=\""+AdminPanel.class.getResource("/adminPanel/livre.png")+"\"/></body></html>");
		livreTab.setBounds(250, 87, 240, 78);
		livreTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				hideAdherentElements();
				hideEmpruntElements();
				showLivreElements();
			}
		});
		frmGestionIlivre.getContentPane().add(livreTab);
		
		adherentTab = new JLabel("");
		adherentTab.setBounds(502, 87, 240, 78);
		adherentTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				hideLivreElements();
				hideEmpruntElements();
				showAdherentElements();
			}
		});
		frmGestionIlivre.getContentPane().add(adherentTab);
		
		 empruntTab = new JLabel("");
			empruntTab.setBounds(775, 87, 240, 78);
			empruntTab.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					hideLivreElements();
					hideAdherentElements();
					showEmpruntElements();
				}
			});
			frmGestionIlivre.getContentPane().add(empruntTab);
			/** </ALL ABOUT TABS > */
			
			/** <ALL ABOUT EMPRUNTES PANEL> */
			 empruntPanel = new JPanel();
			 empruntPanel.setBounds(250, 164, 794, 509);
			 empruntPanel.setVisible(false);
			 empruntPanel.setLayout(null);
			 empruntPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						empruntSearch.setText("Chercher par beneficiaire...");
					}
				});
			frmGestionIlivre.getContentPane().add(empruntPanel);
			
			empruntSearch = new JTextField();
			empruntSearch.setBorder(null);
			empruntSearch.setColumns(10);
			empruntSearch.setText("Chercher par beneficiaire...");
			empruntSearch.setBounds(141, 76, 179, 31);
			empruntSearch.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					empruntSearch.setText("");
				}
			});
			empruntPanel.add(empruntSearch);
			
			JLabel empruntSearchBtn = new JLabel("");
			empruntSearchBtn.setBounds(476, 74, 129, 31);
			empruntSearchBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					try {
						String cne = empruntSearch.getText();
						
						List<Emprunt> emprunts;

						if ( cne != null &&
							!cne.equals("Chercher par beneficiaire...") && cne.trim().length() > 0)  {
							emprunts = new ArrayList<Emprunt>();
							emprunts = empruntDao.userEmpruntes(cne);
						} else {
							emprunts = empruntDao.getAll();
						}
						
						empruntModel.setRowCount(0);
						if(emprunts.get(0) != null)
						{
							String etat = "";
							for(Emprunt emprunt: emprunts)
							{
								Exemplaire exemp = exemplaireDao.exemplaireParId(emprunt.getIdExemplaire());
								if(exemp.getEtat().equals("dispo"))
									etat = "En stock";
								else
									etat = "<html><body style=\"color: red;\">Sortant</body></html>";
								empruntModel.addRow(new Object[] {emprunt.getEmprunt().getTitre(),
										emprunt.getIdExemplaire(),emprunt.getIdAdherent(),
										emprunt.getDate_emprunt(),emprunt.getDate_retour(),etat});
							}
						}
						
					}catch(IndexOutOfBoundsException eofb)
					{
						
					}
					catch (Exception exc) {
						exc.printStackTrace();
						JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", 
								JOptionPane.ERROR_MESSAGE); 
					}
					
					modifAdherentSup.setVisible(false);
					suppAdherentSup.setVisible(false);

				}
			});
			empruntPanel.add(empruntSearchBtn);
			
			empruntModel =   new DefaultTableModel();      
			 empruntTable = new JTable();
			 empruntTable.setBounds(247, 278, 811, 465);
			 empruntTable.setRowHeight(30);
	        

			 empruntModel.addColumn(new String("<html><strong>Titre</strong></html>"));
			 empruntModel.addColumn(new String("<html><strong>Exemplaire id</strong></html>"));
			 empruntModel.addColumn(new String("<html><strong>Beneficaire</strong></html>"));
			 empruntModel.addColumn(new String("<html><strong>date emprunt</strong></html>"));
			 empruntModel.addColumn(new String("<html><strong>date retour</strong></html>"));
			 empruntModel.addColumn(new String("<html><strong>etat</strong></html>"));

			 try {
					List<Emprunt> empruntList = empruntDao.getAll();
					String etat = "";
					for(Emprunt emprunt: empruntList)
					{
						Exemplaire exemp = exemplaireDao.exemplaireParId(emprunt.getIdExemplaire());
						if(exemp.getEtat().equals("dispo"))
							etat = "En stock";
						else
							etat = "<html><body style=\"color: red;\">Sortant</body></html>";
						empruntModel.addRow(new Object[] {emprunt.getEmprunt().getTitre(),
								emprunt.getIdExemplaire(),emprunt.getIdAdherent(),
								emprunt.getDate_emprunt(),emprunt.getDate_retour(),etat});
					}
					empruntTable.setModel(empruntModel);
					
				 }catch(Exception e) {
					
				 }
					
			 empruntTable.addMouseListener(new MouseAdapter() {
			        	@Override
			        	public void mouseClicked(MouseEvent e) {
			        	super.mouseClicked(e);
			    		modEmpruntSup.setVisible(true);
			        	}
					});
				
				 scrollPaneEmprunt = new JScrollPane(empruntTable);
				 scrollPaneEmprunt.setBounds(30, 203, 738, 292);
				 scrollPaneEmprunt.setVisible(false);
				empruntPanel.add(scrollPaneEmprunt);
			 
			JLabel empruntPanBg = new JLabel("");
			empruntPanBg.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/ empruntPanelBg.png")));
			empruntPanBg.setBounds(0, 0, 794, 508);
			empruntPanel.add(empruntPanBg);
			
			/** </ALL ABOUT ADHERENT PANEL> */
			
			/** <ALL ABOUT ADHERENT PANEL> */	
		 adherentPanel = new JPanel();
		adherentPanel.setBounds(250, 164, 794, 509);
		adherentPanel.setVisible(false);
		adherentPanel.setLayout(null);
		adherentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cneSearch.setText("Chercher par cne...");
			}
		});
		frmGestionIlivre.getContentPane().add(adherentPanel);
	
		cneSearch = new JTextField();
		cneSearch.setBorder(null);
		cneSearch.setColumns(10);
		cneSearch.setText("Chercher par cne...");
		cneSearch.setBounds(141, 76, 179, 31);
		cneSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				cneSearch.setText("");
			}
		});
		adherentPanel.add(cneSearch);
		
		JLabel adherentSearchBtn = new JLabel("");
		adherentSearchBtn.setBounds(476, 74, 129, 31);
		adherentSearchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				try {
					String cne = cneSearch.getText();
					
					List<Utilisateur> adherents = null;

					if ( cne != null &&
						!cne.equals("Chercher par cne...") && cne.trim().length() > 0)  {
						adherents = new ArrayList<Utilisateur>();
						adherents.add(0, adherentDao.chercheParId(cne));
					} else {
						adherents = adherentDao.getAll();
					}
					
					adherentModel.setRowCount(0);
					if(adherents.get(0) != null)
					{
						for(Utilisateur adherent: adherents)
						{
							adherentModel.addRow(new Object[] {adherent.getCne(), adherent.getPseudo(),
									adherent.getNomComplet(),adherent.getDate_naiss(),adherent.getMot_de_pass()});
						}
					}
					
				} catch (Exception exc) {
					exc.printStackTrace();
					JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", 
							JOptionPane.ERROR_MESSAGE); 
				}
				
				modifAdherentSup.setVisible(false);
				suppAdherentSup.setVisible(false);

			}		
		});
		adherentPanel.add(adherentSearchBtn);
		
		 adherentModel =   new DefaultTableModel();      
		 adherentTable = new JTable();
		 adherentTable.setBounds(247, 278, 811, 465);
		 adherentTable.setRowHeight(30);
        

        adherentModel.addColumn(new String("<html><strong>Cne</strong></html>"));
        adherentModel.addColumn(new String("<html><strong>Pseudo</strong></html>"));
        adherentModel.addColumn(new String("<html><strong>Nom complet</strong></html>"));
        adherentModel.addColumn(new String("<html><strong>date naissance</strong></html>"));
        adherentModel.addColumn(new String("<html><strong>Mot de passe</strong></html>"));
		
		
		try {
			List<Utilisateur> adherentList = adherentDao.getAll();
			for(Utilisateur adherent: adherentList)
			{
				adherentModel.addRow(new Object[] {adherent.getCne(), adherent.getPseudo(),
						adherent.getNomComplet(),adherent.getDate_naiss(),adherent.getMot_de_pass()});
			}
			adherentTable.setModel(adherentModel);
			
		 }catch(Exception e) {
			
		 }
			
		adherentTable.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        	super.mouseClicked(e);
	    		modifAdherentSup.setVisible(true);
	    		suppAdherentSup.setVisible(true);
	        	}
			});
		
		 scrollPaneAdherent = new JScrollPane(adherentTable);
		scrollPaneAdherent.setBounds(30, 203, 738, 292);
		scrollPaneAdherent.setVisible(false);
		adherentPanel.add(scrollPaneAdherent);

		
		JLabel adherentPanBg = new JLabel("");
		adherentPanBg.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/ adherentPanelBg.png")));
		adherentPanBg.setBounds(0, 0, 794, 508);
		adherentPanel.add(adherentPanBg);
		
		
		
		
		/** </ALL ABOUT ADHERENT PANEL> */
		
		/** <ALL ABOUT LIVRE PANEL> */
	
		titreSearch = new JTextField();
		titreSearch.setBorder(null);
		titreSearch.setBounds(317, 247, 173, 31);
		titreSearch.setText("Chercher par titre...");
		titreSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
 				titreSearch.setText("");
			}
		});
		titreSearch.setColumns(10);
		frmGestionIlivre.getContentPane().add(titreSearch);
		
		auteurSearch = new JTextField();
		auteurSearch.setBorder(null);
		auteurSearch.setColumns(10);
		auteurSearch.setText("Chercher par auteur...");
		auteurSearch.setBounds(591, 245, 179, 32);
		auteurSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				auteurSearch.setText("");
			}
		});
		frmGestionIlivre.getContentPane().add(auteurSearch);
		
		JLabel searchBtn = new JLabel("");
		searchBtn.setBounds(857, 247, 129, 31);
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				try {
					String auteur = auteurSearch.getText();
					String titre = titreSearch.getText();
					
					List<Livre> livres = null;

					if (	(titre != null  || auteur != null ) &&
						(!auteur.equals("Chercher par auteur...") || !titre.equals("Chercher par titre...")) &&
						(titre.trim().length() > 0 || auteur.trim().length() > 0)) {
						livres = livreDao.chercheLivre(titre, auteur);
					} else {
						livres = livreDao.getAll();
					}
					
					livreModel.setRowCount(0);
					for(Livre livre: livres)
					{
						livreModel.addRow(new Object[] {livre.getIsbn(),livre.getTitre(),livre.getAuteur(),
								//livre.getDescription(),
								livre.getCover_imageUrl(),livre.getLanguage(),livre.getGenre(),livre.getDate_edition()
								,livre.getPages()});
					}
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", 
							JOptionPane.ERROR_MESSAGE); 
				}
				
				updateBookSup.setVisible(false);
	    		suppBookSup.setVisible(false);

			}			
		});
		frmGestionIlivre.getContentPane().add(searchBtn);
		
		 livreModel =   new DefaultTableModel();      
		 livreTable = new JTable();
		livreTable.setBounds(247, 278, 811, 465);
        livreTable.setRowHeight(30);
        

		livreModel.addColumn(new String("<html><strong>isbn</strong></html>"));
		livreModel.addColumn(new String("<html><strong>Titre</strong></html>"));
		livreModel.addColumn(new String("<html><strong>Auteur</strong></html>"));
		//livreModel.addColumn(new String("<html><strong>Description</strong></html>"));
		livreModel.addColumn(new String("<html><strong>cover</strong></html>"));
		livreModel.addColumn(new String("<html><strong>language</strong></html>"));
		livreModel.addColumn(new String("<html><strong>genre</strong></html>"));
		livreModel.addColumn(new String("<html><strong>date_edition</strong></html>"));
		livreModel.addColumn(new String("<html><strong>pages</strong></html>"));

		
		try {
			List<Livre> bookList = livreDao.getAll();
			for(Livre livre: bookList)
			{
				livreModel.addRow(new Object[] {livre.getIsbn(),livre.getTitre(),livre.getAuteur(),
						//livre.getDescription(),
						livre.getCover_imageUrl(),livre.getLanguage(),livre.getGenre(),livre.getDate_edition()
						,livre.getPages()});
			}
			livreTable.setModel(livreModel);
			
		 }catch(Exception e) {
			
		 }
			
		livreTable.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseEntered(MouseEvent e) {
	        		// TODO Auto-generated method stub
	        		super.mouseEntered(e);
	        		//textField.setText("Chercher par prix..");
	        	}
	        	
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        	// TODO Auto-generated method stub
	        	super.mouseClicked(e);
	    		updateBookSup.setVisible(true);
	    		suppBookSup.setVisible(true);
	        	}
			});
		
		 scrollPaneLivre = new JScrollPane(livreTable);
		scrollPaneLivre.setBounds(277, 371, 738, 292);
		frmGestionIlivre.getContentPane().add(scrollPaneLivre);
		
		
		
				
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/interface.png")));
		label.setBounds(0, 0, 1044, 673);
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				auteurSearch.setText("Chercher par auteur...");
				titreSearch.setText("Chercher par titre...");
			}
		});
		
		 
		
		adherentBtn = new JLabel("<html><body><img width=\"255\" height=\"55\" src=\""+AdminPanel.class.getResource("/adminPanel/Adherentsbtn.png")+"\"/></body></html>");
		adherentBtn.setBounds(-19, 325, 240, 50);
		adherentBtn.setVisible(false);
		frmGestionIlivre.getContentPane().add(adherentBtn);
		
		
		addEmpruntSup = new JLabel("");
		addEmpruntSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/addemprunt.png")));
		addEmpruntSup.setBounds(22, 547, 40, 35);
		addEmpruntSup.setVisible(false);
		addEmpruntSup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmpruntDialog empDialog = new EmpruntDialog(AdminPanel.this, livreDao, exemplaireDao, empruntDao, null, false);
				empDialog.setVisible(true);
			}
		});
		frmGestionIlivre.getContentPane().add(addEmpruntSup);
		
		addAdherentSup = new JLabel("");
		addAdherentSup.setIcon(new ImageIcon(AdminPanel.class.getResource("/adminPanel/addadherent.png")));
		addAdherentSup.setBounds(25, 382, 40, 35);
		addAdherentSup.setVisible(false);
		addAdherentSup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdherentDialog dialog = new AdherentDialog(AdminPanel.this, adherentDao, null, false);
				dialog.setVisible(true);
			}
		});
		frmGestionIlivre.getContentPane().add(addAdherentSup);
		
		/** </ALL ABOUT ADHERENT PANEL> */
		
		
		
		frmGestionIlivre.getContentPane().add(label);
		
				
	}
	
	private void hideLivreElements()
	{
		livreTab.setText("");
		livreBtn.setVisible(false);
		addBookSup.setVisible(false);
		updateBookSup.setVisible(false);
		suppBookSup.setVisible(false);
		scrollPaneLivre.setVisible(false);
	}
	private void showLivreElements()
	{
		addBookSup.setVisible(true);
		livreBtn.setVisible(true);
		scrollPaneLivre.setVisible(true);
		livreTab.setText("<html><body><img width=\"240\" height=\"78\" src=\""+AdminPanel.class.getResource("/adminPanel/livre.png")+"\"/></body></html>");
	}
	
	private void hideAdherentElements()
	{
		adherentTab.setText("");
		adherentBtn.setVisible(false);
		adherentPanel.setVisible(false);
		addAdherentSup.setVisible(false);
		modifAdherentSup.setVisible(false);
		suppAdherentSup.setVisible(false);
		scrollPaneAdherent.setVisible(false);
	}
	private void showAdherentElements()
	{
		adherentBtn.setVisible(true);
		addAdherentSup.setVisible(true);
		scrollPaneAdherent.setVisible(true);
		adherentTab.setText("<html><body><img width=\"240\" height=\"78\" src=\""+AdminPanel.class.getResource("/adminPanel/Adhérent.png")+"\"/></body></html>");
		adherentPanel.setVisible(true);
	}
	
	private void hideEmpruntElements()
	{
		empruntTab.setText("");
		empruntPanel.setVisible(false);
		addEmpruntSup.setVisible(false);
		modEmpruntSup.setVisible(false);
		scrollPaneEmprunt.setVisible(false);
	}
	private void showEmpruntElements()
	{
		empruntTab.setText("<html><body><img width=\"240\" height=\"78\" src=\""+AdminPanel.class.getResource("/adminPanel/Emprunt.png")+"\"/></body></html>");
		empruntPanel.setVisible(true);
		addEmpruntSup.setVisible(true);
		scrollPaneEmprunt.setVisible(true);
	}
	
	public void tableLivreRefresh()
	{
		livreModel.setRowCount(0);
		List<Livre> livres = livreDao.getAll();
		for(Livre livre: livres)
		{
			livreModel.addRow(new Object[] {livre.getIsbn(),livre.getTitre(),livre.getAuteur(),
					//livre.getDescription(),
					livre.getCover_imageUrl(),livre.getLanguage(),livre.getGenre(),livre.getDate_edition()
					,livre.getPages()});
		}
		updateBookSup.setVisible(false);
		suppBookSup.setVisible(false);

	}
	
	public void tableAdherentRefresh()
	{
		adherentModel.setRowCount(0);
		List<Utilisateur> adherents = adherentDao.getAll();
		for(Utilisateur adherent: adherents)
		{
			adherentModel.addRow(new Object[] {adherent.getCne(), adherent.getPseudo(),
					adherent.getNomComplet(),adherent.getDate_naiss(),adherent.getMot_de_pass()});
		}
		modifAdherentSup.setVisible(false);
		suppAdherentSup.setVisible(false);
	}
	
	public void tableEmpruntRefresh()
	{
		empruntModel.setRowCount(0);
		List<Emprunt> emprunts = empruntDao.getAll();
		String etat="";
		for(Emprunt emprunt: emprunts)
		{
			Exemplaire exemp;
			try {
				exemp = exemplaireDao.exemplaireParId(emprunt.getIdExemplaire());
				if(exemp.getEtat().equals("dispo"))
					etat = "En stock";
				else
					etat = "<html><body style=\"color: red;\">Sortant</body></html>";
				empruntModel.addRow(new Object[] {emprunt.getEmprunt().getTitre(),
						emprunt.getIdExemplaire(),emprunt.getIdAdherent(),
						emprunt.getDate_emprunt(),emprunt.getDate_retour(),etat});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//modifAdherentSup.setVisible(false);
		//suppAdherentSup.setVisible(false);
	}
}
