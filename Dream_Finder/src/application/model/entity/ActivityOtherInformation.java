package application.model.entity;

public class ActivityOtherInformation {
	private String description;
	private Boolean freeCancellation;
	private Boolean payLater;
	private Boolean skipLine;
	
	private Integer duration;
	private Boolean timeInMinutes; //true il tempo è in minuti
	
	
	
	public ActivityOtherInformation(String description, Boolean freeCancellation, Boolean payLater, Boolean skipLine,
			Integer duration, Boolean timeInMinutes) {
		this.description = description;
		this.freeCancellation = freeCancellation;
		this.payLater = payLater;
		this.skipLine = skipLine;
		this.duration = duration;
		this.timeInMinutes = timeInMinutes;
	}

	public Boolean getFreeCancellation() {
		return freeCancellation;
	}
	
	public void setFreeCancellation(Boolean freeCancellation) {
		this.freeCancellation = freeCancellation;
	}
	
	public Boolean getPayLater() {
		return payLater;
	}
	
	public void setPayLater(Boolean payLater) {
		this.payLater = payLater;
	}
	
	public Boolean getSkipLine() {
		return skipLine;
	}
	
	public void setSkipLine(Boolean skipLine) {
		this.skipLine = skipLine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getTimeInMinutes() {
		return timeInMinutes;
	}

	public void setTimeInMinutes(Boolean timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}
}
