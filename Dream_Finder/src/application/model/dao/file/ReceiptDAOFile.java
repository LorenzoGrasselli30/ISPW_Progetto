

package application.model.dao.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import application.exception.DAOException;
import application.model.dao.ReceiptDAO;
import application.model.entity.Receipt;

public class ReceiptDAOFile implements ReceiptDAO {
	
	private static final String RECEIPT_FILE_PATH = "data/Receipt.csv";
	private static final String RECEIPT_HEADER = "providerEmail,bookingID,cardNumber,expiredDate,ownerName,paymentID,paymentDescription,paymentOutcome";
	
    public ReceiptDAOFile() {
    	UtilsFile.ensureFileExists(RECEIPT_FILE_PATH, RECEIPT_HEADER);
    }
    
	@Override
	public Boolean saveReceipt(Receipt receipt) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECEIPT_FILE_PATH, true))) {
			writer.newLine();
            writer.write(receipt.getProvider().getEmail() + "," + receipt.getBookingInformation().getBookingID() 
            		+ "," + receipt.getCard().getCardNumber() + "," + receipt.getCard().getExpiredDate().toString() + "," + receipt.getCard().getOwnerName()
            		+ "," + receipt.getStripe().getPaymentID() + "," + receipt.getStripe().getPaymentDescription() + "," + receipt.getStripe().getPaymentOutcome());
        } catch (IOException e) {
        	throw new DAOException("");
        }
		
		return true;
	}

	@Override
	public Receipt findByID(String paymentID) {
		// TODO Auto-generated method stub
		return null;
	}

}
