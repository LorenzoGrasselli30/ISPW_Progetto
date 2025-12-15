package application.model.dao;

import java.util.List;

import application.model.entity.Activity;
import application.model.entity.Provider;

public interface ActivityDAO {
	
	List<Activity> findTopActivities(List<Provider> providers);

	void saveNewActivity();
	
}
