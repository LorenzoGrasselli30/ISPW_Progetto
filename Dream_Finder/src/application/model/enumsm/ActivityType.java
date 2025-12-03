package application.model.enumsm;

import application.model.enums.ProviderType;

public enum ActivityType {
	CULTURE("culture"),
    NATURE("nature"),
	FOOD("food");
	
    private final String stringName;

    ActivityType(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }
	    
    public static ActivityType fromString(String str) {
    	switch (str.trim().toLowerCase()) {
    	case "culture":
    		return CULTURE;
    	case "nature":
    		return NATURE;
    	case "food":
    		return FOOD;
    	default:
    		return null;
    	}
    }
}
