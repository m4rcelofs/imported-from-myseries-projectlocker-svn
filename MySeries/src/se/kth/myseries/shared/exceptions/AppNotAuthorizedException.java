package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * Exception thrown during user authentication, if the user hasn't authorized
 * the app to access personal data
 * 
 * @author Marcelo
 * @since 14/10/2010
 */
public class AppNotAuthorizedException extends MySeriesFacebookException {
	
	private static final long	serialVersionUID	= 1L;
	
	public AppNotAuthorizedException() {
		super(
				"It seems you haven't authorized the application. <br />"
				+ "You can use it without authorizing, but you'll miss the best part!");
	}
	
}
