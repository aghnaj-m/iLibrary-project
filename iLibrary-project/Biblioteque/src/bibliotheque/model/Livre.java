package bibliotheque.model;

import java.util.ArrayList;
import java.util.Date;

public class Livre {
	
	private long isbn;
	private String titre ;
	private String description;
	private String cover_imageUrl;
	private String language;
	private String auteur;
	private String genre;
	private int date_edition;
	private int pages;
	private ArrayList<Exemplaire> exemplaires;
	
	public Livre(long isbn,String titre,String description,String cover_imageUrl,String language,String auteur,String genre,int date_edition,int pages)
	{
		this.isbn = isbn;
		this.titre = titre;
		this.description = description;
		this.cover_imageUrl = cover_imageUrl;
		this.language = language;
		this.auteur = auteur;
		this.genre = genre;
		this.date_edition = date_edition;
		this.pages = pages;
		this.exemplaires = new ArrayList<Exemplaire>();
				
	}

	public ArrayList<Exemplaire> getExemplaires() {
		return exemplaires;
	}
	
	
	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover_imageUrl() {
		return cover_imageUrl;
	}

	public void setCover_imageUrl(String cover_imageUrl) {
		this.cover_imageUrl = cover_imageUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getDate_edition() {
		return date_edition;
	}

	public void setDate_edition(int date_edition) {
		this.date_edition = date_edition;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
}
