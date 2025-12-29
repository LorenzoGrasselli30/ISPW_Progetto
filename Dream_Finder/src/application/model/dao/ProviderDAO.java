package application.model.dao;

import java.util.List;

import application.model.entity.Activity;
import application.model.entity.Provider;

public interface ProviderDAO {
	List<Provider> findTopProviders();
	List<Provider> providersList();
	Provider findByActivity(Activity activity);
}
