package application.controller.application;

import java.io.IOException;
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
import application.model.dao.TravelerDAO;
import application.model.entity.Activity;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;
import application.payment.StripePayment;
import application.adapter.PaymentAdapter;
import application.adapter.Target;

public class BookingApplicationController {
	
	private TravelerDAO travelerDAO;
	private ActivityDAO activityDAO;
	private BookingDAO bookingDAO;
	
	public BookingApplicationController() {
		this.travelerDAO= FactoryDAO.getFactoryInstance().getTravelerDAO();
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
		this.bookingDAO= FactoryDAO.getFactoryInstance().getBookingDAO();
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
		for (context.getGuests(): guest) {
			GuestInformation newGuest= new GuestInformation();
			
			guests.add(newGuest);
		}
		
		return true;
	}
}
