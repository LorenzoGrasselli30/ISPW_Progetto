package application.model.dao;

import application.model.entity.User;

public interface UserDAO {

	User findByEmail(String formattedEmail);

	void saveNewUser(User user);
	
}
