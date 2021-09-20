package bibliotheque.model;

import java.util.Date;

public class Utilisateur {
	
	private String cne;
	private String nomComplet;
	private Date date_naiss;
	private String pseudo;
	private String mot_de_pass;
	private byte isAdmin;
	
	public byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Utilisateur(String cne,String nomComplet,Date date_naiss, String pseudo,String mot_de_pass,byte isAdmin)
	{
		this.cne = cne;
		this.nomComplet = nomComplet;
		this.date_naiss = date_naiss;
		this.pseudo = pseudo;
		this.mot_de_pass = mot_de_pass;
		this.isAdmin = isAdmin;
	}

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}



	public Date getDate_naiss() {
		return date_naiss;
	}

	public void setDate_naiss(Date date_naiss) {
		this.date_naiss = date_naiss;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMot_de_pass() {
		return mot_de_pass;
	}

	public void setMot_de_pass(String mot_de_pass) {
		this.mot_de_pass = mot_de_pass;
	}

}
