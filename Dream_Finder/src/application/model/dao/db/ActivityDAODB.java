package application.model.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import application.exception.DAOException;
import application.model.dao.ActivityDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.entity.User;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;
import application.model.enums.UserRole;

public class ActivityDAODB implements ActivityDAO {
	
	private static final String ACTIVITY_NAME_STRING = "activityName";
	private static final String EMAIL_STRING = "email";

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
			
			try (PreparedStatement stmTopActivities = conn.prepareStatement(SQLQueries.FIND_TOP_ACTIVITIES)) {
				
				stmTopActivities.setString(1, provider.getProviderName());
				ResultSet rsTopActivities = stmTopActivities.executeQuery();
				
				while(rsTopActivities.next()) {
					Activity newActivity = this.activityHelper(rsTopActivities, provider);
					
					topActivities.add(newActivity);
				}
				
			} catch (SQLException e) {
		    	throw new DAOException("Errore di ricerca delle attività");
		    }
		}
		
		for (Activity activity : topActivities) {
			
			try (PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
				stmDates.setString(1, activity.getActivityName());
				stmDates.setString(2, activity.getProvider().getEmail());
				ResultSet rsDates = stmDates.executeQuery();
				
				activity.setAvaibleDates(this.availableDatesHelper(rsDates));
				
			} catch (SQLException e) {
		    	throw new DAOException("Errore di ricerca delle attività");
		    }
		}

		return topActivities;
	}

	@Override
	public Activity findByProvider(String activityName, String providerName) {
		Activity activityFounded = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmActivityInfo = conn.prepareStatement(SQLQueries.FIND_ACTIVITY_INFO);
				PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			
			stmActivityInfo.setString(1, activityName);
			stmActivityInfo.setString(2, providerName);
			ResultSet rsActivityInfo = stmActivityInfo.executeQuery();
			
			while(rsActivityInfo.next()) {
				
				stmDates.setString(1, rsActivityInfo.getString(ACTIVITY_NAME_STRING));
				stmDates.setString(2, rsActivityInfo.getString(EMAIL_STRING));
				ResultSet rsDates = stmDates.executeQuery();
					
				ActivityAvailableDates availableDates = this.availableDatesHelper(rsDates);
				
				Provider provider = this.providerHelper(rsActivityInfo);
				
				activityFounded = this.activityHelper(rsActivityInfo, provider, availableDates);
				
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore nel caricamento dell'attività corrispondente");
	    }
		
		return activityFounded;
	}

	@Override
	public List<Activity> findRelatedActivities(String activityName, ActivityType activityType, String providerName) {
		//Lista che comprende il risultato finale 
		List<Activity> relatedActivities = new ArrayList<>();
		
		// Divide le attività in tre gruppi di priorità
		List<Activity> highScore = new ArrayList<>(); // Stesso provider e stesso tipo
		List<Activity> mediumScore = new ArrayList<>(); // Solo stesso provider o stesso tipo
		List<Activity> others = new ArrayList<>(); // Tutte le altre
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmActivityRelated = conn.prepareStatement(SQLQueries.FIND_RELATED);
				PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			
			stmActivityRelated.setString(1, activityName);
			stmActivityRelated.setString(2, activityType.toString());
			stmActivityRelated.setString(3, providerName);
			
			ResultSet rsActivityRelated = stmActivityRelated.executeQuery();
			
			while(rsActivityRelated.next()) {
				
				stmDates.setString(1, rsActivityRelated.getString(ACTIVITY_NAME_STRING));
				stmDates.setString(2, rsActivityRelated.getString(EMAIL_STRING));
				ResultSet rsDates = stmDates.executeQuery();
				
				ActivityAvailableDates availableDates = this.availableDatesHelper(rsDates);
				
				Provider provider = this.providerHelper(rsActivityRelated);
				
				Activity activity = this.activityHelper(rsActivityRelated, provider, availableDates);
				
				if (activity.calculateRelevanceScore(activity, activityType, providerName)==2) {
					highScore.add(activity);
				} else if (activity.calculateRelevanceScore(activity, activityType, providerName)==1) {
					mediumScore.add(activity);
				} else {
					others.add(activity);
				}
				
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore nel caricamento delle attività correlate");
	    }
		
		relatedActivities.addAll(highScore);
		relatedActivities.addAll(mediumScore);
		relatedActivities.addAll(others);
		
		// Limita a 10 attività
		return relatedActivities.stream()
			.limit(10)
			.collect(Collectors.toList());
	}

	@Override
	public boolean reservePlaces(Activity activity, LocalDate day, Integer requestedPlaces) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmReservePlaces = conn.prepareStatement(SQLQueries.UPDATE_PLACES)) {
			
			Integer currentPlaces = activity.getAvaibleDates().getAvaiblePlaces().get(day);
			
			stmReservePlaces.setInt(1, (currentPlaces - requestedPlaces));
			stmReservePlaces.setString(2, activity.getActivityName());
			stmReservePlaces.setString(3, activity.getProvider().getEmail());
			stmReservePlaces.setDate(4, Date.valueOf(day));
			
			
			stmReservePlaces.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Errore il giorno o l'attività richiesta per la prenotazione non è stata trovata");
	    }
		return true;
	}
	
	//Helpers
	
	private Provider providerHelper(ResultSet rs) throws SQLException {
        return new Provider(
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("providerName"),
            ProviderType.fromString(rs.getString("providerType")),
            rs.getInt("nOfferedActivities"),
            rs.getString("location"),
            rs.getString("pname"),
            rs.getString("psurname")
        );
    }
	
	private ActivityAvailableDates availableDatesHelper(ResultSet rs) throws SQLException {
		Map<LocalDate, Integer> availablePlaces = new HashMap<>();
		
		while(rs.next()) {
			   LocalDate date = rs.getDate("aDay").toLocalDate(); 
			   Integer places = rs.getInt("nPlaces");              
			   availablePlaces.put(date, places);
		}
			
		return new ActivityAvailableDates(availablePlaces);
    }
	
	private Activity activityHelper(ResultSet rs, Provider provider) throws SQLException {
		return new Activity(
				rs.getString("activityName"), 
				rs.getDouble("price"), 
				ActivityType.fromString(rs.getString("activityType")), 
				provider, 
				new ActivityRating(
						rs.getDouble("activityRate"), 
						rs.getInt("nRating")
						), 
				new ActivityOtherInformation (
						rs.getString("activityDescription"), 
						rs.getBoolean("freeCancellation"), 
						rs.getBoolean("payLater"), 
						rs.getBoolean("skipLine"), 
						rs.getInt("duration"), 
						rs.getBoolean("timeInMinutes")
						), 
				null
				); 
	}
	
}
