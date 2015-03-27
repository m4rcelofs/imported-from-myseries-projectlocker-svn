package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * Exception thrown during user authentication, if the app is not running on
 * Facebook
 * 
 * @author Marcelo
 * @since 14/10/2010
 */
public class NotOnFacebookException extends MySeriesFacebookException {
	
	private static final long	serialVersionUID	= 1L;
	
	public NotOnFacebookException() {
		super("Thou shall not try this outside of Facebook!");
	}
	
}
