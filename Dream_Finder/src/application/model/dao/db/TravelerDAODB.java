package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.exception.DAOException;
import application.model.dao.TravelerDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Traveler;
import application.model.entity.User;
import application.model.enums.UserRole;

public class TravelerDAODB implements TravelerDAO {

	@Override
	public Traveler findByEmail(String formattedEmail) {
		Traveler newTraveler = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmTraveler = conn.prepareStatement(SQLQueries.FIND_TRAVELER_BY_EMAIL)) {
			
			stmTraveler.setString(1, formattedEmail);
			ResultSet rsTraveler = stmTraveler.executeQuery();
			
			while(rsTraveler.next()) {
				newTraveler = new Traveler (
						new User (
								rsTraveler.getString("email"), 
								rsTraveler.getString("password"), 
								UserRole.fromString(rsTraveler.getString("ruolo"))
								),
						rsTraveler.getString("username"),
						rsTraveler.getString("travelerName"),
						rsTraveler.getString("travelerSurname"),
						rsTraveler.getString("dob")
						);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca del traveler");
	    }
		
		return newTraveler;
	}
}
