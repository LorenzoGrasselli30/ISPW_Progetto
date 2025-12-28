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
    
    //Informazioni sul traveler che effetttua la prenotazione
    private String travelerName;
    private String travelerSurname;
    private String travelerDOB;
    
    //Informazioni per la prenotazione 
    private List<GuestInformationDTO> guests;
    
    //Informazioni della carta per la ricevuta
  	private String cardNumber;
  	private String cvv;
  	private String expiredDate;
  	private String ownerName;
  	
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
	
	public String getExpiredDate() {
		return expiredDate;
	}
	
	public void setExpiredDate(String expiredDate) {
		if (expiredDate == null || expiredDate.trim().isEmpty()) {
			throw new IllegalArgumentException("La data di nascita non può essere vuota.");
		}

		try {
			LocalDate.parse(expiredDate, DateTimeFormatter.ISO_LOCAL_DATE);
			
			// Se il parsing ha successo assegno il valore
			this.expiredDate = expiredDate;
			
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Formato data non valido. Usare il formato YYYY-MM-DD.");
		}
		
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public String getTravelerSurname() {
		return travelerSurname;
	}

	public void setTravelerSurname(String travelerSurname) {
		this.travelerSurname = travelerSurname;
	}

	public String getTravelerDOB() {
		return travelerDOB;
	}

	public void setTravelerDOB(String travelerDOB) {
		this.travelerDOB = travelerDOB;
	}
  	
}
