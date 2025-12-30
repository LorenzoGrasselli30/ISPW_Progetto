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
import application.model.bean.GuestInformationDTO;
import application.model.bean.PaymentOutcomeDTO;
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
	public Boolean makeBooking(BookingContext context) {
		
		//Pagamento dell'attività
		Target paymentTarget= new PaymentAdapter(new StripePayment());
		
		PaymentOutcomeDTO paymentInfo= paymentTarget.verifyPayment(context.getCardNumber(), context.getExpiredDate(), context.getCvv(), context.getActivity().getActivityName(), 
				context.getOwnerName(), context.getActivity().getProviderName(), context.getTotalPrice());
		
		System.out.println(paymentInfo.getPaymentID());
		
		if (!paymentInfo.getPaymentID().equals("Succeded")) {
			//Fai qualcosa per tornare indietro nella prenotazione 
			return false;
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
		
		Boolean bookingResult= bookingDAO.confirmBooking(newBooking);
		
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
		
		return true;
	}
	
	
}
