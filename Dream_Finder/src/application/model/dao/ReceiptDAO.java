package application.model.dao;

import application.model.entity.Receipt;

public interface ReceiptDAO {
	Boolean saveReceipt(
			String PaymentID,
			String PaymentDescription,
			String PaymentOutcome, 
			String cardNumber,
			String expiredDate,
			String ownerName
			);
}
