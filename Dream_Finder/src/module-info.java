module Dream_Finder {
	exports application.controller.graphic;
	exports application;
	exports application.configuration;

	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	
	// Stripe API
	requires stripe.java;
    requires com.google.gson;
    
    // JDBC driver modules
 	requires java.sql;
 	requires org.mariadb.jdbc;
    
 	requires org.junit.jupiter.api;
 	
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller.graphic to javafx.fxml;
}