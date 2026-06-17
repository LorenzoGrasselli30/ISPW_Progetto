package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.exception.DAOException;
import application.model.dao.ReceiptDAO;
import application.model.entity.Receipt;

public class ReceiptDAODemo implements ReceiptDAO {
	
	private Map<String, Receipt> receipts = new HashMap<>();
	
	@Override
	public void saveReceipt(Receipt receipt) {
		receipts.put(receipt.getStripe().getPaymentID(), receipt);
	}

	@Override
	public Receipt findByID(String paymentID) {
		return receipts.get(paymentID);
	}

}
