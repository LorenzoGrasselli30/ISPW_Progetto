package application.model.entity;

import java.time.LocalDate;

public class GuestInformation {
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	
	public GuestInformation(String name, String surname, LocalDate dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
}
