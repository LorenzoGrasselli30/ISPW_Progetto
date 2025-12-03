package application.model.enums;

public enum UserRole {
	TRAVELER("traveler"),
    PROVIDER("provider");

    private final String stringName;

	UserRole(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }
	    
    public static UserRole fromString(String str) {
    	switch (str.trim().toLowerCase()) {
    	case "traveler":
    		return TRAVELER;
    	case "provider":
    		return PROVIDER;
    	default:
    		return null;
    	}
    }
    
    /*
    public static List<String> getRoleDisplayNames() {
        return Arrays.stream(RoleUser.values())
                .map(RoleUser::getDisplayName)
                .collect(Collectors.toList());
    }
    */
}
