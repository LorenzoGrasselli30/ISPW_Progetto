package application.model.dao;

import java.util.List;

import application.model.entity.Activity;

public interface ActivityDAO {
	
	List<Activity> findTopActivities();

	void saveNewActivity();
	
}
