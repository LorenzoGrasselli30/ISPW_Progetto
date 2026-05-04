package application.model.entity;

public class ProviderPersonalInfo {
	private String location;
	private String name;
	private String surname;
	
	public ProviderPersonalInfo(String location, String name, String surname) {
		this.location = location;
		this.name = name;
		this.surname = surname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	
	
}
