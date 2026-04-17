package application.model.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.exception.DAOException;
import application.model.dao.ReceiptDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Booking;
import application.model.entity.CardInformation;
import application.model.entity.Receipt;
import application.model.entity.StripeInformation;

public class ReceiptDAODB implements ReceiptDAO {

	@Override
	public Boolean saveReceipt(Receipt receipt) {
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmReceipt = conn.prepareStatement(SQLQueries.INSERT_RECEIPT)) {
			
			stmReceipt.setString(1, receipt.getStripe().getPaymentID());
			stmReceipt.setString(2, receipt.getStripe().getPaymentDescription());
			stmReceipt.setString(3, receipt.getStripe().getPaymentOutcome());
			stmReceipt.setString(4, receipt.getCard().getCardNumber());
			stmReceipt.setDate(5, receipt.getCard().getExpiredDate());
			stmReceipt.setString(6, receipt.getCard().getOwnerName());
			stmReceipt.setString(7, receipt.getBookingInformation().getBookingID());
			
			stmReceipt.executeUpdate();
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore nella generazione della ricevuta");
	    }
		
		return true;
	}

	@Override
	public Receipt findByID(String paymentID) {
		Receipt newReceipt = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmReceipt = conn.prepareStatement(SQLQueries.FIND_RECEIPT)) {
			
			stmReceipt.setString(1, paymentID);
			ResultSet rsReceipt = stmReceipt.executeQuery();
			
			while(rsReceipt.next()) {
				BookingDAODB bookingDAO = new BookingDAODB();
				Booking booking = bookingDAO.findByID(rsReceipt.getString("booking"));
				
				newReceipt = new Receipt(
						booking.getActivity().getProvider(), 
						booking, 
						new CardInformation (
								rsReceipt.getString("cardNumber"), 
								rsReceipt.getDate("expiredDate").toLocalDate(), 
								rsReceipt.getString("ownerName")
								), 
						new StripeInformation (
								rsReceipt.getString("paymentID"), 
								rsReceipt.getString("paymentDescription"), 
								rsReceipt.getString("paymentOutcome")
								)
						);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore nella ricerca della ricevuta");
	    }
		
		return newReceipt;
	}

}
