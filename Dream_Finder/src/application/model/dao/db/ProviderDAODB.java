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
	
	//Va a trovare i 5 provider che hanno il rate più alto 
	@Override
	public List<Provider> findTopProviders() {
		
		List<Provider> providers= new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmTopProviders = conn.prepareStatement(SQLQueries.FIND_TOP_PROVIDERS)) {
			
			ResultSet rsTopProviders = stmTopProviders.executeQuery();
			
			while(rsTopProviders.next()) {
				Provider newProvider = new Provider(
						rsTopProviders.getString("email"), 
						rsTopProviders.getString("password"), 
						rsTopProviders.getString("providerName"), 
						ProviderType.fromString(rsTopProviders.getString("providerType")), 
						rsTopProviders.getInt("nOfferedActivities"), 
						rsTopProviders.getString("location"), 
						rsTopProviders.getString("pName"), 
						rsTopProviders.getString("pSurname")
						);
				
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
				Provider newProvider = new Provider(
						rsProviders.getString("email"), 
						rsProviders.getString("password"), 
						rsProviders.getString("providerName"), 
						ProviderType.fromString(rsProviders.getString("providerType")), 
						rsProviders.getInt("nOfferedActivities"), 
						rsProviders.getString("location"), 
						rsProviders.getString("pName"), 
						rsProviders.getString("pSurname")
						);
				
				System.out.println(rsProviders.getString("email"));
				stmActivities.setString(1, rsProviders.getString("email"));
				ResultSet rsActivities = stmActivities.executeQuery();
				
				while(rsActivities.next()) {
					Map<LocalDate, Integer> availablePlaces = new HashMap<>();
					
					stmDates.setString(1, rsActivities.getString("activityName"));
					stmDates.setString(2, rsProviders.getString("email"));
					ResultSet rsDates = stmDates.executeQuery();
					
					while(rsDates.next()) {
						   LocalDate date = rsDates.getDate("aDay").toLocalDate(); 
						   Integer places = rsDates.getInt("nPlaces");              
						   availablePlaces.put(date, places);
					}
						
					ActivityAvailableDates availableDates = new ActivityAvailableDates(availablePlaces);
					
					newProvider.addActivity(
							rsActivities.getString("activityName"), 
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
		// TODO Auto-generated method stub
		return null;
	}

}
