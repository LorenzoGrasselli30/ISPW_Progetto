package application.model.dao;

import application.model.entity.Receipt;

public interface ReceiptDAO {
	
	Boolean saveReceipt(Receipt receipt);
	
}
