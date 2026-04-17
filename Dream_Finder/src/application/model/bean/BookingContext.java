package application.model.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BookingContext {
	private ActivityDTO activity;
    private int nFullTickets;
    private int nReducedTickets;
    private boolean shuttleService;
    private Double shuttlePrice;
    private boolean guideService;
    private Double guidePrice;
    private Double totalPrice;
    private LocalDate bookedDate;
    
    //Informazioni per la prenotazione 
    private List<GuestInformationDTO> guests;
    
    //Informazioni della carta per la ricevuta
  	private String cardNumber;
  	private String cvv;
  	private LocalDate expiredDate;
  	private String ownerName;
  	
  	private String paymentID;
  	private String bookingID;
  	
	public ActivityDTO getActivity() {
		return activity;
	}
	
	public void setActivity(ActivityDTO activity) {
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
	
	public Double getShuttlePrice() {
		return shuttlePrice;
	}
	
	public void setShuttlePrice(Double shuttlePrice) {
		this.shuttlePrice = shuttlePrice;
	}
	
	public boolean isGuideService() {
		return guideService;
	}
	
	public void setGuideService(boolean guideService) {
		this.guideService = guideService;
	}
	
	public Double getGuidePrice() {
		return guidePrice;
	}
	
	public void setGuidePrice(Double guidePrice) {
		this.guidePrice = guidePrice;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public List<GuestInformationDTO> getGuests() {
		return guests;
	}
	
	public void setGuests(List<GuestInformationDTO> guests) {
		this.guests = guests;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		if (!cardNumber.matches("\\d{16}")) {
			throw new IllegalArgumentException("Il numero della carta deve essere composto da esattamente 16 cifre numeriche");
		}
		this.cardNumber = cardNumber;
	}
	
	public String getCvv() {
		return cvv;
	}
	
	public void setCvv(String cvv) {
		if (!cvv.matches("\\d{3}")) {
			throw new IllegalArgumentException("Il CVV deve essere composto da esattamente 3 cifre numeriche");
		}
		this.cvv = cvv;
	}
	
	public LocalDate getExpiredDate() {
		return expiredDate;
	}
	
	public void setExpiredDate(LocalDate expiredDate) {
		if (expiredDate == null) {
			throw new IllegalArgumentException("La data di nascita non può essere vuota.");
		}
		
		this.expiredDate = expiredDate;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public LocalDate getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDate bookedDate) {
		this.bookedDate = bookedDate;
	}
  	
}
