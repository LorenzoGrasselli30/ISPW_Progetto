package application.model.entity;

import java.util.List;

public class Booking {
	private String bookingID;
	
	private Traveler traveler;
	private List<GuestInformation> guests;
	private Activity activity;
	
	private int nFullTickets;
    private int nReducedTickets;
    private boolean shuttleService;
    private boolean guideService;
    private Double totalPrice;
    private String bookingDate;
    
	public Booking(String bookingID, Traveler traveler, List<GuestInformation> guests, Activity activity,
			int nFullTickets, int nReducedTickets, boolean shuttleService, boolean guideService, Double totalPrice,
			String bookingDate) {
		this.bookingID = bookingID;
		this.traveler = traveler;
		this.guests = guests;
		this.activity = activity;
		this.nFullTickets = nFullTickets;
		this.nReducedTickets = nReducedTickets;
		this.shuttleService = shuttleService;
		this.guideService = guideService;
		this.totalPrice = totalPrice;
		this.bookingDate = bookingDate;
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

	public int getnFullTickets() {
		return nFullTickets;
	}

	public void setnFullTickets(int nFullTickets) {
		this.nFullTickets = nFullTickets;
	}

	public int getnReducedTickets() {
		return nReducedTickets;
	}

	public void setnReducedTickets(int nReducedTickets) {
		this.nReducedTickets = nReducedTickets;
	}

	public boolean isShuttleService() {
		return shuttleService;
	}

	public void setShuttleService(boolean shuttleService) {
		this.shuttleService = shuttleService;
	}

	public boolean isGuideService() {
		return guideService;
	}

	public void setGuideService(boolean guideService) {
		this.guideService = guideService;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	
}
