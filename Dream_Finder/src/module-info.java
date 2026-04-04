module Dream_Finder {
	exports application.controller.graphic;
	exports application;
	exports application.configuration;

	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires stripe.java;
    requires com.google.gson;
    
 // JDBC driver modules
 	requires java.sql;
 	requires org.mariadb.jdbc;
 	//requires com.mysql.cj;
    
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller.graphic to javafx.fxml;
}