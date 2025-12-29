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
    
	
	
}
