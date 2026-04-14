package application.model.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.exception.DAOException;
import application.model.dao.ReceiptDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Receipt;

public class ReceiptDAODB implements ReceiptDAO {

	@Override
	public Boolean saveReceipt(Receipt receipt) {
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmReceipt = conn.prepareStatement(SQLQueries.INSERT_RECEIPT)) {
			
			stmReceipt.setString(1, receipt.getStripe().getPaymentID());
			stmReceipt.setString(2, receipt.getStripe().getPaymentDescription());
			stmReceipt.setString(3, receipt.getStripe().getPaymentOutcome());
			stmReceipt.setString(4, receipt.getCard().getCardNumber());
			stmReceipt.setString(5, receipt.getCard().getExpiredDate());
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
		// TODO Auto-generated method stub
		return null;
	}

}
