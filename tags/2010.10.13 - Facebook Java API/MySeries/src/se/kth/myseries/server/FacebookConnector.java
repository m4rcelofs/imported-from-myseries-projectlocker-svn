package se.kth.myseries.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.ProfileField;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
public class FacebookConnector {
	
	private static final Logger			log		= Logger.getLogger(FacebookConnector.class
			.getName());
	
	private static final String			api_key	= "157981547561789";
	private static final String			secret	= "f76cffdf2257315a5b3a562e67516155";
	
	private final FacebookXmlRestClient	facebook;
	
	public FacebookConnector() {
		facebook = new FacebookXmlRestClient(api_key, secret);
	}
	
	public String getCurrentUser() {
		
		try {
			Long userID = facebook.users_getLoggedInUser();
			
			List<ProfileField> fields = new ArrayList<ProfileField>();
			fields.add(ProfileField.FIRST_NAME);
			
			List<Long> ids = new ArrayList<Long>();
			ids.add(userID);
			
			Document result = facebook.users_getInfo(ids, fields);
			result.toString();
			
		} catch (FacebookException e) {
			log.warning(e.getMessage());
			return "Could not get your profile information from Facebook, sorry :(";
		}
		
		return null;
	}
	
}
