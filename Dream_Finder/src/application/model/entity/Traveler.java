package application.model.entity;

import java.time.LocalDate;
import java.util.List;

import application.model.enums.UserRole;

public class Traveler extends User {
	
	private String username;
	private String name;
	private String surname;
	private LocalDate dob;
	
	private List<Booking> bookingHistory;
	
	public Traveler(String email, String password, String username, String name, String surname, LocalDate dob) {
		
		super(email, password, UserRole.TRAVELER);
		this.username = username;
		this.name= name;
		this.surname= surname;
		this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
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

	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}

	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
}
