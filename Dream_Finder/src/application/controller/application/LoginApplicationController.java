package application.controller.application;

import application.exception.ValidationException;
import application.util.Validator;

public class LoginApplicationController {

	public boolean authenticate(String formattedEmail, String fomattedPassword) throws ValidationException {

		// Validazione dei dati
        if (!Validator.isValidEmail(formattedEmail)) {
        	throw new ValidationException("Formato dell'email non valido. Esempio: name@mail.com");
        }
        
		return false;
	}

	public String getPathWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitleWindow() {
		// TODO Auto-generated method stub
		return null;
	}

}
