package application.model.dao.db.queries;

public class SQLQueries {
	private SQLQueries() {}
	
	public static final String FIND_TOP_PROVIDERS =
			"SELECT *"
			+ " FROM ispw.Provider, ispw.User"
			+ " WHERE User.email=Provider.providerEmail"
			+ " ORDER BY rate DESC LIMIT 5;";
	
	public static final String FIND_TOP_ACTIVITIES =
			"SELECT Activity.*"
			+ " FROM ispw.Activity JOIN ispw.Provider"
			+ " ON Activity.provider = Provider.providerEmail"
			+ " WHERE Provider.providerName = ?"
			+ " ORDER BY Activity.rate DESC"
			+ " LIMIT 2;";
	
	public static final String FIND_AVAILABLE_DATES =
			"SELECT *"
			+ " FROM ispw.AvailableDates"
			+ " WHERE AvailableDates.activity = ?"
			+ " AND AvailableDates.provider = ?;";
	
	public static final String FIND_USER =
			"SELECT *"
			+ "	FROM ispw.User"
			+ "	WHERE User.email = ?;";
	
	public static final String FIND_ACTIVITY_INFO =
			" SELECT"
			+ " -- User"
			+ " u.*"
			+ " -- Provider"
			+ " p.providerEmail, p.providerRuolo, p.providerName, p.providerType, p.location,"
			+ " p.pname, p.psurname, p.rate AS providerRate, p.nOfferedActivities,"
			+ " -- Activity"
			+ " a.activityName, a.price, a.activityType, a.activityDescription, a.freeCancellation, a.payLater, a.skipLine,"
			+ " a.duration, a.timeInMinutes, a.rate AS activityRate, a.nRating, a.provider AS activityProvider,"
			+ " FROM ispw.Activity a"
			+ " JOIN ispw.Provider p"
			+ " ON p.providerEmail = a.provider"
			+ " JOIN ispw.User u"
			+ " ON u.email = p.providerEmail"
			+ " AND u.ruolo = p.providerRuolo"
			+ " WHERE a.activityName = ?"
			+ " AND p.providerName = ?"
			+ " ;";
	

	
}
