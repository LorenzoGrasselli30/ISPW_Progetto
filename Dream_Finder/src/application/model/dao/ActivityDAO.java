package application.model.dao;

import application.model.entity.Activity;

public interface ActivityDAO {
	
	Activity findTopActivities();

	void saveNewActivity();
	
}
