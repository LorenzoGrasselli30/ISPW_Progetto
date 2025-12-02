package model.dao;

import model.entity.User;

public interface UserDAO {

	User findByEmail(String formattedEmail);

	void saveNewUser(User user);
	
}
