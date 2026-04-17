package application.model.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TravelerDTO {
	private String username;
	private String name;
	private String surname;
	private LocalDate dob;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDate getDob() {
		return dob;
	}
	
	public void setDob(LocalDate dob) {
		if (dob == null) {
			throw new IllegalArgumentException("La data di nascita non può essere vuota.");
		}
		
		this.dob = dob;	
	}
	
}
