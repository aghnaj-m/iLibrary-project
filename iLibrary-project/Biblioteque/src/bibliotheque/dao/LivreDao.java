package bibliotheque.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import bibliotheque.connexion.Connexion;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Exemplaire;
import bibliotheque.model.Livre;

public class LivreDao {

	private Connexion myConn = new Connexion();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public LivreDao() {}
	
	public void supprimer(long isbn) {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement("delete from livre where isbn=?");
			
			// set param
			myStmt.setLong(1,isbn);
			
			// execute SQL
			myStmt.executeUpdate();			
		}catch (SQLException e) {
			//throw new DAOException("Error deleting an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
	}
	
	public void modifier(Livre livre) {
		PreparedStatement myStmt = null;
		String sql = "update livre set titre = ? , description = ? , "
				+ " language = ? , auteur = ? , genre = ? , date_edition = ? , pages = ? where isbn = ?";
		
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setString(1, livre.getTitre());
			myStmt.setString(2, livre.getDescription());
			myStmt.setString(3, livre.getLanguage());
			myStmt.setString(4, livre.getAuteur());
			myStmt.setString(5, livre.getGenre());
			myStmt.setInt(6, livre.getDate_edition());
			myStmt.setInt(7, livre.getPages());
			myStmt.setLong(8, livre.getIsbn());
			
			// execute SQL
			myStmt.executeUpdate();			
		}catch (SQLException e) {
			//throw new DAOException("Error updating an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	public void ajouter(Livre livre) {
		
		PreparedStatement myStmt = null;
		String sql = "insert into livre values (?,?,?,?,?,?,?,?,?)";
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setLong(1, livre.getIsbn());;
			myStmt.setString(2, livre.getTitre());
			myStmt.setString(3, livre.getDescription());
			myStmt.setString(4, livre.getCover_imageUrl());
			myStmt.setString(5, livre.getLanguage());
			myStmt.setString(6, livre.getAuteur());
			myStmt.setString(7, livre.getGenre());
			myStmt.setInt(8, livre.getDate_edition());
			myStmt.setInt(9, livre.getPages());
			
			
			// execute SQL
			
			int etatDajout = myStmt.executeUpdate();
			
		}catch (SQLException e) {
			//throw new DAOException("Error adding an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	
	public int nbrDispoExemplaire(String isbn)
	{
		Statement myStmt = null;
		ResultSet myRs = null;
		
		int nbr_des_exemplaire_dispo = -1;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select count(*) as disponible from exemplaire where "
					+ "idLivre = "+isbn+" and etat = 'dispo'");
			
			while (myRs.next()) {
				nbr_des_exemplaire_dispo = myRs.getInt("disponible");
			}
		}
		catch(SQLException e)
			{
				e.printStackTrace();
			}
		return nbr_des_exemplaire_dispo;
	}
	
	
	public List<Livre> getAll() {
		List<Livre> list = new ArrayList<Livre>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select * from livre");
			
			while (myRs.next()) {
				Livre tempLivres = convertRowToBook(myRs);
				list.add(tempLivres);
			}
			return list;		
		}catch (SQLException e) {
			//throw new DAOException("Error searching all employees ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		return list;
	}
	
	public Livre livreParSonExemplaire(int idExemplaire)throws Exception {
		Livre list = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			//System.out.println("titre: "+titre+"\t auteur: "+auteur);
			myStmt = myConn.getConnexion().prepareStatement("select isbn,titre,description, cover_image ,"
					+ " language , auteur , genre , date_edition , pages from livre join exemplaire "
					+ "on livre.isbn = exemplaire.idLivre join emprunts "
					+ "on emprunts.exemplaireId = exemplaire.id where id = ? ");
			
			myStmt.setInt(1, idExemplaire);
			;
			
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				list = convertRowToBook(myRs);
			}
			return list;
		}catch (SQLException e) {
			throw new Exception();
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public Livre chercheLivre(long isbn)throws Exception {
		

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Livre tempLivre = null;

		try {
			
			myStmt = myConn.getConnexion().prepareStatement("select * from livre where isbn = ? ");
			
			
			myStmt.setLong(1, isbn);
			;
			
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				 tempLivre = convertRowToBook(myRs);
			}
			return tempLivre;
		}catch (SQLException e) {
			throw new Exception();
		}
		finally {
			close(myStmt, myRs);
		}
	}


	
	public List<Livre> chercheLivre(String keyword)throws Exception {
		List<Livre> list = new ArrayList<Livre>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			if(keyword.length() > 0)
				keyword = "%"+keyword+"%";
			//System.out.println("titre: "+titre+"\t auteur: "+auteur);
			myStmt = myConn.getConnexion().prepareStatement("select * from livre where titre like ? OR auteur like ? ");
			
			myStmt.setString(1, keyword);
			myStmt.setString(2, keyword);
			;
			
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Livre tempLivre = convertRowToBook(myRs);
				list.add(tempLivre);
			}
			return list;
		}catch (SQLException e) {
			throw new Exception();
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Livre> chercheLivre(String titre,String auteur)throws Exception {
		List<Livre> list = new ArrayList<Livre>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			if(titre.length() > 0)
				titre = "%"+titre+"%";
			if(auteur.length() > 0)
				auteur = "%"+auteur+"%";
			myStmt = myConn.getConnexion().prepareStatement("select * from livre where (titre like ? and auteur like ? ) or (titre like ? or auteur like ? ) ");
			
			myStmt.setString(1, titre);
			myStmt.setString(2, auteur);
			myStmt.setString(3, titre);
			myStmt.setString(4, auteur);
			
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Livre tempLivre = convertRowToBook(myRs);
				list.add(tempLivre);
			}
			return list;
		}catch (SQLException e) {
			throw new Exception();
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	private Livre convertRowToBook(ResultSet myRs) {
		
		Livre tempBook= null;
		
		try{
			long isbn = myRs.getLong("isbn");
			String titre = myRs.getString("titre");
			String description = myRs.getString("description");
			String cover_imageUrl = myRs.getString("cover_image");
			String language = myRs.getString("language");
			String auteur = myRs.getString("auteur");
			String genre = myRs.getString("genre");
			int date_edition = myRs.getInt("date_edition");
			int pages = myRs.getInt("pages");
			
			tempBook = new Livre(isbn,titre,description,cover_imageUrl,language,auteur,genre,date_edition,pages);
		
		} catch (SQLException e) {
			//throw new DAOException("Error reading the ResultSet ... ", e);
		}
						
		return tempBook;
	}
	
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		if (myRs != null) {
			try {
				myRs.close();
			} catch (SQLException e) {
				//throw new DAOException("Error closing the ResultSet ... ", e);
			}
		}

		if (myStmt != null) {
			try {
				myStmt.close();
			} catch (SQLException e) {
				//throw new DAOException("Error closing the Statement ... ", e);
			}
		}
		
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				//throw new DAOException("Error closing the Connection ... ", e);
			}
		}
	}

	private void close(Statement myStmt, ResultSet myRs) {
		close(null, myStmt, myRs);		
	}

	private void close(Statement myStmt)  {
		close(null, myStmt, null);		
	}
	
}

