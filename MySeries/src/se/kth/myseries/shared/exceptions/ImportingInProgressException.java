package se.kth.myseries.shared.exceptions;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public class ImportingInProgressException extends TvDbException {
	
	private static final long	serialVersionUID	= 1L;
	
	public ImportingInProgressException() {
		super(
		"The series is being imported, please wait a minute and try again");
	}

}
