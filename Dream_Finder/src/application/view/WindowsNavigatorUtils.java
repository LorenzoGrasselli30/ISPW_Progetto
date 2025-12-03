package application.view;

import java.io.IOException;

import application.model.enums.UserRole;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowsNavigatorUtils {
    
	private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;
    
    private static final String BASE_PATH = System.getProperty("app.view.basePath", "/application/view/"); //Corretto da sonarcloud
    
    private static Event lastParentEvent;
    
    private WindowsNavigatorUtils() { //Corretto da sonarcloud
        throw new IllegalStateException("WindowsNavigatorUtils class");
    }
    
	//Se si ingrandisce la finestra poi la finestra viene  aperta piccola Ricordati.di.cancellare()
	public static void openWindow(Event event, String fxmlPath, String title) throws IOException {
		// Ottieni lo stage corrente
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Salva le dimensioni e la posizione correnti
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        double currentX = stage.getX();
        double currentY = stage.getY();
        
        // Carica il nuovo FXML
        Parent root = FXMLLoader.load(WindowsNavigatorUtils.class.getResource(BASE_PATH + fxmlPath));

        // Imposta la nuova scena
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        
        // Ripristina le dimensioni e la posizione
        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.setX(currentX);
        stage.setY(currentY);
        
        stage.show();
    }
	
	public static void openModalWindow(Event event, String fxmlPath, String title) throws IOException {
	//Salva il lastParentEvent per il login
	if (fxmlPath.equals("loginView.fxml")) {	
	lastParentEvent = event;
	}
	
	FXMLLoader loader = new FXMLLoader(WindowsNavigatorUtils.class.getResource(BASE_PATH + fxmlPath));
	Parent root = loader.load();
	
	Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	
	Stage modalStage = new Stage();
	modalStage.setTitle(title);
	modalStage.initOwner(parentStage);
	modalStage.initModality(Modality.WINDOW_MODAL);
	
	// Calcola il centro della finestra chiamante
    double centerX = parentStage.getX() + (parentStage.getWidth() - root.prefWidth(-1)) / 2;
    double centerY = parentStage.getY() + (parentStage.getHeight() - root.prefHeight(-1)) / 2;
    
    // Posiziona la finestra al centro
    modalStage.setX(centerX);
    modalStage.setY(centerY);
    
	modalStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
	modalStage.setResizable(false);
	
	modalStage.show();
	}
	
	public static void closeWindow(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
	
	public static void changeParentWindow(Event event, String fxmlPath, String title) throws IOException {
		//Stage corrente
        Stage modalstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //Parent window
        Stage parentStage = (Stage) modalstage.getOwner();
        
        modalstage.close();
        
        //Dimensioni della parent
        double currentWidth = parentStage.getWidth();
        double currentHeight = parentStage.getHeight();
        double currentX = parentStage.getX();
        double currentY = parentStage.getY();
        
        Parent root = FXMLLoader.load(WindowsNavigatorUtils.class.getResource(BASE_PATH + fxmlPath));
        
        parentStage.setScene(new Scene(root));
        parentStage.setTitle(title);
        
        // Ripristina le dimensioni e la posizione
        parentStage.setWidth(currentWidth);
        parentStage.setHeight(currentHeight);
        parentStage.setX(currentX);
        parentStage.setY(currentY);
        
        parentStage.show();
    }
	
	public static void loginToWindow(Event event, String userRole) throws IOException {
		//Stage corrente
        Stage modalstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //Parent window
        Stage parentStage = (Stage) modalstage.getOwner();
        
        modalstage.close();
        
        String parentTitle= ((Node) lastParentEvent.getSource()).getId();
        System.out.println(parentTitle);
        
        if ("traveler".equals(userRole)) { //Comportamento del login se l'utente è un traveler
        	
        	if ("Info Attività".equals(parentStage.getTitle()) && ("formButton".equals(parentTitle))) {
        		WindowsNavigatorUtils.openWindow(event, "paymentView.fxml", "Schermata di pagamento");
        	} else {
        		WindowsNavigatorUtils.closeWindow(event);
        	}
        	
        } else { //Comportamento del login se l'utente è un provider
        	
        	if ("Homepage".equals(parentStage.getTitle()) && ("newActivityButton".equals(parentTitle))) {
        		WindowsNavigatorUtils.openWindow(event, "newActivityView.fxml", "Nuova attività");
        	} else {
        		WindowsNavigatorUtils.changeParentWindow(event, "homeProviderView.fxml", "Homepage");
        	}
        	
        }
	}
}
