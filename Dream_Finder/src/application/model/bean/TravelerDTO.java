package application.model.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TravelerDTO {
	private String username;
	private String name;
	private String surname;
	private String dob;
	
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
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		if (dob == null || dob.trim().isEmpty()) {
			throw new IllegalArgumentException("La data di nascita non può essere vuota.");
		}

		try {
			LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);
			
			// Se il parsing ha successo assegno il valore
			this.dob = dob;
			
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Formato data non valido. Usare il formato YYYY-MM-DD.");
		}
	}
	
}
