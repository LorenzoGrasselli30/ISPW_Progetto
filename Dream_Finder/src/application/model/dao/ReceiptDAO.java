package application.model.dao;

import application.model.entity.Receipt;

public interface ReceiptDAO {
	void saveReceipt(Receipt receipt);
	Receipt findByID(String paymentID);
}
