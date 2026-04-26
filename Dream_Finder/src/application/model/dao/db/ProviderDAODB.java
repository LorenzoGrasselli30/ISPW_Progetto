package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.exception.DAOException;
import application.model.bean.ActivityDTO;
import application.model.dao.ProviderDAO;
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

public class ProviderDAODB implements ProviderDAO {
	
	private static final String ACTIVITY_NAME_STRING = "activityName";
	
	//Va a trovare i 5 provider che hanno il rate più alto 
	@Override
	public List<Provider> findTopProviders() {
		
		List<Provider> providers= new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmTopProviders = conn.prepareStatement(SQLQueries.FIND_TOP_PROVIDERS)) {
			
			ResultSet rsTopProviders = stmTopProviders.executeQuery();
			
			while(rsTopProviders.next()) {
				Provider newProvider = this.providerHelper(rsTopProviders);
				
				providers.add(newProvider);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca del provider");
	    }
		
		return providers;
	}

	@Override
	public List<Provider> providersList() {
		List<Provider> providers= new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmProviders = conn.prepareStatement(SQLQueries.FIND_ALL_PROVIDER);
				PreparedStatement stmActivities = conn.prepareStatement(SQLQueries.FIND_ACTIVITY_BY_EMAIL);
				PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			
			ResultSet rsProviders = stmProviders.executeQuery();
			
			while(rsProviders.next()) {
				Provider newProvider = this.providerHelper(rsProviders);
				
				stmActivities.setString(1, rsProviders.getString("email"));
				ResultSet rsActivities = stmActivities.executeQuery();
				
				while(rsActivities.next()) {
					
					stmDates.setString(1, rsActivities.getString(ACTIVITY_NAME_STRING));
					stmDates.setString(2, rsProviders.getString("email"));
					ResultSet rsDates = stmDates.executeQuery();
						
					ActivityAvailableDates availableDates = this.availableDatesHelper(rsDates);
					
					newProvider.addActivity(
							rsActivities.getString(ACTIVITY_NAME_STRING), 
							rsActivities.getDouble("price"), 
							ActivityType.fromString(rsActivities.getString("activityType")), 
							new ActivityRating(
									rsActivities.getDouble("rate"), 
									rsActivities.getInt("nRating")
									), 
							new ActivityOtherInformation(
									rsActivities.getString("activityDescription"), 
									rsActivities.getBoolean("freeCancellation"), 
									rsActivities.getBoolean("payLater"), 
									rsActivities.getBoolean("skipLine"), 
									rsActivities.getInt("duration"), 
									rsActivities.getBoolean("timeInMinutes")
									), 
							availableDates
							);
				}
				
				providers.add(newProvider);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca del provider");
	    }
		
		return providers;
	}

	@Override
	public Provider findByActivity(Activity activity) {
		List<Provider> providers = this.providersList();
		for(Provider provider : providers) {
			if(provider.getActivities().contains(activity)) {
				return provider;
			}
		}
		return null;
	}

	@Override
	public Provider findByEmail(String email) {
		Provider newProvider = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmProvider = conn.prepareStatement(SQLQueries.FIND_PROVIDER_BY_EMAIL);
				PreparedStatement stmActivities = conn.prepareStatement(SQLQueries.FIND_ACTIVITY_BY_EMAIL);
				PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			
				stmProvider.setString(1, email);
				ResultSet rsProvider = stmProvider.executeQuery();
				
				while (rsProvider.next()) {
					newProvider = this.providerHelper(rsProvider);
					
					stmActivities.setString(1, email);
					ResultSet rsActivities= stmActivities.executeQuery();
					
					while(rsActivities.next()) {
						
						stmDates.setString(1, rsActivities.getString(ACTIVITY_NAME_STRING));
						stmDates.setString(2, email);
						ResultSet rsDates = stmDates.executeQuery();
							
						ActivityAvailableDates availableDates = this.availableDatesHelper(rsDates);
						
						newProvider.addActivity(
								rsActivities.getString(ACTIVITY_NAME_STRING), 
								rsActivities.getDouble("price"), 
								ActivityType.fromString(rsActivities.getString("activityType")), 
								new ActivityRating(
										rsActivities.getDouble("rate"), 
										rsActivities.getInt("nRating")
										), 
								new ActivityOtherInformation(
										rsActivities.getString("activityDescription"), 
										rsActivities.getBoolean("freeCancellation"), 
										rsActivities.getBoolean("payLater"), 
										rsActivities.getBoolean("skipLine"), 
										rsActivities.getInt("duration"), 
										rsActivities.getBoolean("timeInMinutes")
										), 
								availableDates
								);
					}
				}
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca del provider");
	    }
		
		return newProvider;
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
}
