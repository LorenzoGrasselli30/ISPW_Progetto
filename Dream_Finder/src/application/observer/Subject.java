package application.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

	 private List<Observer> observers = new ArrayList<>();

	 public void registerObserver(Observer o) {
	        if (o != null && !observers.contains(o)) {
	            observers.add(o);
	        }
	    }

	    public void removeObserver(Observer o) {
	        observers.remove(o);
	    }

	    protected void notifyObservers() {
	        for (Observer o : observers) {
	            o.update();
	        }
	    }
}
