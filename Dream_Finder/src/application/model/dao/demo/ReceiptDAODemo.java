package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.model.dao.ReceiptDAO;
import application.model.entity.Booking;
import application.model.entity.Receipt;

public class ReceiptDAODemo implements ReceiptDAO {
	
	private Map<String, Booking> receipts = new HashMap<>();
	
	@Override
	public Boolean saveReceipt(String PaymentID, String PaymentDescription, String PaymentOutcome, String cardNumber,
			String expiredDate, String ownerName) {
		// TODO Auto-generated method stub
		return true;
	}

}
