package application.model.dao.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.entity.User;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;
import application.model.enums.UserRole;

public class ProviderDAODemo implements ProviderDAO{
	
	private Map<String, Provider> providers = new HashMap<>();
	
	public ProviderDAODemo() {
		initializeProviderDemo();
	}

	private void initializeProviderDemo() {
		//Creazione di date di disponibilità
		
		
		//Creazione del provider1
		Provider provider1= new Provider(new User("luigi.verdi@mail.com", "LuigiVerdi1!", UserRole.PROVIDER), "LuigiSRL",ProviderType.COMPANY, 0, "Italia", "Luigi", "Verdi");
		
		//Associazione delle attività al provider1
		provider1.addActivity("Roma: tour guidato del Colosseo", 30.0, ActivityType.CULTURE, new ActivityRating(4.8, 340), 
				new ActivityOtherInformation("Visita il Colosseo, il più grande anfiteatro del mondo romano",
				true, false, true, 1, false), this.initializeAvailableDates(10)); 
		provider1.addActivity("Roma: biglietto d'ingresso per Castel Sant'Angelo", 40.0, ActivityType.CULTURE, new ActivityRating(4.5, 200), 
				new ActivityOtherInformation("Risparmia tempo durante il tuo viaggio a Roma con questo biglietto d'ingresso a Castel Sant'Angelo",
				true, true, true, 2, false), this.initializeAvailableDates(10));
		provider1.addActivity(
		            "Roma: tour privato del Foro Romano e del Palatino",
		            27.99,
		            ActivityType.CULTURE,
		            new ActivityRating(4.5, 300),
		            new ActivityOtherInformation(
		                    "Esplora il cuore dell'antica Roma con un tour del Foro Romano e del Palatino con una guida autorizzata",
		                    true,
		                    false,
		                    false,
		                    180,
		                    true
		            ),
		            this.initializeAvailableDates(10)
		    );
		
		providers.put("luigi.verdi@mail.com", provider1);
		
		//Creazione del provider2
		Provider provider2= new Provider(new User("provider2@mail.com", "Provider2!", UserRole.PROVIDER), "Provider2Group", ProviderType.INDIVIDUAL, 0, "Francia", "Provider2", "Provider2");
				
		//Associazione delle attività al provider2
		provider2.addActivity(
	            "Parigi: crociera sulla Senna",
	            59.99,
	            ActivityType.CULTURE,
	            new ActivityRating(4.4, 480),
	            new ActivityOtherInformation(
	                    "Ammirate i monumenti più famosi di Parigi durante una piacevole crociera sulla Senna.",
	                    true,
	                    true,
	                    true,
	                    1,
	                    false
	            ),
	            this.initializeAvailableDates(10)
	    );
		provider2.addActivity(
	            "Parigi: laboratorio di pasticceria francese",
	            29.99,
	            ActivityType.FOOD,
	            new ActivityRating(3.8, 220),
	            new ActivityOtherInformation(
	                    "Impara a preparare madeleine, macaron, financier e una deliziosa cioccolata calda",
	                    true,
	                    true,
	                    false,
	                    2,
	                    false
	            ),
	            this.initializeAvailableDates(10)
	    );
		provider2.addActivity(
	            "Parigi: tour guidato di Notre-Dame",
	            18.0,
	            ActivityType.CULTURE,
	            new ActivityRating(4.0, 96),
	            new ActivityOtherInformation(
	                    "Scopri Notre-Dame attraverso i suoi simboli gotici, gli interni restaurati e 850 anni di storia",
	                    true,
	                    true,
	                    true,
	                    75,
	                    true
	            ),
	            this.initializeAvailableDates(10)
	    );
		
		providers.put("provider2@mail.com", provider2);
				
		//Creazione del provider3
		Provider provider3= new Provider(new User("giacomo.bianchi@mail.com", "GiacomoBianchi1!", UserRole.PROVIDER), "BianchiCorp", ProviderType.EDU, 0, "Spagna", "Giacomo", "Bianchi");
				
		//Associazione delle attività al provider3
		provider3.addActivity(
	            "Madrid: Biglietto d'ingresso al Palazzo Reale",
	            22.0,
	            ActivityType.CULTURE,
	            new ActivityRating(4.2, 130),
	            new ActivityOtherInformation(
	                    "Goditi un viaggio nella storia della Spagna e della sua monarchia grazie a questo biglietto d'ingresso con accesso rapido al Palazzo Reale",
	                    true,
	                    true,
	                    true,
	                    150,
	                    true
	            ),
	            this.initializeAvailableDates(10)
	    );

	    provider3.addActivity(
	            "Madrid: biglietto per lo Zoo Aquarium",
	            25.0,
	            ActivityType.NATURE,
	            new ActivityRating(4.1, 170),
	            new ActivityOtherInformation(
	                    "Scopri animali, creature del mare e piante provenienti da tutto il mondo",
	                    true,
	                    false,
	                    true,
	                    4,
	                    false
	            ),
	            this.initializeAvailableDates(10)
	    );

	    provider3.addActivity(
	            "Barcellona: biglietto d'ingresso alla Sagrada Familia",
	            15.0,
	            ActivityType.CULTURE,
	            new ActivityRating(4.0, 345),
	            new ActivityOtherInformation(
	                    "Entra nel capolavoro incompiuto di Gaudí, la Sagrada Familia",
	                    false,
	                    false,
	                    true,
	                    45,
	                    true
	            ),
	            this.initializeAvailableDates(10)
	    );
		
		providers.put("giacomo.bianchi@mail.com", provider3);
		
		//Creazione del provider4
				Provider provider4= new Provider(new User("marco.marroni@mail.com", "Marcomarroni1!", UserRole.PROVIDER), "MarcoTravel", ProviderType.EDU, 0, "Germania", "Marco", "Marroni");
						
		//Associazione delle attività al provider4
				provider4.addActivity(
			            "Museo del Muro di Berlino al Checkpoint Charlie",
			            14.0,
			            ActivityType.CULTURE,
			            new ActivityRating(4.0, 140),
			            new ActivityOtherInformation(
			                    "Fondato poco dopo la costruzione del Muro di Berlino, questo museo ha avuto un ruolo fondamentale nella storia e conserva oggetti originali.",
			                    true,
			                    true,
			                    true,
			                    2,
			                    false
			            ),
			            this.initializeAvailableDates(10)
			    );

			    provider4.addActivity(
			            "Berlino: Biglietto d'ingresso per il Palazzo di Charlottenburg",
			            25.0,
			            ActivityType.CULTURE,
			            new ActivityRating(4.5, 168),
			            new ActivityOtherInformation(
			                    "Il Palazzo Vecchio e l'Ala Nuova formano il più importante complesso di palazzi degli ex elettori del Brandeburgo.",
			                    true,
			                    true,
			                    true,
			                    3,
			                    false
			            ),
			            this.initializeAvailableDates(10)
			    );

			    provider4.addActivity(
			            "Berlino: biglietto d'ingresso al Berlin Story Bunker",
			            18.99,
			            ActivityType.CULTURE,
			            new ActivityRating(4.2, 345),
			            new ActivityOtherInformation(
			                    "Scoprite la mostra \"Hitler, come è potuto accadere\" e il Berlin Story Museum con un unico biglietto",
			                    true,
			                    false,
			                    true,
			                    2,
			                    false
			            ),
			            this.initializeAvailableDates(10)
			    );
				
				providers.put("marco.marroni@mail.com", provider4);
		
		//Creazione del provider5
				Provider provider5= new Provider(new User("provider5@mail.com", "Provider5!", UserRole.PROVIDER), "Provider5Group", ProviderType.INDIVIDUAL, 0, "Italia", "Provider5", "Provider5");
						
				//Associazione delle attività al provider5
				provider5.addActivity(
			            "Londra: esperienza di Buckingham Palace e cambio della guardia",
			            55.0,
			            ActivityType.CULTURE,
			            new ActivityRating(4.2, 2345),
			            new ActivityOtherInformation(
			                    "Immergiti nella ricca storia di Westminster, che abbraccia oltre un millennio, mentre le guide ti svelano i punti salienti di Buckingham Palace.",
			                    true,
			                    true,
			                    true,
			                    2,
			                    false
			            ),
			            this.initializeAvailableDates(10)
			    );

			    provider5.addActivity(
			            "Londra: tour rapido del Museo di storia naturale",
			            15.0,
			            ActivityType.NATURE,
			            new ActivityRating(4.2, 70),
			            new ActivityOtherInformation(
			                    "Viaggia nel tempo e scopri le meraviglie della scienza e del mondo naturale",
			                    true,
			                    false,
			                    true,
			                    120,
			                    true
			            ),
			            this.initializeAvailableDates(10)
			    );

			    provider5.addActivity(
			            "Londra: Biglietto d'ingresso per il London Eye",
			            33.99,
			            ActivityType.CULTURE,
			            new ActivityRating(4.0, 345),
			            new ActivityOtherInformation(
			                    "Ammira monumenti come il Big Ben, Buckingham Palace e la Cattedrale di St Paul da 135 metri di altezza",
			                    false,
			                    false,
			                    true,
			                    30,
			                    true
			            ),
			            this.initializeAvailableDates(10)
			    );
				
				providers.put("provider5@mail.com", provider5);	
	}
	
	public List<Provider> providersList() {
		return new ArrayList<>(providers.values());
	}
	
	private ActivityAvailableDates initializeAvailableDates(Integer dailyPlaces) {
		 Map<LocalDate, Integer> places = new HashMap<>();
	     LocalDate current = java.time.LocalDate.now();
	     int addedDays = 0;

	        while (addedDays < 10) {
	            current = current.plusDays(1); //Aumenta di un giorno
	            if (current.getDayOfWeek() != java.time.DayOfWeek.SUNDAY) {
	                places.put(current, dailyPlaces);
	                addedDays++;
	            }
	        }

	     return new ActivityAvailableDates(places);
	}
	
	@Override
	public List<Provider> findTopProviders() {
		return providers.values().stream()
				.sorted((p1, p2) -> Double.compare(p2.getProviderRate(), p1.getProviderRate()))
				.limit(5)
				.collect(Collectors.toList());
	}

	@Override
	public Provider findByActivity(Activity activity) {
		for(Provider provider : providers.values()) {
			if(provider.getActivities().contains(activity)) {
				return provider;
			}
		}
		return null;
	}
	
}
