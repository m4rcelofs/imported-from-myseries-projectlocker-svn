package se.kth.myseries.client.utils.navigation;

import com.smartgwt.client.widgets.Canvas;

/**
 * MySeries2 <br>
 * 
 * @author Léna
 * @since 15 nov. 2010
 */
public interface Page {
	
	/**
	 * @return Name to be shown on navigation bar
	 */
	public abstract String getName();
	
	/**
	 * @return Canvas to be shown to the user
	 */
	public abstract Canvas getCanvas();
	
	/**
	 * @return Unique identifier
	 */
	public abstract Object getIdentifier();
	
}
