package application.model.bean;

import java.util.List;

public class BookingDTO {
	private String bookingID;
	
	private String travelerName;
	private String providerName;
	private List<GuestInformationDTO> guests;
	private String activityName;
	
	private int nFullTickets;
    private int nReducedTickets;
    private boolean shuttleService;
    private boolean guideService;
    private Double totalPrice;
    private String bookingDate;
    private String bookedDate;
    
	public String getBookingID() {
		return bookingID;
	}
	
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	
	public String getTravelerName() {
		return travelerName;
	}
	
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	
	public List<GuestInformationDTO> getGuests() {
		return guests;
	}
	
	public void setGuests(List<GuestInformationDTO> guests) {
		this.guests = guests;
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}
    
}
