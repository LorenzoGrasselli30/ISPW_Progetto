package application.controller.application;

import application.configuration.UserSession;
import application.model.bean.TravelerDTO;
import application.model.dao.FactoryDAO;
import application.model.dao.TravelerDAO;
import application.model.entity.Traveler;

public class BookingApplicationController {
	
	private TravelerDAO travelerDAO;
	
	public BookingApplicationController() {
		this.travelerDAO= FactoryDAO.getFactoryInstance().getTravelerDAO();
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
	
	
}
