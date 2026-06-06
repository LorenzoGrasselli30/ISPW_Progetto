package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.exception.DAOException;
import application.model.dao.ReceiptDAO;
import application.model.entity.Receipt;

public class ReceiptDAODemo implements ReceiptDAO {
	
	private Map<String, Receipt> receipts = new HashMap<>();
	
	@Override
	public Boolean saveReceipt(Receipt receipt) {
		if (receipt == null) {
			throw new DAOException("Errore nella generazione della ricevuta");
		}
		
		receipts.put(receipt.getStripe().getPaymentID(), receipt);
		return true;
	}

	@Override
	public Receipt findByID(String paymentID) {
		return receipts.get(paymentID);
	}

}
