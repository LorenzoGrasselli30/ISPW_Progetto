package application.model.entity;

public class CardInformation {
	private String cardNumber;
	private String expiredDate;
	private String ownerName;
	
	public CardInformation(String cardNumber, String expiredDate, String ownerName) {
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

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
