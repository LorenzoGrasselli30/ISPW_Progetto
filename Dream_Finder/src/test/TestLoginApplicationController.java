package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import application.controller.application.LoginApplicationController;
import application.exception.ValidationException;

public class TestLoginApplicationController {
	
	private LoginApplicationController loginController = new LoginApplicationController();
	
	@Test
    void testAuthenticate_Success() throws ValidationException {
        // Verifica che l'autenticazione vada a buon fine
        assertTrue(loginController.authenticate("luigi.verdi@mail.com", "LuigiVerdi1!"));
    }
	
	@Test
    void testAuthenticate_NotValidEmail() throws ValidationException {
        // Verifica che venga lanciata una ValidationException quando si immette un formato di email non valido
		//In questo caso manca @
		Exception thrown = assertThrows(ValidationException.class, () -> {
			loginController.authenticate("luigi.verdimail.com", "LuigiVerdi1!"); 
        });

        // Verifica messaggio dell'eccezione
        assertEquals("Formato dell'email non valido. Esempio: name@mail.com", thrown.getMessage());
    }
	
	@Test
    void testAuthenticate_WrongPassword() {
    	// Verifica che venga lanciata una ValidationException per password errata
        Exception thrown = assertThrows(ValidationException.class, () -> {
            loginController.authenticate("luigi.verdi@mail.com", "Luigiverdi1."); 
        });

        // Verifica messaggio dell'eccezione
        assertEquals("L'email o la password non corrispondono", thrown.getMessage());
    }
	
	@Test
    void testAuthenticate_UserNotExists() {
    	// Verifica che venga lanciata una ValidationException per password errata
        Exception thrown = assertThrows(ValidationException.class, () -> {
            loginController.authenticate("not.found@mail.com", "LuigiVerdi1!"); 
        });

        // Verifica messaggio dell'eccezione
        assertEquals("L'email o la password non corrispondono", thrown.getMessage());
    }
    
}
