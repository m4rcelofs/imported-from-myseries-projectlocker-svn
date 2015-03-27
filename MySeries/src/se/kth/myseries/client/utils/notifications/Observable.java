package se.kth.myseries.client.utils.notifications;

/**
 * MySeries <br>
 * A class that accepts Observers and notifies them of changes
 * 
 * @author Marcelo
 * @since 25/11/2010
 */
public interface Observable {
	
	public void addObservers(Observer... observers);
	
	public void notifyObservers();

}
