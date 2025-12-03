package application.model.entity;

import application.model.enums.UserRole;

public class User {
	
	private String email;
	private String password;
	private UserRole userRole;
	
	//Costruttore
	public User(String email, String password, UserRole userRole) {
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}
	
	//Getter e setter
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	
}
