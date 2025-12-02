package model.enums;

public enum UserRole {
	TRAVELER("traveler"),
    PROVIDER("provider");

    private final String displayName;

	UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
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
