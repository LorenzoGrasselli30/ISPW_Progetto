package application.model.enums;

public enum PaymentErrorReason {
	CARD_EXPIRED("expired_card"),
	CARD_DECLINED("card_declined"),
	INVALID_CVV("incorrect_cvc"),
	NETWORK_ERROR("network_error"),
	TEMPORARY_ERROR("temporary_error"),
	UNKNOWN_ERROR("unknown_error");
	
	private String reason;
	
	PaymentErrorReason(String reason) {
		this.reason = reason;
	}
	
	public String getStringReason() {
	        return reason;
	    }
		    
	public static PaymentErrorReason fromString(String str) {
	    	switch (str.trim().toLowerCase()) {
	    	case "expired_card":
	    		return CARD_EXPIRED;
	    	case "card_declined":
	    		return CARD_DECLINED;
	    	case "incorrect_cvc":
	    		return INVALID_CVV;
	    	case "network_error":
	    		return NETWORK_ERROR;
	    	case "temporary_error":
	    		return TEMPORARY_ERROR;
	    	case "unknown_error":
	    		return UNKNOWN_ERROR;
	    	default:
	    		return null;
	    	}
	    }
}
