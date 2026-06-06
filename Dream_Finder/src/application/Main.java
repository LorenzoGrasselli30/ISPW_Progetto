package application;
	
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.configuration.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import application.view.cli.*;

public class Main extends Application {
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/homeView.fxml"));
			Scene scene = new Scene(root, 640, 480);
			primaryStage.setTitle("Homepage");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Main exception", e);
		}
	}
	
	public static void main(String[] args) {
		//Bisogna creare un modo per scegliere GUI o CLI e successivamente bisogna chiedere se si vuole avviare l'applicazione con DB, Demo, File 
		String visual = null;
		String mode = null;
		Scanner scanner= new Scanner(System.in); 
		
		try {
			System.out.println("Scegli l'interfaccia da utilizzare: GUI/CLI (Invio per versione di default)");
			while (true) {
				visual = scanner.nextLine().trim().toUpperCase();
				if (("GUI".equals(visual)) || ("CLI".equals(visual)) || ("".equals(visual))) {	
					break;
				}
				System.out.println("Inserisci un'interfaccia valida: GUI/CLI");
			}
			
			System.out.println("Scegli la modalità da utilizzare: db/demo/file (Invio per versione demo)");
			while (true) {
				mode = scanner.nextLine().trim().toLowerCase();
				if (("db".equals(mode)) || ("demo".equals(mode)) || ("file".equals(mode)) || ("".equals(mode))) {
					if (!("".equals(mode))) {
					AppConfig.initMode(mode);//Inizializzazione della modalità
					}
					AppConfig startMode= AppConfig.getInstance();
					System.out.println("Avvio la modalità: " + startMode.getMode());
					
					break;
				}
				System.out.println("Inserisci un'interfaccia valida: db/demo/file");	
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Main exception", e);
		}
		
		if ("GUI".equals(visual)) {
			launch(args); //Chiama il metodo start della GUI
		} else if ("".equals(visual)) {
			launch(args); //Chiama il metodo start della GUI
		} else {
			NavigatorCLI navigator = new NavigatorCLIImplementation();
			new HomepageCLIView(navigator).start();
		}
	}
}
