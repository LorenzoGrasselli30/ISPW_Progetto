package application.model.dao.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import application.exception.DAOException;
import application.model.dao.BookingDAO;
import application.model.entity.Activity;
import application.model.entity.Booking;
import application.model.entity.GuestInformation;
import application.model.entity.Provider;

public class BookingDAOFile implements BookingDAO {
	
	private static final String BOOKING_FILE_PATH = "data/Booking.csv";
    private static final String BOOKING_HEADER = "bookingID,bookingDate,bookedDate,nFullTickets,nReducedTickets,shuttleService,guideService,shuttlePrice,guidePrice,totalPrice,travelerEmail,activityName";
    private static final String GUEST_FILE_PATH = "data/Guest.csv";
    private static final String GUEST_HEADER = "booking,guestName,guestSurname,dob";
    
    
	public BookingDAOFile() {
    	UtilsFile.ensureFileExists(BOOKING_FILE_PATH, BOOKING_HEADER);
    	UtilsFile.ensureFileExists(GUEST_FILE_PATH, GUEST_HEADER);
    }

	@Override
	public String confirmBooking(Booking booking) {
		booking.setBookingID(UUID.randomUUID().toString());
		
		String result= booking.getBookingID();
				
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE_PATH))) {
            writer.write(booking.getBookingID() + "," + booking.getBookingDate().toString() + "," + booking.getBookedDate().toString()
            + "," + booking.getPriceInformation().getnFullTickets() + "," + booking.getPriceInformation().getnReducedTickets()
            + "," + booking.getPriceInformation().isShuttleService() + "," + booking.getPriceInformation().isGuideService()
            + "," + booking.getPriceInformation().getShuttlePrice() + "," + booking.getPriceInformation().getGuidePrice() + "," + booking.getPriceInformation().getTotalPrice()
            + "," + booking.getTraveler().getEmail() + "," + booking.getActivity().getActivityName() + "\n");
        } catch (IOException e) {
        	throw new DAOException("");
        }
		
		for (GuestInformation guest : booking.getGuests()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE_PATH))) {
	            writer.write(booking.getBookingID() + "," + guest.getName() + "," + guest.getSurname()  + "," + guest.getDateOfBirth().toString() + "\n");
	        } catch (IOException e) {
	        	throw new DAOException("");
	        }
		}
		
		
		return result;
	}

	@Override
	public Booking findByID(String bookingID) {
		// TODO Auto-generated method stub
		return null;
	}
}
