package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.controller.application.BookingApplicationController;
import application.exception.AvailabilityException;
import application.exception.DAOException;
import application.exception.PaymentProcessingException;
import application.model.dao.ActivityDAO;
import application.model.dao.ProviderDAO;
import application.model.dao.demo.ActivityDAODemo;
import application.model.dao.demo.ProviderDAODemo;
import application.model.entity.Activity;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;

public class TestBookingApplicationController {
	
	private BookingApplicationController bookingController = new BookingApplicationController();
	ProviderDAO providerDAO = new ProviderDAODemo();
	ActivityDAO activityDAO = new ActivityDAODemo(providerDAO);
	
	@Test
    void testmakeBooking_unavailable() {
		// Verifica che viene lanciata una AvailabilityException quando il numero di biglietti richiesti non è disponibile
		ActivityDTO activity = bookingController.fetchActivityInfo("Roma: tour guidato del Colosseo", "LuigiSRL");
		
		BookingContext context = new BookingContext();
		context.setActivity(activity);
		context.setnFullTickets(4);
		context.setnReducedTickets(4);
		context.setShuttleService(false);
		context.setShuttlePrice(0.0);
		context.setGuideService(false);
		context.setGuidePrice(0.0);
		context.setTotalPrice(99.99);
		
		// Prendo un giorno valido per la prenotazione: i giorni disponibili sono tutti i giorni a partire da domani della settimana tranne domenica
		LocalDate current = java.time.LocalDate.now();
		LocalDate day;
		if (current.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
			day = current.plusDays(2);
		} else {
		    day = current.plusDays(1); //Aumenta di un giorno
		}
		context.setBookedDate(day);
		
		assertThrows(AvailabilityException.class, () -> {
			bookingController.makeBooking(context);
        });
    }
	
	@Test
    void testmakeBooking_paymentDeclined() {
		// Verifica che viene lanciata una PaymentProcessingException il saldo della carta non è sufficiente
		ActivityDTO activity = bookingController.fetchActivityInfo("Roma: tour guidato del Colosseo", "LuigiSRL");
		
		BookingContext context = new BookingContext();
		context.setActivity(activity);
		context.setnFullTickets(1);
		context.setnReducedTickets(0);
		context.setShuttleService(false);
		context.setShuttlePrice(0.0);
		context.setGuideService(false);
		context.setGuidePrice(0.0);
		context.setTotalPrice(99.99);
		
		// Prendo un giorno valido per la prenotazione: i giorni disponibili sono tutti i giorni a partire da domani della settimana tranne domenica
		LocalDate current = java.time.LocalDate.now();
		LocalDate day;
		if (current.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
			day = current.plusDays(2);
		} else {
		    day = current.plusDays(1); //Aumenta di un giorno
		}
		context.setBookedDate(day);
		
		// Inserisco una carta valida ma che non ha saldo sufficiente
		context.setCardNumber("4242424242424240");
		context.setCvv("111");
		context.setExpiredDate(day.plusYears(1));
		context.getOwnerName();
		
		assertThrows(PaymentProcessingException.class, () -> {
			bookingController.makeBooking(context);
        });
    }
}
