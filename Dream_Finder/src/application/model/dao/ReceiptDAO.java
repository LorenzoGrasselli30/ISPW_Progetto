package application.model.dao;

public interface ReceiptDAO {

	Boolean saveQuotation(String travelerName, String travelerSurname, String providerName, int nFullTicket,
			int nReducedTicket, Double shuttlePrice, Double guidePrice, Double totalPrice);

}
