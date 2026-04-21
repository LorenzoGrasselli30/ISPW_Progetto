package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.exception.DAOException;
import application.model.dao.UserDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.User;
import application.model.enums.UserRole;

public class UserDAODB implements UserDAO {

	@Override
	public User findByEmail(String formattedEmail) {
		
		User newUser = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmUser = conn.prepareStatement(SQLQueries.FIND_USER)) {
			
			stmUser.setString(1, formattedEmail);
			ResultSet rsUser = stmUser.executeQuery();
			
			if (rsUser.next()) {
				UserRole role = UserRole.fromString(rsUser.getString("ruolo"));

	            switch (role) {
	                case TRAVELER:
	                    newUser = new TravelerDAODB().findByEmail(formattedEmail);
	                    break;
	                case PROVIDER:
	                    newUser = new ProviderDAODB().findByEmail(formattedEmail);
	                    break;
	                default:
	                    throw new DAOException("Ruolo utente non supportato");
	            }
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca delle informazioni dell'utente");
	    }
		
		return newUser;
	}

}
