package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  14/11/2010
 */
public class DatabaseException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	public DatabaseException() {
		super("unknnown exception from database");
	}
	
	/**
	 * @param message
	 */
	public DatabaseException(final String message) {
		super(message);
	}
	
	
}
