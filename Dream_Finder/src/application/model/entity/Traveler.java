package application.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Traveler {
	
	private String email;
	private String password;
	private String username;
	private String dob;
	
	private List<Activity> favActivities;
	
	public Traveler(String email, String password, String username, String dob) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.dob = dob;
		this.favActivities = new ArrayList<>();
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

}
