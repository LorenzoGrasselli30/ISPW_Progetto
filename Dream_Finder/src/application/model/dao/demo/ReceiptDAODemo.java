package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.model.dao.ReceiptDAO;
import application.model.entity.Booking;
import application.model.entity.Receipt;

public class ReceiptDAODemo implements ReceiptDAO {
	
	private Map<String, Receipt> receipts = new HashMap<>();
	
	@Override
	public Boolean saveReceipt(Receipt receipt) {
		
		receipts.put(receipt.getPaymentID(), receipt);
		return true;
	}

}
