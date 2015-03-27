package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  29/10/2010
 */
public class MySeriesFacebookException extends SocialNetworkException {
	
	private static final long	serialVersionUID	= 1L;
	
	public MySeriesFacebookException() {
		super("Facebook exception");
	}
	
	public MySeriesFacebookException(final String message) {
		super(message);
	}
}
