package se.kth.myseries.server;

/**
 * MySeries <br>
 * Textual constants for session and everything
 * 
 * @author Marcelo
 * @since 28/10/2010
 */
public class ServerConstants {
	
	public static final String	FB_SIGN_ALGORITHM	= "HMACSHA256";
	
	public static final String	CACHE_NAME			= "MYSERIES_CACHE";
	
	public static final String	FB_SECRET			= "f76cffdf2257315a5b3a562e67516155";
	
	public static final String	SERIES_BANNER		= "1";
	public static final String	SERIES_POSTER		= "2";
	public static final String	SEASON_BANNER		= "3";
	
	public static final boolean	CACHE_ENABLED		= true;
	
	public static final String	IMAGE_SERVLET_URL	= "/myseries/image?id=";
}
