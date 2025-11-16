package application.configuration;

//Ho bisogno di una classe che mantiene il tipo di modalità scelto a run time dall'utente e concettualmente devo avere un'istanza che deve essere utilizzata da più client -> singleton

public class AppConfig {
	private static AppConfig singletonIstance;
	private String mode;

	public AppConfig(String mode) {
		this.mode = mode;
	}
	
	public static void initMode(String mode) {
		singletonIstance = new AppConfig(mode);
    }

    public static AppConfig getInstance() {
        if (singletonIstance == null) {
        	singletonIstance = new AppConfig("demo"); //Non sarebbe meglio un controllo?
        }
        return singletonIstance;
    }

	public String getMode() {
		return mode;
	}
	
}
