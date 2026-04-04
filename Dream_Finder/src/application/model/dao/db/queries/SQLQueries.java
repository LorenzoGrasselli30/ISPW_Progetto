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
}
