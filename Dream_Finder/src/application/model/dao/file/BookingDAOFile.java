package application.model.dao.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import application.exception.DAOException;
import application.model.dao.BookingDAO;
import application.model.entity.Activity;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Booking;
import application.model.entity.BookingPriceInformation;
import application.model.entity.GuestInformation;
import application.model.entity.Provider;
import application.model.entity.Traveler;
import application.model.enums.ActivityType;

public class BookingDAOFile implements BookingDAO {
	
	private static final String BOOKING_FILE_PATH = "data/Booking.csv";
    private static final String BOOKING_HEADER = "bookingID,bookingDate,bookedDate,nFullTickets,nReducedTickets,shuttleService,guideService,shuttlePrice,guidePrice,totalPrice,travelerEmail,activityName";
    private static final String GUEST_FILE_PATH = "data/Guest.csv";
    private static final String GUEST_HEADER = "booking,guestName,guestSurname,dob";
	private static final String TRAVELER_FILE_PATH = "data/Traveler.csv";
    private static final String TRAVELER_HEADER = "email,password,username,name,surname,dob";
    private static final String ACTIVITY_FILE_PATH = "data/Activity.csv";
    private static final String ACTIVITY_HEADER = "providerEmail,activityName,price,activityType,rating,nRating,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    private static final String DATES_FILE_PATH = "data/AvailableDates.csv";
    private static final String DATES_HEADER = "activityName,providerEmail,aDay,nPlaces";
    
	public BookingDAOFile() {
    	UtilsFile.ensureFileExists(BOOKING_FILE_PATH, BOOKING_HEADER);
    	UtilsFile.ensureFileExists(GUEST_FILE_PATH, GUEST_HEADER);
    	UtilsFile.ensureFileExists(TRAVELER_FILE_PATH, TRAVELER_HEADER);
    	UtilsFile.ensureFileExists(ACTIVITY_FILE_PATH, ACTIVITY_HEADER);
    	UtilsFile.ensureFileExists(DATES_FILE_PATH, DATES_HEADER);
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
		Booking newBooking = null;
		String line;
		
		try (BufferedReader bookingReader = new BufferedReader(new FileReader(BOOKING_FILE_PATH));
				BufferedReader guestsReader = new BufferedReader(new FileReader(GUEST_FILE_PATH));
				BufferedReader travelerReader = new BufferedReader(new FileReader(TRAVELER_FILE_PATH));
				BufferedReader activityReader = new BufferedReader(new FileReader(ACTIVITY_FILE_PATH));
				BufferedReader datesReader = new BufferedReader(new FileReader(DATES_FILE_PATH))) {
			
			String travelerEmail = null;
			String activityName = null;
			
			while ((line = bookingReader.readLine()) != null) {
            	String[] parts = line.split(",");
            	if (parts[0].equals(bookingID)) {
            		travelerEmail = parts[10];
            		activityName = parts[11];
            				
            		newBooking = new Booking(
            				bookingID, 
            				null, //traveler
            				null, //guests
            				null, //activity
            				new BookingPriceInformation(
            						Integer.parseInt(parts[3]), //nFullTickets
            						Integer.parseInt(parts[4]), //nReducedTickets
            						Boolean.parseBoolean(parts[5]), //shuttleService
            						Boolean.parseBoolean(parts[6]), //guideService
            						Double.parseDouble(parts[7]), //shuttlePrice
            						Double.parseDouble(parts[8]), //guidePrice
            						Double.parseDouble(parts[9])  //totalPrice
            						), 
            				LocalDate.parse(parts[1]), //bookingDate
            				LocalDate.parse(parts[2])  //bookedDate
            				);
            		
            	}
            }
			
			//Recupero informazioni dei partecipanti
			List<GuestInformation> guests = new ArrayList<>();
			while ((line = guestsReader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].equals(bookingID)) {
					GuestInformation guest = new GuestInformation(
							parts[1], 
							parts[2], 
							LocalDate.parse(parts[3])
							);
					
					guests.add(guest);
				}
			}
			
			//Recupero del traveler che ha prenotato
			Traveler traveler = null;
			while ((line = travelerReader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].equals(travelerEmail)) {
					traveler = new Traveler(parts[0], parts[1], parts[2], parts[3], parts[4], LocalDate.parse(parts[5]));
				}
			}
			
			//Recupero dell'attività prenotata
			Activity activity = null;
			while ((line = activityReader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[1].equals(activityName)) {
					activity = new Activity(
							parts[1], //activityName
							Double.parseDouble(parts[2]), //price
							ActivityType.fromString(parts[3]), //activityType
							null, 
							new ActivityRating(
									Double.parseDouble(parts[4]), //rate
									Integer.parseInt(parts[5]) //nRating
									), 
							new ActivityOtherInformation (
									parts[6], //description
									Boolean.parseBoolean(parts[7]),  // freeCancellation
									Boolean.parseBoolean(parts[8]),  // bookNowPayLater
									Boolean.parseBoolean(parts[9]),  // skipTheLine
									Integer.parseInt(parts[10]),     // duration
									Boolean.parseBoolean(parts[11])  // durationInMinutes
									), 
							null
							); 
				}
			}
			
			//Recupero delle date disponibili dell'attività
			activity.setAvaibleDates(UtilsFile.availableDatesHelper(activity));
			
			newBooking.setActivity(activity);
			newBooking.setGuests(guests);
			newBooking.setTraveler(traveler);
			
    	} catch (IOException e) {
        	throw new DAOException("");
        }
		
		return newBooking;
	}
}
