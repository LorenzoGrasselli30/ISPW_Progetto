package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.exception.DAOException;
import application.model.bean.ActivityDTO;
import application.model.dao.ProviderDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.entity.User;
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
						new User(
								rsTopProviders.getString("email"), 
								rsTopProviders.getString("password"), 
								UserRole.fromString(rsTopProviders.getString("ruolo"))	
								), 
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider findByActivity(Activity activity) {
		// TODO Auto-generated method stub
		return null;
	}

}
