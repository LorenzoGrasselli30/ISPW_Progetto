package application.controller.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.stripe.exception.StripeException;

import application.configuration.UserSession;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.GuestInformationDTO;
import application.model.bean.PaymentOutcomeDTO;
import application.model.bean.ReceiptDTO;
import application.model.bean.TravelerDTO;
import application.model.dao.ActivityDAO;
import application.model.dao.BookingDAO;
import application.model.dao.FactoryDAO;
import application.model.dao.ProviderDAO;
import application.model.dao.ReceiptDAO;
import application.model.dao.TravelerDAO;
import application.model.entity.Activity;
import application.model.entity.Booking;
import application.model.entity.GuestInformation;
import application.model.entity.Provider;
import application.model.entity.Receipt;
import application.model.entity.Traveler;
import application.model.enums.ActivityType;
import application.payment.StripePayment;
import application.adapter.PaymentAdapter;
import application.adapter.Target;

public class BookingApplicationController {
	
	private TravelerDAO travelerDAO;
	private ActivityDAO activityDAO;
	private BookingDAO bookingDAO;
	private ProviderDAO providerDAO;
	private ReceiptDAO receiptDAO;
	
	public BookingApplicationController() {
		this.travelerDAO= FactoryDAO.getFactoryInstance().getTravelerDAO();
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
		this.bookingDAO= FactoryDAO.getFactoryInstance().getBookingDAO();
		this.providerDAO= FactoryDAO.getFactoryInstance().getProviderDAO();
		this.receiptDAO= FactoryDAO.getFactoryInstance().getReceiptDAO();
	}
	
	public ActivityDTO fetchActivityInfo(String activityName, String providerName) {
		
		Activity newActivityInfo= activityDAO.findByProvider(activityName, providerName);
		
		ActivityDTO newActivityDTO= new ActivityDTO();
		newActivityDTO.setActivityName(newActivityInfo.getActivityName());
		newActivityDTO.setActivityType(newActivityInfo.getActivityType());
		newActivityDTO.setDescription(newActivityInfo.getDescription());
		newActivityDTO.setDuration(newActivityInfo.getDuration());
		newActivityDTO.setTimeInMinutes(newActivityInfo.getTimeInMinutes());
		newActivityDTO.setnRating(newActivityInfo.getnRating());
		newActivityDTO.setRate(newActivityInfo.getRate());
		newActivityDTO.setPrice(newActivityInfo.getPrice());
		newActivityDTO.setSkipLine(newActivityInfo.getSkipLine());
		newActivityDTO.setFreeCancellation(newActivityInfo.getFreeCancellation());
		newActivityDTO.setPayLater(newActivityInfo.getPayLater());
		newActivityDTO.setProviderName(newActivityInfo.getProvider().getProviderName());
		
		return newActivityDTO;
	}

	public List<ActivityDTO> fetchRelatedInfo(String activityName, ActivityType activityType, String providerName) {
		
		List<Activity> newActivities= activityDAO.findRelatedActivities(activityName, activityType, providerName);
		
		List<ActivityDTO> relatedActivity= new ArrayList();
		for (Activity activity: newActivities) {
			ActivityDTO newActivity= new ActivityDTO();
			newActivity.setActivityName(activity.getActivityName());
			newActivity.setDescription(activity.getDescription());
			newActivity.setnRating(activity.getnRating());
			newActivity.setRate(activity.getRate());
			newActivity.setPrice(activity.getPrice());
			newActivity.setProviderName(activity.getProvider().getProviderName());
			
			relatedActivity.add(newActivity);
		}
		
		return relatedActivity;
	}
	
	public TravelerDTO fetchCurrentTraveler(UserSession userSession) {
		String email= userSession.getCurrentUser().getEmail();
		
		Traveler newTraveler= travelerDAO.findByEmail(email);
		
		TravelerDTO currentTraveler= new TravelerDTO();
		
		currentTraveler.setName(newTraveler.getName());
		currentTraveler.setSurname(newTraveler.getSurname());
		currentTraveler.setDob(newTraveler.getDob());
		
		return currentTraveler;
	}
	
