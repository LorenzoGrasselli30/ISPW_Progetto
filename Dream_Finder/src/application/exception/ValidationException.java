package application.exception;

//Eccezione del validator che ha il compito di controllare la correttezza delle email e password inserite durante il login
public class ValidationException extends Exception { 
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
	        super(message);
	    }
}
