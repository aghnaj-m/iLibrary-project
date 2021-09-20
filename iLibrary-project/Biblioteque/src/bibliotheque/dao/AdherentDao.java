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

import javax.swing.JOptionPane;

import com.mysql.cj.util.Util;

import bibliotheque.connexion.Connexion;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;

public class AdherentDao {

	private Connexion myConn = new Connexion();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public AdherentDao() {}
	
	public void supprimer(String cne) {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement("delete from utilisateur where cne=?");
			
			// set param
			myStmt.setString(1,cne);
			
			// execute SQL
			myStmt.executeUpdate();			
		}catch (SQLException e) {
			//throw new DAOException("Error deleting an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
	}
	
	public void modifier(Utilisateur adherent) {
		PreparedStatement myStmt = null;
		String sql = "update utilisateur set nomComplet = ?, date_naiss = ? , "
				+ "pseudo = ? where cne = ?";
		
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setString(1, adherent.getNomComplet());
			myStmt.setString(2, dateFormat.format(adherent.getDate_naiss()));
			myStmt.setString(3, adherent.getPseudo());
			myStmt.setString(4, adherent.getCne());
			
			// execute SQL
			myStmt.executeUpdate();			
		}catch (SQLException e) {
			//throw new DAOException("Error updating an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	public void ajouter(Utilisateur adherent) {
		
		PreparedStatement myStmt = null;
		String sql = "insert into utilisateur values (?,?,?,?,md5(?),0)";
		try {
			// prepare statement
			myStmt = myConn.getConnexion().prepareStatement(sql);
			
			// set params
			myStmt.setString(1, adherent.getCne());
			if(adherent.getNomComplet().equals("") || adherent.getNomComplet() == null)
				myStmt.setString(2, null);
			else
				myStmt.setString(2, adherent.getNomComplet());
			if(adherent.getDate_naiss() == null)
				myStmt.setString(3, null);
			else
				myStmt.setString(3, dateFormat.format(adherent.getDate_naiss()));
			myStmt.setString(4, adherent.getPseudo());
			myStmt.setString(5, adherent.getMot_de_pass());
				
			// execute SQL
			
			int etatDajout = myStmt.executeUpdate();
			
		}catch (SQLException e) {
			//throw new DAOException("Error adding an employee ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt);
		}
		
	}
	
	
	public List<Utilisateur> getAll() {
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select * from utilisateur order by nomComplet");
			while (myRs.next()) {
				Utilisateur tempUser = convertRowToUser(myRs);
				list.add(tempUser);
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
	

	public String chercheParPseudo(String pseudo) {

		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select * from utilisateur where pseudo = \""+pseudo+"\"");
			while (myRs.next()) {
				Utilisateur tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}
			if(list.size() == 1)
				return list.get(0).getCne();		
		}catch (SQLException e) {
			//throw new DAOException("Error searching all employees ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		return null;

    }
	
	public Utilisateur chercheParId(String cne) throws SQLException {
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.getConnexion().createStatement();
			myRs = myStmt.executeQuery("select * from utilisateur where cne = \""+cne+"\"");
			while (myRs.next()) {
				Utilisateur tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}
			if(list.size() == 1)
				return list.get(0);		
		}catch (SQLException e) {
			//throw new DAOException("Error searching all employees ... ", e);
			e.printStackTrace();
		}finally {
			close(myStmt, myRs);
		}
		return null;
    }
	
	private Utilisateur convertRowToUser(ResultSet myRs) {
		
		Utilisateur tempUser= null;
		
		try{
			String cne = myRs.getString("cne");
			String nomComplet = myRs.getString("nomComplet");
			Date date_naiss = myRs.getDate("date_naiss");
			String pseudo = myRs.getString("pseudo");
			String mot_de_pass = myRs.getString("mot_de_pass");
			byte isAdmin = myRs.getByte("adminOption");

			tempUser = new Utilisateur(cne,nomComplet,date_naiss,pseudo,mot_de_pass,isAdmin);
		
		} catch (SQLException e) {
			//throw new DAOException("Error reading the ResultSet ... ", e);
		}
						
		return tempUser;
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