	//Client che chiama l'istanza di Adapteer
	public BookingContext makeBooking(BookingContext context) {
		
		//Pagamento dell'attività
		Target paymentTarget= new PaymentAdapter(new StripePayment());
		
		PaymentOutcomeDTO paymentInfo= paymentTarget.verifyPayment(context.getCardNumber(), context.getExpiredDate(), context.getCvv(), context.getActivity().getActivityName(), 
				context.getOwnerName(), context.getActivity().getProviderName(), context.getTotalPrice());
		
		System.out.println(paymentInfo.getPaymentID());
		System.out.println(paymentInfo.getPaymentOutcome());
		
		if (!paymentInfo.getPaymentOutcome().equals("succeeded")) {
			//Fai qualcosa per tornare indietro nella prenotazione 
			return null;
		}
		
		//Prenotazione dell'attività
		Activity bookedActivity= activityDAO.findByProvider(context.getActivity().getActivityName(), context.getActivity().getProviderName());
		Traveler currentTraveler= travelerDAO.findByEmail(UserSession.getInstance().getCurrentUser().getEmail());
		
		List<GuestInformation> guests= new ArrayList();
		
		for (GuestInformationDTO guest: context.getGuests()) {
			GuestInformation newGuest= new GuestInformation(guest.getName(), guest.getSurname(), guest.getDateOfBirth());
			
			guests.add(newGuest);
		}
		
		String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Booking newBooking= new Booking(
				null,
				currentTraveler,
				guests,
				bookedActivity,
				context.getnFullTickets(),
				context.getnReducedTickets(),
				context.isShuttleService(),
				context.isGuideService(),
				context.getTotalPrice(),
				currentDateTime
				);
		
		String bookingResult= bookingDAO.confirmBooking(newBooking);
		
		//Salvataggio della ricevuta
		Provider currentProvider = providerDAO.findByActivity(bookedActivity);
		
		Receipt receipt= new Receipt(
				currentProvider,
				context.getnFullTickets(),
				context.getnReducedTickets(),
				context.getShuttlePrice(),
				context.getGuidePrice(),
				context.getTotalPrice(),
				context.getCardNumber(),
				context.getExpiredDate(),
				context.getOwnerName(),
				paymentInfo.getPaymentID(),
				paymentInfo.getPaymentDescription(),
				paymentInfo.getPaymentOutcome()
				);
		
		Boolean receiptResult= receiptDAO.saveReceipt(receipt);
		
		context.setPaymentID(paymentInfo.getPaymentID());
		context.setBookingID(bookingResult);
		
		// DEBUG PRINT START
				System.out.println("---------- BOOKING CONTEXT DUMP ----------");
				System.out.println("Owner Name: " + context.getOwnerName());
				System.out.println("Card Number: " + context.getCardNumber());
				System.out.println("Expired Date: " + context.getExpiredDate());
				System.out.println("CVV: " + context.getCvv());
				System.out.println("Activity Name: " + (context.getActivity() != null ? context.getActivity().getActivityName() : "NULL"));
				System.out.println("Provider Name: " + (context.getActivity() != null ? context.getActivity().getProviderName() : "NULL"));
				System.out.println("Full Tickets: " + context.getnFullTickets());
				System.out.println("Reduced Tickets: " + context.getnReducedTickets());
				System.out.println("Shuttle Service: " + context.isShuttleService());
				System.out.println("Shuttle Price: " + context.getShuttlePrice());
				System.out.println("Guide Service: " + context.isGuideService());
				System.out.println("Guide Price: " + context.getGuidePrice());
				System.out.println("Total Price: " + context.getTotalPrice());
				System.out.println("Payment ID: " + context.getPaymentID());
				System.out.println("Booking ID: " + context.getBookingID());
				System.out.println("Number of Guests: " + (context.getGuests() != null ? context.getGuests().size() : "0"));
				System.out.println("------------------------------------------");
				// DEBUG PRINT END
				
		return context;
	}

	public ReceiptDTO fetchCurrentReceipt(String paymentID) {
		ReceiptDTO result= new ReceiptDTO();
		
		Receipt reciptFounded= receiptDAO.findByID(paymentID);
		
		result.setProviderName(reciptFounded.getProvider().getProviderName());
		result.setnFullTicket(reciptFounded.getnFullTicket());
		result.setnReducedTicket(reciptFounded.getnReducedTicket());
		result.setTotalPrice(reciptFounded.getTotalPrice());
		result.setGuidePrice(reciptFounded.getGuidePrice());
		result.setShuttlePrice(reciptFounded.getShuttlePrice());
		
		result.setCardNumber(reciptFounded.getCardNumber());
		result.setExpiredDate(reciptFounded.getExpiredDate());
		result.setOwnerName(reciptFounded.getOwnerName());
		
		result.setPaymentID(reciptFounded.getPaymentID());
		result.setPaymentDescription(reciptFounded.getPaymentDescription());
		result.setPaymentOutcome(reciptFounded.getPaymentOutcome());
		
		return result;
	}

	public BookingDTO fetchCurrentTicket(String bookingID) {
		BookingDTO result= new BookingDTO();
		
		Booking bookingFounded= bookingDAO.findByID(bookingID);
		
		result.setBookingID(bookingFounded.getBookingID());
		
		result.setTravelerName(bookingFounded.getTraveler().getName());
		
		List<GuestInformationDTO> guests= new ArrayList();
		
		for (GuestInformation guest: bookingFounded.getGuests()) {
			GuestInformationDTO newGuest= new GuestInformationDTO();
			
			newGuest.setName(guest.getName());
			newGuest.setSurname(guest.getSurname());
			newGuest.setDateOfBirth(guest.getDateOfBirth());
			
			guests.add(newGuest);
		}
		result.setGuests(guests);
		
		result.setActivityName(bookingFounded.getActivity().getActivityName());
		
		result.setnFullTickets(bookingFounded.getnFullTickets());
		result.setnReducedTickets(bookingFounded.getnReducedTickets());
		result.setShuttleService(bookingFounded.isShuttleService());
		result.setGuideService(bookingFounded.isGuideService());
		result.setTotalPrice(bookingFounded.getTotalPrice());
		result.setBookingDate(bookingFounded.getBookingDate());
		
		return result;
	}
	
	
}
