package application.model.entity;

import java.util.List;

public class Booking {
	private String bookingID;
	
	private Traveler traveler;
	private List<GuestInformation> guests;
	private Activity activity;
	
	private BookingPriceInformation priceInformation;
	
    private String bookingDate;
    private String bookedDate;
    
	public Booking(String bookingID, Traveler traveler, List<GuestInformation> guests, Activity activity, 
			BookingPriceInformation priceInformation, String bookingDate, String bookedDate) {
		this.bookingID = bookingID;
		this.traveler = traveler;
		this.guests = guests;
		this.activity = activity;
		this.priceInformation = priceInformation;
		this.bookingDate = bookingDate;
		this.bookedDate = bookedDate;
	}

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public List<GuestInformation> getGuests() {
		return guests;
	}

	public void setGuests(List<GuestInformation> guests) {
		this.guests = guests;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public BookingPriceInformation getPriceInformation() {
		return priceInformation;
	}

	public void setPriceInformation(BookingPriceInformation priceInformation) {
		this.priceInformation = priceInformation;
	}

	public String getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}
}
