package bibliotheque.model;

public class Exemplaire {
	
	private int id;
	private String etat;
	private long idLivre;
	
	public Exemplaire(int id,String etat,long idLivre)
	{
		this.id = id;
		this.etat = etat;
		this.idLivre = idLivre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public long getIdLivre() {
		return idLivre;
	}

	public void setIdLivre(long idLivre) {
		this.idLivre = idLivre;
	}
	
	

}
