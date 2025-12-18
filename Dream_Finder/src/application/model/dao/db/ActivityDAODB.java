package application.model.dao.db;

import java.util.List;

import application.model.dao.ActivityDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class ActivityDAODB implements ActivityDAO {

	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		// TODO Auto-generated method stub
		return null;
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
