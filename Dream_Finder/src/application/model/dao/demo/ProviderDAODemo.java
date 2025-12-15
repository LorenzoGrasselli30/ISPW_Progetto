package application.model.dao.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;

public class ProviderDAODemo implements ProviderDAO{
	
	private Map<String, Provider> providers = new HashMap<>();
	
	public ProviderDAODemo() {
		initializeProviderDemo();
	}

	private void initializeProviderDemo() {
		//Creazione del provider1
		Provider provider1= new Provider("luigi.verdi@mail.com", "LuigiVerdi1!", "LuigiSRL",ProviderType.COMPANY, 0, "Italia", "Luigi", "Verdi", 4.6);
		
		//Associazione delle attività al provider1
		provider1.addActivity("Roma: tour guidato del Colosseo", "Visita il Colosseo, il più grande anfiteatro del mondo romano", 40.0,
				1, false, ActivityType.CULTURE, true, false, true, 340, 4.8);
		provider1.addActivity("Roma: biglietto d'ingresso per Castel Sant'Angelo", "Risparmia tempo durante il tuo viaggio a Roma con questo biglietto d'ingresso a Castel Sant'Angelo", 50.0,
				2, false, ActivityType.CULTURE, true, true, true, 200, 4.5);
		provider1.addActivity("Roma: tour privato del Foro Romano e del Palatino", "Esplora il cuore dell'antica Roma con un tour del Foro Romano e del Palatino con una guida autorizzata", 27.99,
				180, true, ActivityType.CULTURE, true, false, false, 300, 4.5);
		
		providers.put("luigi.verdi@mail.com", provider1);
		
		//Creazione del provider2
		Provider provider2= new Provider("provider2@mail.com", "Provider2!", "Provider2Group", ProviderType.INDIVIDUAL, 0, "Francia", "Provider2", "Provider2", 3.5);
				
		//Associazione delle attività al provider2
		provider2.addActivity("Parigi: crociera sulla Senna", "Ammirate i monumenti più famosi di Parigi durante una piacevole crociera sulla Senna.", 59.99,
				1, false, ActivityType.CULTURE, true, true, true, 480, 4.4);
		provider2.addActivity("Parigi: laboratorio di pasticceria francese", "impara a preparare madeleine, macaron, financier e una deliziosa cioccolata calda", 29.99, 
				2, false, ActivityType.FOOD, true, true, false, 220, 3.8);
		provider2.addActivity("Parigi: tour guidato di Notre-Dame", "Scopri Notre-Dame attraverso i suoi simboli gotici, gli interni restaurati e 850 anni di storia", 18.0,
				75, true, ActivityType.CULTURE, true, true, true, 96, 4.0);
		
		providers.put("provider2@mail.com", provider2);
				
		//Creazione del provider3
		Provider provider3= new Provider("giacomo.bianchi@mail.com", "GiacomoBianchi1!", "BianchiCorp", ProviderType.EDU, 0, "Spagna", "Giacomo", "Bianchi", 4.1);
				
		//Associazione delle attività al provider3
		provider3.addActivity("Madrid: Biglietto d'ingresso al Palazzo Reale", "Goditi un viaggio nella storia della Spagna e della sua monarchia grazie a questo biglietto d'ingresso con accesso rapido al Palazzo Reale di Madrid", 29.99, 
				150, true, ActivityType.CULTURE, true, true, true, 130, 4.2);
		provider3.addActivity("Madrid: biglietto per lo Zoo Aquarium", "Scopri animali, creature del mare e piante provenienti da tutto il mondo", 25.0,
				4, false, ActivityType.NATURE, true, false, true, 170, 4.1);
		provider3.addActivity("Barcellona: biglietto d'ingresso alla Sagrada Familia", "Entra nel capolavoro incompiuto di Gaudí, la Sagrada Familia", 15.0,
				45, true, ActivityType.CULTURE, false, false, true, 345, 4.0);
		
		providers.put("giacomo.bianchi@mail.com", provider3);			
	}

	@Override
	public List<Provider> findTopProviders() {
		return providers.values().stream()
				.sorted((p1, p2) -> Double.compare(p2.getProviderRate(), p1.getProviderRate()))
				.limit(5)
				.collect(Collectors.toList());
	}
	
	
	
}
