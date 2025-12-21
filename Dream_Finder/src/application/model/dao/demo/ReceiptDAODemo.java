package application.model.dao.demo;

import java.util.Map;

import application.model.dao.ReceiptDAO;
import application.model.entity.Receipt;

public class ReceiptDAODemo implements ReceiptDAO {
	
	private Map<String, Receipt> quotations;
	
	@Override
	public Boolean saveQuotation(Receipt receipt) {
		//Inserire i controlli 
		quotations.put(receipt.getTravelerName()+" "+receipt.getTravelerSurname(), receipt);
		return true;
	}

}
