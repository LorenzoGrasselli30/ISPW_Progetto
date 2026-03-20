package application.model.entity;

public class ActivityRating {
	private Double rate;
	private Integer nRating;
	
	public ActivityRating(Double rate, Integer nRating) {
		this.rate = rate;
		this.nRating = nRating;
	}

	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Integer getnRating() {
		return nRating;
	}
	
	public void setnRating(Integer nRating) {
		this.nRating = nRating;
	}
}
