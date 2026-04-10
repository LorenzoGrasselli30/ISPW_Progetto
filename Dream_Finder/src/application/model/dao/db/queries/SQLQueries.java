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
			//User
			+ " u.*,"
			//Provider
			+ " p.providerEmail, p.providerRuolo, p.providerName, p.providerType, p.location,"
			+ " p.pname, p.psurname, p.rate AS providerRate, p.nOfferedActivities,"
			//Activity
			+ " a.activityName, a.price, a.activityType, a.activityDescription, a.freeCancellation, a.payLater, a.skipLine,"
			+ " a.duration, a.timeInMinutes, a.rate AS activityRate, a.nRating, a.provider AS activityProvider"
			+ " FROM ispw.Activity a"
			+ " JOIN ispw.Provider p"
			+ " ON p.providerEmail = a.provider"
			+ " JOIN ispw.User u"
			+ " ON u.email = p.providerEmail"
			+ " AND u.ruolo = p.providerRuolo"
			+ " WHERE a.activityName = ?"
			+ " AND p.providerName = ?;";
	
	public static final String FIND_RELATED =
			" SELECT"
			//User
			+ " u.*,"
			//Provider
			+ " p.providerEmail, p.providerRuolo, p.providerName, p.providerType, p.location,"
			+ " p.pname, p.psurname, p.rate AS providerRate, p.nOfferedActivities,"
			//Activity
			+ " a.activityName, a.price, a.activityType, a.activityDescription, a.freeCancellation, a.payLater, a.skipLine,"
			+ " a.duration, a.timeInMinutes, a.rate AS activityRate, a.nRating, a.provider AS activityProvider"
			+ " FROM ispw.Activity a"
			+ " JOIN ispw.Provider p"
			+ " ON p.providerEmail = a.provider"
			+ " JOIN ispw.User u"
			+ " ON u.email = p.providerEmail"
			+ " AND u.ruolo = p.providerRuolo"
			+ " WHERE NOT ("
			+ " a.activityName = ? AND a.activityType = ? AND p.providerName = ?"
			+ " );";
	
	public static final String FIND_TRAVELER_BY_EMAIL =
			"SELECT User.email, User.password, User.ruolo,"
			+ "	Traveler.username, Traveler.travelerName, Traveler.travelerSurname, Traveler.dob"
			+ "	FROM ispw.Traveler JOIN ispw.User ON"
			+ "	Traveler.travelerEmail = user.email"
			+ " WHERE User.email = ?;";
	
	public static final String FIND_ALL_PROVIDER =
			"SELECT User.email, User.password, User.ruolo,"
			+ "	Provider.providerName,  Provider.providerType,  Provider.location,  Provider.pname,  Provider.psurname,  Provider.rate,  Provider.nOfferedActivities"
			+ "	FROM ispw.Provider, ispw.User"
			+ "	WHERE Provider.providerEmail = User.email;";
	
	public static final String FIND_ACTIVITY_BY_EMAIL =
			"SELECT"
			+ " Activity.activityName, Activity.price, Activity.activityType,"
			+ " Activity.activityDescription, Activity.freeCancellation, Activity.payLater, Activity.skipLine, Activity.duration, Activity.timeInMinutes,"
			+ " Activity.rate, Activity.nRating"
			+ " FROM ispw.Activity JOIN ispw.Provider ON"
			+ " Activity.provider = Provider.providerEmail"
			+ " WHERE Provider.providerEmail = ?;";
	
	public static final String INSERT_BOOKING =
			"INSERT INTO `ispw`.`booking` ("
			+ "	`bookingID`, "
			+ "	`bookingDate`, "
			+ "	`bookedDate`, "
			+ "	`nFullTickets`, "
			+ "	`nReducedTickets`, "
			+ "	`shuttleService`, "
			+ "	`guideService`, "
			+ "	`shuttlePrice`, "
			+ "	`guidePrice`, "
			+ "	`totalPrice`, "
			+ "	`traveler`, "
			+ "	`activity` "
			+ "	) "
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
}
