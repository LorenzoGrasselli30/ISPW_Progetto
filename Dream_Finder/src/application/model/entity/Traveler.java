package application.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Traveler {
	
	private String email;
	private String password;
	private String username;
	private String name;
	private String surname;
	private String dob;
	
	//private List<Activity> favActivities;
	
	public Traveler(String email, String password, String username, String name, String surname, String dob) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.name= name;
		this.surname= surname;
		this.dob = dob;
		//this.favActivities = new ArrayList<>();
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
