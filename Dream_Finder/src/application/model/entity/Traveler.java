package application.model.entity;

public class Traveler {
	
	private User travelerUser;
	private String username;
	private String name;
	private String surname;
	private String dob;
	
	//private List<Activity> favActivities;
	
	public Traveler(User travelerUser, String username, String name, String surname, String dob) {
		this.setTravelerUser(travelerUser);
		this.username = username;
		this.name= name;
		this.surname= surname;
		this.dob = dob;
		//this.favActivities = new ArrayList<>();
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

	public User getTravelerUser() {
		return travelerUser;
	}

	public void setTravelerUser(User travelerUser) {
		this.travelerUser = travelerUser;
	}

}
