package application.view;

import java.io.IOException;

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
    
    private WindowsNavigatorUtils() { //Corretto da sonarcloud
        throw new IllegalStateException("OpenWindowUtils class");
    }
	//Se si ingrandisce la finestra pi la finestra viene  aperta piccola Ricordati.di.cancellare()
	public static void openWindow(Event event, String fxmlPath, String title) throws IOException {
		Parent root = FXMLLoader.load(WindowsNavigatorUtils.class.getResource(BASE_PATH + fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
	
	public static void openModalWindow(Event event, String fxmlPath, String title) throws IOException {
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
	
}
