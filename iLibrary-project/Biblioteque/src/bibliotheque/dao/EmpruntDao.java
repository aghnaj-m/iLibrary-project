package bibliotheque.dao;

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

import bibliotheque.connexion.Connexion;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Exemplaire;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;

public class EmpruntDao {
	
	private Connexion myConn;
	private LivreDao livredao;
	DateFormat dateFormat;
	
	public EmpruntDao(LivreDao livredao)
	{
		myConn = new Connexion();
		this.livredao = livredao;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public void modifier(Emprunt emprunt) {
		PreparedStatement myStmt = null;
		String sql = "update emprunts set date_emprunt = ? , date_retour = ? where exemplaireId = ? and adherentId = ?";
		
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setString(1, dateFormat.format(emprunt.getDate_emprunt()));
			myStmt.setString(2, dateFormat.format(emprunt.getDate_retour()));
			myStmt.setInt(3, emprunt.getIdExemplaire());
			myStmt.setString(4,emprunt.getIdAdherent());
			
			// execute SQL
			myStmt.executeUpdate();			
		}catch (SQLException e) {
			//throw new DAOException("Error updating an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	public void ajouter(Emprunt emprunt) {
		
		PreparedStatement myStmt = null;
		String sql = "insert into emprunts values (?,?,?,?)";
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setString(1, dateFormat.format(emprunt.getDate_emprunt()));
			myStmt.setString(2, dateFormat.format(emprunt.getDate_retour()));
			myStmt.setInt(3, emprunt.getIdExemplaire());
			myStmt.setString(4,emprunt.getIdAdherent());
				
			// execute SQL
			
			int etatDajout = myStmt.executeUpdate();
			
		}catch (SQLException e) {
			//throw new DAOException("Error adding an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	public List<Emprunt> getAll() {
		List<Emprunt> list = new ArrayList<Emprunt>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select * from emprunts");
			
			while (myRs.next()) {
				Emprunt tempEmprunt = convertRowToEmprunt(myRs);
				list.add(tempEmprunt);
			}
			return list;		
		}catch (SQLException e) {
			//throw new DAOException("Error searching all employees ... ", e);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		return list;
	}
	
	public List<Emprunt> userEmpruntes(String cne)
	{
		List<Emprunt> list = new ArrayList<Emprunt>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			//System.out.println("titre: "+titre+"\t auteur: "+auteur);
			myStmt = myConn.getConnexion().prepareStatement("select date_emprunt, date_retour , exemplaireId"
					+ " , adherentId from emprunts join exemplaire on emprunts.exemplaireId = exemplaire.id "
					+ "where adherentId = ? and etat = \"reserve\"");
			
			myStmt.setString(1, cne);
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Emprunt tempEmprunt = convertRowToEmprunt(myRs);
				list.add(tempEmprunt);
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return list;
	}

	public Emprunt cherchereEmprunt(String beneficiaire,int idExemplaire)
	{
		Emprunt list = null;

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.getConnexion().prepareStatement("select * from emprunts where adherentId = ? and exemplaireId = ? ");
			
			myStmt.setString(1, beneficiaire);
			myStmt.setInt(2, idExemplaire);
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				 list = convertRowToEmprunt(myRs);
				
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		return list;
	}

	
	private Emprunt convertRowToEmprunt(ResultSet myRs) throws Exception
	{
		Emprunt tempEmprunt= null;
		
		try{
			String adherent_cne = myRs.getString("adherentId");
			int exemplaireId = myRs.getInt("exemplaireId");
			Date date_emprunt = myRs.getDate("date_emprunt");
			Date date_retour = myRs.getDate("date_retour");
			Livre emprunt = livredao.livreParSonExemplaire(myRs.getInt("exemplaireId"));

			
			tempEmprunt = new Emprunt(date_emprunt, date_retour, adherent_cne, exemplaireId, emprunt);
		
		} catch (SQLException e) {
			//throw new DAOException("Error reading the ResultSet ... ", e);
			e.printStackTrace();
		}
						
		return tempEmprunt;
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
