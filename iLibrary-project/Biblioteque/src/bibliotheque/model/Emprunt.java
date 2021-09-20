package bibliotheque.model;

import java.util.Date;

public class Emprunt {
	
	private Date date_emprunt;
	private Date date_retour;
	private String idAdherent;
	private int idExemplaire;
	private Livre emprunt;
	
	public Emprunt(Date date_emprunt, Date date_retour, String idAdherent, int idExemplaire,Livre emprunt)
	{
		this.date_emprunt = date_emprunt;
		this.date_retour = date_retour;
		this.idAdherent = idAdherent;
		this.idExemplaire = idExemplaire;
		this.emprunt = emprunt;
	}

	public Livre getEmprunt() {
		return emprunt;
	}

	public void setEmprunt(Livre emprunt) {
		this.emprunt = emprunt;
	}

	public Date getDate_emprunt() {
		return date_emprunt;
	}

	public void setDate_emprunt(Date date_emprunt) {
		this.date_emprunt = date_emprunt;
	}

	public Date getDate_retour() {
		return date_retour;
	}

	public void setDate_retour(Date date_retour) {
		this.date_retour = date_retour;
	}

	public String getIdAdherent() {
		return idAdherent;
	}

	public void setIdAdherent(String idAdherent) {
		this.idAdherent = idAdherent;
	}

	public int getIdExemplaire() {
		return idExemplaire;
	}

	public void setIdExemplaire(int idExemplaire) {
		this.idExemplaire = idExemplaire;
	}
	
	

}
