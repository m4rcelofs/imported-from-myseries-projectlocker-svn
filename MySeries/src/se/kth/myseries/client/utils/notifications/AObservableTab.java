package se.kth.myseries.client.utils.notifications;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.tab.Tab;

/**
 * MySeries <br>
 * A tab that listens to Observable events and passes them to the observers in
 * (for instance, internal components)
 * it
 * 
 * @author Marcelo
 * @since 25/11/2010
 */
public class AObservableTab extends Tab implements Observable, Observer {
	
	private final List<Observer>	observers;
	
	public AObservableTab(final String name, final String picture) {
		super(name, picture);
		this.observers = new ArrayList<Observer>();
	}
	
	@Override
	public void update() {
		this.notifyObservers();
	}
	
	@Override
	public void addObservers(final Observer... observers) {
		for (Observer o : observers)
			this.observers.add(o);
	}
	
	@Override
	public void notifyObservers() {
		for (Observer o : observers)
			o.update();
	}
	
}
