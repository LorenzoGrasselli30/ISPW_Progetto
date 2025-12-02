package application.configuration;

import model.entity.User;

public class UserSession { //Una sessione di un utente può essere gestita con il pattern singleton
	
	private static UserSession singletonIstance;
	private User currentUser;
	
	private UserSession() {}	
	
	public static UserSession getInstance() {
		if (singletonIstance == null) {
			singletonIstance = new UserSession();
        }
        return singletonIstance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
