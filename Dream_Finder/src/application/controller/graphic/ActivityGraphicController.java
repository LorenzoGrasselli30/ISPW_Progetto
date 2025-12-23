package application.controller.graphic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.configuration.UserSession;
import application.controller.application.ActivityApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.ReceiptDTO;
import application.model.entity.Activity;
import application.observer.Observer;
import application.observer.PriceCalculator;
import application.view.ActivityLayoutUtils;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityGraphicController implements Observer{
    
	private ActivityApplicationController activityController;
	
	//Pattern observer
	private PriceCalculator subject= new PriceCalculator();
	
	//Variabili per le immagini
	@FXML
	private ImageView mainActivityImg;
	
	@FXML
	private ImageView secondaryActivityImg1;
	
	@FXML
	private ImageView secondaryActivityImg2;
	
	@FXML
    private HBox imageGalleryContainer;
    
    @FXML
    private VBox secondaryImagesContainer;
    
    //Variabili per i campi di testo
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label ratingLabel;
    
    @FXML
    private Label nReviewsLabel;
    
    @FXML
    private Label providerLabel;
    
    @FXML
    private Label totalPriceLabel;
    
    @FXML
    private Label ppPriceLabel;
    
    @FXML
    private Label durationLabel;
    
    @FXML
    private VBox cancellationSection;
    
    @FXML
    private VBox paylaterSection;
    
    @FXML
    private HBox skiplineSection;
    
    @FXML
    private HBox relatedContainer;
    
    //Bottoni e Label per il form
    @FXML 
    private Button plusFullTicket;
    
    @FXML 
    private Button minusFullTicket;
    
    @FXML 
    private Label fullTicketLabel;
    
    @FXML 
    private Button plusReducedTicket;
    
    @FXML 
    private Button minusReducedTicket;
    
    @FXML 
    private Label reducedTicketLabel;
    
    @FXML 
    private MenuButton guideButton;
    
    //Stato interno per il calcolo del prezzo
 	private int fullTicketCount = 1; //Almeno un adulto deve essere presente
 	private int reducedTicketCount = 0;
 	private ActivityDTO currentActivity;
    private Boolean shuttleService = false;
    private Boolean guideTour = false;
    private String selectedLanguage;
    
    public ActivityGraphicController() {
		activityController = new ActivityApplicationController();
	}
	
	public void initialize() {
    	System.out.println("Initialize chiamato");
    	
    	//Gestione delle immagini
    	if (imageGalleryContainer != null && mainActivityImg != null) {
			mainActivityImg.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
			mainActivityImg.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty()
            );
			mainActivityImg.setPreserveRatio(false);

			imageGalleryContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
	            if (newScene != null) {
	                imageGalleryContainer.prefHeightProperty().bind(
	                    newScene.heightProperty().multiply(0.4)
	                );
	            }
			});
        }
        
        if (imageGalleryContainer != null && secondaryActivityImg1 != null) {
        	secondaryActivityImg1.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
        	secondaryActivityImg1.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty().multiply(0.5)
            );
        	secondaryActivityImg1.setPreserveRatio(false);
        }
        
        if (imageGalleryContainer != null && secondaryActivityImg2 != null) {
        	secondaryActivityImg2.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
        	secondaryActivityImg2.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty().multiply(0.5)
            );
        	secondaryActivityImg2.setPreserveRatio(false);
        }
	}
	
    public void selectActivityInfo(ActivityDTO activity) {
		String activityName= activity.getActivityName();
		String providerName= activity.getProviderName();
		
		ActivityDTO activityInfo= activityController.fetchActivityInfo(activityName, providerName);
		
		//Aggiorna i dati per l'observer 
		currentActivity= activityInfo;
		
		// Popola la UI con i dati recuperati
				if (titleLabel != null) {
					titleLabel.setText(activityInfo.getActivityName());
				}
				
				if (descriptionLabel != null) {
					descriptionLabel.setText(activityInfo.getDescription());
				}
				
				if (ratingLabel != null) {
					ratingLabel.setText("Punteggio: " + activityInfo.getRate() + "/5");
				}
				
				if (nReviewsLabel != null) {
					nReviewsLabel.setText("(" + activityInfo.getnRating() + " recensioni)");
				}
				
				if (providerLabel != null) {
					providerLabel.setText("Fornitore dell'attività: " + activityInfo.getProviderName());
				}
				
				if (totalPriceLabel != null) {
					totalPriceLabel.setText(String.format("%.2f€", activityInfo.getPrice()));
				}
				
				if (ppPriceLabel != null) {
					ppPriceLabel.setText(String.format("(%.2f€ a persona)", activityInfo.getPrice()));
				}
				
				if (durationLabel != null) {
					String unit = Boolean.TRUE.equals(activityInfo.getTimeInMinutes()) ? "minuti" : "ore";
					durationLabel.setText("Durata dell'attività: " + activityInfo.getDuration() + " " + unit);
				}
				if (cancellationSection != null) {
					cancellationSection.setVisible(activityInfo.getFreeCancellation());
					cancellationSection.setManaged(activityInfo.getFreeCancellation());
				}

				if (paylaterSection != null) {
					paylaterSection.setVisible(activityInfo.getPayLater());
					paylaterSection.setManaged(activityInfo.getPayLater());
				}

				if (skiplineSection != null) {
					skiplineSection.setVisible(activityInfo.getSkipLine());
					skiplineSection.setManaged(activityInfo.getSkipLine());
				}	
		
				List<ActivityDTO> relatedInfo= activityController.fetchRelatedInfo(activityInfo.getActivityName(), 
						activityInfo.getActivityType(), activityInfo.getProviderName());
				
				populateRelatedSection(relatedInfo);
				
				//Registra l'ActivityGraphicController come Observer
				subject.registerObserver(this);
	}
    
    //Pattern observer
	private void recalculateTotal() {
		System.out.println("currentBasePrice: "+currentActivity.getPrice());
		subject.calculatePrice(currentActivity, fullTicketCount, reducedTicketCount, guideTour, shuttleService);
	}
    
	//Pattern observer
	@Override
	public void update() {
		Double observerState = subject.getPrice();
		if (totalPriceLabel != null && ppPriceLabel != null) {
			totalPriceLabel.setText(String.format("%.2f€", observerState));
			ppPriceLabel.setText(String.format("(%.2f€ a persona)", observerState/(fullTicketCount+reducedTicketCount)));
		}
	}
		
    private void populateRelatedSection(List<ActivityDTO> activities) {
    	if (relatedContainer == null) {
			//System.err.println("Errore: forYouContainer non è stato inizializzato");
			return;
		}
		
		// Pulisce il container prima di popolarlo
    	relatedContainer.getChildren().clear();
		
		// Crea una card per ogni attività
		for (ActivityDTO activity : activities) {
			VBox activityCard = ActivityLayoutUtils.createActivityCard(
					activity, 
					event -> {
						handleActivityClick(event, activity);
					}, 
					event -> {
						handleHeartClick(event);
					}
			);
			relatedContainer.getChildren().add(activityCard);
		}
    }
    
    private void handleActivityClick(MouseEvent event, ActivityDTO activity) {
		try {
			WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attività", activity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	private void handleHeartClick(MouseEvent event) {
		try {
			WindowsNavigatorUtils.openModalWindow(event, "loginView.fxml", "Login", null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    @FXML
	private void useActivityForm(MouseEvent event) throws IOException {
    	 
    	    switch (((Node) event.getSource()).getId()) {
    	        case "plusFullTicket":
    	        	fullTicketCount++;
    				fullTicketLabel.setText(String.valueOf(fullTicketCount));
    				recalculateTotal();
    	        	break;
    	        case "minusFullTicket":
    	        	if(fullTicketCount > 1) {
    					fullTicketCount--;
    					fullTicketLabel.setText(String.valueOf(fullTicketCount));
    					recalculateTotal();
    				}
    	            break;
    	        case "plusReducedTicket":
    	        	reducedTicketCount++;
    				reducedTicketLabel.setText(String.valueOf(reducedTicketCount));
    				recalculateTotal();
    	        	break;
    	        case "minusReducedTicket":
    	        	if(reducedTicketCount > 0) {
    					reducedTicketCount--;
    					reducedTicketLabel.setText(String.valueOf(reducedTicketCount));
    					recalculateTotal();
    				}
    	        	break;
    	        case "shuttleServiceButton":
    	        	shuttleService = !shuttleService;
    				recalculateTotal();
    	        	break;
    	        case "guideButton":
    	        	for (MenuItem item : guideButton.getItems()) {
    					item.setOnAction(e -> {
    						selectedLanguage = item.getText();
    						if (!"Non necessaria".equals(selectedLanguage)) {
    							guideTour= true;
    						} else {
    							guideTour= false;
    						}
    						guideButton.setText(selectedLanguage); // Aggiorna il testo del bottone
    						recalculateTotal();
    					});
    				}
    	        	break;
    	    }
	}
	
	@FXML
	public void goToHomepage(MouseEvent event) throws IOException {
		String fxmlFile = "homeView.fxml";
		String title = "Homepage";
        WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
    }
	
	@FXML
	public void goToLogin(MouseEvent event) throws IOException {
		String fxmlFile = "loginView.fxml";
		String title = "Login";
		if (UserSession.getInstance().getCurrentUser() == null) {
			WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title, null);
		} else {
			AlertUtils.showAlert(AlertType.INFORMATION, title, "L'utente e' gia' loggato");
		}
    }
	
	@FXML
	public void submitActivityForm(MouseEvent event) throws IOException {
		BookingContext context= new BookingContext();
		
		context.setActivity(currentActivity);
		context.setnFullTickets(fullTicketCount);
		context.setnReducedTickets(reducedTicketCount);
		context.setGuideService(guideTour);
		context.setShuttleService(shuttleService);
		
		//Se non si muove l'observer non vengono calcolati i prezzi correttamente
		recalculateTotal();
		
		// Prende i prezzi calcolati dal Subject
		context.setTotalPrice(subject.getPrice()); 
		context.setShuttlePrice(subject.getShuttlePrice());
		context.setGuidePrice(subject.getGuidePrice());
		
		UserSession session = UserSession.getInstance();
        
        if (session.getCurrentUser() != null) { //Caso utente già loggato
            String role = session.getCurrentUser().getUserRole().getStringName();
            
            if ("traveler".equals(role)) { 
                WindowsNavigatorUtils.openFormWindow(event, "formView.fxml", "Dati sui partecipanti", context);
            } else {
                AlertUtils.showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Attenzione", "Per prenotare questa attivita' accedi o crea un account viaggiatore.");
                WindowsNavigatorUtils.openWindow(event, "homeView.fxml", "Homepage");
            }
        } else { //Caso utente non loggato
            WindowsNavigatorUtils.openModalWindow(event, "loginView.fxml", "Login", context);
        }
        
    }
	
}
