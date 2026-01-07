package application.controller.graphic;

import application.model.bean.BookingContext;
import application.model.bean.ReceiptDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReceiptGraphicController {
	
	@FXML
    private Label nameLabel;

    @FXML
    private Label providerNameLabel;

    @FXML
    private Label receiptIDLabel;

    @FXML
    private Label paymentOutcomeLabel;

    @FXML
    private Label cardNumberLabel;

    @FXML
    private Label expireLabel;
    
    @FXML
    private Label activityNameLabel;
    
    @FXML
    private Label infoLabel;

    @FXML
    private Label shuttleLabel;

    @FXML
    private Label guideLabel;

    @FXML
    private Label totalPriceLabel;
    
	public void initReceipt(ReceiptDTO receipt) {
		/*
		if (receipt == null) {
            return;
        }
	*/
        // Imposta i nomi
        if (nameLabel != null) {
            nameLabel.setText(receipt.getOwnerName() != null ? receipt.getOwnerName() : "N/D");
        }
        if (providerNameLabel != null) {
            providerNameLabel.setText(receipt.getProviderName() != null ? receipt.getProviderName() : "N/D");
        }

        // Imposta info transazione
        if (receiptIDLabel != null) {
            receiptIDLabel.setText("- ID: " + (receipt.getPaymentID() != null ? receipt.getPaymentID() : "N/D"));
        }
        if (paymentOutcomeLabel != null) {
            paymentOutcomeLabel.setText("- Stato: " + (receipt.getPaymentOutcome() != null ? receipt.getPaymentOutcome() : "N/D"));
        }

        // Imposta info pagamento
        if (cardNumberLabel != null) {
            String maskedCard = "************";
            if (receipt.getCardNumber() != null && receipt.getCardNumber().length() >= 4) {
                maskedCard += receipt.getCardNumber().substring(receipt.getCardNumber().length() - 4);
            } else {
                maskedCard += "xxxx";
            }
            cardNumberLabel.setText("Numero della carta: " + maskedCard);
        }
        
        if (expireLabel != null) {
            expireLabel.setText("Data di scadenza della carta: " + (receipt.getExpiredDate() != null ? receipt.getExpiredDate() : "N/D"));
        }

        // Imposta info prodotto
        if(receipt.getPaymentDescription() != null && !receipt.getPaymentDescription().isEmpty()) {
        	activityNameLabel.setText(receipt.getPaymentDescription());
        }
        
        if (infoLabel != null) {
            String details = "Ingresso intero: x" + receipt.getnFullTicket() + " | Ingresso ridotto: x" + receipt.getnReducedTicket();
            infoLabel.setText(details);
        }

        if (shuttleLabel != null) {
            shuttleLabel.setText("- Servizio navetta: " + (receipt.getShuttlePrice() != null ? String.format("%.2f", receipt.getShuttlePrice()) + "€" : "No"));
        }
        
        if (guideLabel != null) {
            guideLabel.setText("- Tour guidato: " + (receipt.getGuidePrice() != null ? String.format("%.2f", receipt.getGuidePrice()) + "€" : "No"));
        }

        // Imposta totale
        if (totalPriceLabel != null) {
            totalPriceLabel.setText("Totale: " + (receipt.getTotalPrice() != null ? String.format("%.2f", receipt.getTotalPrice()) : "0.00") + "€");
        }
	}

}
