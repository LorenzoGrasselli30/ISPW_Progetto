package application.controller.application;

import application.configuration.UserSession;
import application.exception.ValidationException;
import application.util.Validator;
import model.dao.FactoryDAO;
import model.dao.TravelerDAO;
import model.dao.UserDAO;
import model.entity.User;

public class LoginApplicationController {
	
	private UserDAO userDAO;
	private UserSession userSession;

	public LoginApplicationController() {
		 this.userDAO = FactoryDAO.getFactoryInstance().getUserDAO();
		 this.userSession = UserSession.getInstance();
	 }
	
	public boolean authenticate(String formattedEmail, String formattedPassword) throws ValidationException {

		// Validazione dei dati
        if (!Validator.isValidEmail(formattedEmail)) {
        	throw new ValidationException("Formato dell'email non valido. Esempio: name@mail.com");
        }
        
        //Trovo l'utente a partire dall'email
        User user = userDAO.findByEmail(formattedEmail);
        
        //controllo che la password di quesro utente è giusta
        if ((user != null) && (formattedPassword.equals(user.getPassword()))) {
        	//setto lo UserSession a questo utente
        	userSession.setCurrentUser(user);
        } else {
        	throw new ValidationException("Invalid email or password");
        }
        
 		return true;
	}

	public String getPathWindow() {
		
		return null;
	}

	public String getTitleWindow() {
		// TODO Auto-generated method stub
		return null;
	}

}
