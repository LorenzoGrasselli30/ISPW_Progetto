package application.model.entity;

public class ProviderRating {
	private Double providerRate;
	private Integer nOfferedActivities;
	
	public ProviderRating(Double providerRate, Integer nOfferedActivities) {
		this.providerRate = providerRate;
		this.nOfferedActivities = nOfferedActivities;
	}

	public Double getProviderRate() {
		return providerRate;
	}

	public void setProviderRate(Double providerRate) {
		this.providerRate = providerRate;
	}

	public Integer getnOfferedActivities() {
		return nOfferedActivities;
	}

	public void setnOfferedActivities(Integer nOfferedActivities) {
		this.nOfferedActivities = nOfferedActivities;
	}
}
