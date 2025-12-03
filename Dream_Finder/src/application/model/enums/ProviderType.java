package application.model.enums;

public enum ProviderType {
	COMPANY("company"),
    INDIVIDUAL("individual"),
	EDU("edu");
	
    private final String stringName;

    ProviderType(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }
	    
    public static ProviderType fromString(String str) {
    	switch (str.trim().toLowerCase()) {
    	case "company":
    		return COMPANY;
    	case "individual":
    		return INDIVIDUAL;
    	case "edu":
    		return EDU;
    	default:
    		return null;
    	}
    }
}
