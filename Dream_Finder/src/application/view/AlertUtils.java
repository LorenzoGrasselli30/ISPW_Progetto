package application.view;

import java.util.Objects;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AlertUtils {
	//Impedisco la creazione di istanze
	private AlertUtils() {
	    throw new IllegalStateException("AlertUtils class");
	}
	
	private static final String CSS_PATH = "/styles/alerts.css"; 
	private static final String ICON_PATH = "/images/DF.png";
	
	private static final String BACKGROUND_COLOR = "-fx-background-color: ";
	private static final String FX_FONT =  "-fx-text-fill: white;" +
            							   "-fx-font-weight: bold;" +
            							   "-fx-font-size: 14px;" +
            							   "-fx-padding: 8 20;" +
            							   "-fx-background-radius: 5;" +
            							   "-fx-cursor: hand;" +
            							   "-fx-effect: dropshadow(three-pass-box, ";

	    public static void showAlert(Alert.AlertType alertType, String title, String message) {
	        Platform.runLater(() -> {
	            Alert alert = new Alert(alertType);
	            setupAlertStructure(alert, title, message);
	            String color = styleByAlertType(alert, alertType);
	            addCustomEffects(alert);
	            addAppIcon(alert);
	            customizeButtons(alert, color);
	            
	            alert.showAndWait();
	        });        
	    }

	    private static void setupAlertStructure(Alert alert, String title, String message) {
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.getStylesheets().add(Objects.requireNonNull(AlertUtils.class.getResource(CSS_PATH)).toExternalForm());
	        
	        Label contentLabel = new Label(message);
	        contentLabel.setWrapText(true);
	        contentLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #444; -fx-alignment: center;");
	        contentLabel.setMaxWidth(Double.MAX_VALUE); // Occupa tutto lo spazio disponibile
	        dialogPane.setContent(contentLabel);
	        
	        dialogPane.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-width: 2; -fx-alignment: center;");
	        dialogPane.setMinWidth(400);
	        dialogPane.setPrefWidth(500);
	    }

	    private static String styleByAlertType(Alert alert, Alert.AlertType type) {
	        String color = switch (type) {
	            case ERROR -> "#d32f2f";
	            case WARNING -> "#f57c00";
	            case INFORMATION -> "#1976d2";
	            case CONFIRMATION -> "#388e3c";
	            default -> throw new IllegalArgumentException("Unexpected value: " + type);
	        };
	        
	        alert.getDialogPane().setStyle("-fx-border-color: " + color + ";");
	        return color;
	    }

	    private static void addCustomEffects(Alert alert) {
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.setEffect(new DropShadow(15, Color.gray(0.3)));
	        dialogPane.setOpacity(0);
	        
	        FadeTransition ft = new FadeTransition(Duration.millis(250), dialogPane);
	        ft.setFromValue(0);
	        ft.setToValue(1);
	        alert.setOnShown(e -> ft.play());
	    }

	    private static void addAppIcon(Alert alert) {
	    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image(Objects.requireNonNull(AlertUtils.class.getResourceAsStream(ICON_PATH))));	    
	    }

	    private static void customizeButtons(Alert alert, String color) {
	    	 Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
	    	    if (okButton != null) {
	    	    	// Colore più chiaro per l'hover
	    	        String hoverColor = calculateLighterColor(color, 0.2);
	    	     // Calcola colore ombra correlato
	    	        String shadowColor = color + "80";
	    	        // Stile base
	    	        okButton.setStyle(
	    	        	BACKGROUND_COLOR + color + ";" +
	    	        	FX_FONT + shadowColor + ", 8, 0.5, 0, 1);"
	    	        );

	    	        // Effetto hover mantenendo dimensioni e colore testo
	    	        okButton.setOnMouseEntered(e -> 
	    	            okButton.setStyle(
	    	            	BACKGROUND_COLOR + hoverColor + ";" +
	    	            	FX_FONT + shadowColor + ", 12, 0.7, 0, 2);"
	    	            )
	    	        );

	    	        // Ritorno allo stato originale
	    	        okButton.setOnMouseExited(e -> 
	    	            okButton.setStyle(
	    	            	BACKGROUND_COLOR + color + ";" +	
	    	            	FX_FONT + shadowColor + ", 8, 0.5, 0, 1);"
	    	            )
	    	        );
	    	    }
	    }

	    private static String calculateLighterColor(String hexColor, double factor) {
	        try {
	            Color color = Color.web(hexColor);
	            color = color.deriveColor(0, 1, factor + 1, 1); // Aumenta la luminosità
	            return String.format("#%02x%02x%02x", 
	                (int)(color.getRed() * 255),
	                (int)(color.getGreen() * 255),
	                (int)(color.getBlue() * 255));
	        } catch (Exception e) {
	            return "#42a5f5"; // Fallback color
	        }
	    }
	    
	    
	    public static void comingSoonAlert() {
	    	showAlert(Alert.AlertType.INFORMATION, null, "Coming soon");
	    }
}
