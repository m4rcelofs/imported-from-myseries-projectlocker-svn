package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * Indicates an error with TheTVDB API.
 * @author Marcelo
 * @since  19/11/2010
 */
public class TvDbException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	public TvDbException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public TvDbException(final String message) {
		super(message);
	}
}