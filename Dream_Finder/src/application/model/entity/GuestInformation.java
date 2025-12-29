package application.model.entity;

public class GuestInformation {
	private String name;
	private String surname;
	private String dateOfBirth;
	
	public GuestInformation(String name, String surname, String dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}
	
	/*
	public Boolean isReducedTicket (String dateOfBirth) {
		return null;
	}
	*/
}
