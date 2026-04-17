package application.model.entity;

import java.time.LocalDate;

public class CardInformation {
	private String cardNumber;
	private LocalDate expiredDate;
	private String ownerName;
	
	public CardInformation(String cardNumber, LocalDate expiredDate, String ownerName) {
		this.cardNumber = cardNumber;
		this.expiredDate = expiredDate;
		this.ownerName = ownerName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
