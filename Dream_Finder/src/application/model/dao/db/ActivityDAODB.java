package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

import application.exception.DAOException;
import application.model.dao.ActivityDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class ActivityDAODB implements ActivityDAO {

	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		List<Activity> topActivities = new ArrayList<>();
		
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			throw new DAOException("Errore di accesso al database");
		}
		
		for (Provider provider : providers) {
			try (PreparedStatement stmTopActivities = conn.prepareStatement(SQLQueries.FIND_TOP_ACTIVITIES);
					PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
				
				stmTopActivities.setString(1, provider.getProviderName());
				ResultSet rsTopActivities = stmTopActivities.executeQuery();
				
				while(rsTopActivities.next()) {
					
					Map<LocalDate, Integer> availablePlaces = new HashMap<>();
					
					stmDates.setString(1, rsTopActivities.getString("activityName"));
					stmDates.setString(2, provider.getProviderUser().getEmail());
					ResultSet rsDates = stmDates.executeQuery();
					
					while(rsDates.next()) {
						   LocalDate date = rsDates.getDate("aDay").toLocalDate(); 
						   Integer places = rsDates.getInt("nPlaces");              
						   availablePlaces.put(date, places);
					}
						
					ActivityAvailableDates availableDates = new ActivityAvailableDates(availablePlaces);
					
					Activity newActivity = new Activity(
							rsTopActivities.getString("activityName"), 
							rsTopActivities.getDouble("price"), 
							ActivityType.fromString(rsTopActivities.getString("activityType")), 
							provider, 
							new ActivityRating(
									rsTopActivities.getDouble("rate"), 
									rsTopActivities.getInt("nRating")
									), 
							new ActivityOtherInformation (
									rsTopActivities.getString("activityDescription"), 
									rsTopActivities.getBoolean("freeCancellation"), 
									rsTopActivities.getBoolean("payLater"), 
									rsTopActivities.getBoolean("skipLine"), 
									rsTopActivities.getInt("duration"), 
									rsTopActivities.getBoolean("timeInMinutes")
									), 
							availableDates
							);
					
					topActivities.add(newActivity);
				}
				
			} catch (SQLException e) {
		    	throw new DAOException("Errore di ricerca delle attività");
		    }
		}
		
		
		return topActivities;
	}

	@Override
	public Activity findByProvider(String activityName, String providerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> findRelatedActivities(String activityName, ActivityType activityType, String providerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
