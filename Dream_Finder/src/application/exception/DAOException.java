package application.exception;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}
