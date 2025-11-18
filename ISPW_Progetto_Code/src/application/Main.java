package application;
	
import java.util.Scanner;

import application.configuration.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

//Homepage, Catalogo, attivita, form, pagamento, consigliati, agg attivita, login, prenotazioni organizzatore

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/paymentView.fxml"));
			Scene scene = new Scene(root, 640, 480);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Font font = Font.loadFont(getClass().getResourceAsStream("/Fonts/Montserrat-Regular.ttf"), 18);
			//System.out.println(font); // Se null, c’è un problema!
			
			primaryStage.setTitle("Homepage");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false); // <-- Rende la finestra NON ridimensionabile per il login, pagamento
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//Bisogna creare un modo per scegliere GUI o CLI e successivamente bisogna chiedere se si vuole avviare l'applicazione con DB, Demo, File 
		String visual = null;
		String mode = null;
		Scanner scanner= new Scanner(System.in); 
		
		try {
			System.out.println("Scegli l'interfaccia da utilizzare: GUI/CLI");
			while (true) {
				visual = scanner.nextLine().trim().toUpperCase();
				if (("GUI".equals(visual)) || ("CLI".equals(visual))) {	
					break;
				}
				System.out.println("Inserisci un'interfaccia valida: GUI/CLI");
			}
			
			System.out.println("Scegli la modalità da utilizzare: db/demo/file");
			while (true) {
				mode = scanner.nextLine().trim().toLowerCase();
				if (("db".equals(mode))||("demo".equals(mode))||("file".equals(mode))) {
					AppConfig.initMode(mode);//Inizializzazione della modalità
					
					AppConfig startMode= AppConfig.getInstance();
					System.out.println("Avvio la modalità: " + startMode.getMode());
					
					break;
				}
				System.out.println("Inserisci un'interfaccia valida: db/demo/file");	
			}
			
			
			
			if ("GUI".equals(visual)) {
				launch(args); //Chiama il metodo start della GUI
			} else {
				System.out.println("Funzione non ancora implementata");
				//creare un metodo start della CLI
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
