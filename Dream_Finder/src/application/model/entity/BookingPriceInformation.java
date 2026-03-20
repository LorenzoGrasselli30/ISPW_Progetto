package application.model.entity;

public class BookingPriceInformation {
	private int nFullTickets;
    private int nReducedTickets;
    private boolean shuttleService;
    private boolean guideService;
    private Double shuttlePrice;
	private Double guidePrice; 
    private Double totalPrice;
    
	public BookingPriceInformation(int nFullTickets, int nReducedTickets, boolean shuttleService, boolean guideService,
			Double totalPrice) {
		this.nFullTickets = nFullTickets;
		this.nReducedTickets = nReducedTickets;
		this.shuttleService = shuttleService;
		this.guideService = guideService;
		this.totalPrice = totalPrice;
	}
	
	
	public BookingPriceInformation(int nFullTickets, int nReducedTickets, Double shuttlePrice, Double guidePrice,
			Double totalPrice) {
		this.nFullTickets = nFullTickets;
		this.nReducedTickets = nReducedTickets;
		this.shuttlePrice = shuttlePrice;
		this.guidePrice = guidePrice;
		this.totalPrice = totalPrice;
	}

	public int getnFullTickets() {
		return nFullTickets;
	}

	public void setnFullTickets(int nFullTickets) {
		this.nFullTickets = nFullTickets;
	}

	public int getnReducedTickets() {
		return nReducedTickets;
	}

	public void setnReducedTickets(int nReducedTickets) {
		this.nReducedTickets = nReducedTickets;
	}

	public boolean isShuttleService() {
		return shuttleService;
	}

	public void setShuttleService(boolean shuttleService) {
		this.shuttleService = shuttleService;
	}

	public boolean isGuideService() {
		return guideService;
	}

	public void setGuideService(boolean guideService) {
		this.guideService = guideService;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getShuttlePrice() {
		return shuttlePrice;
	}

	public void setShuttlePrice(Double shuttlePrice) {
		this.shuttlePrice = shuttlePrice;
	}

	public Double getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Double guidePrice) {
		this.guidePrice = guidePrice;
	}
}
