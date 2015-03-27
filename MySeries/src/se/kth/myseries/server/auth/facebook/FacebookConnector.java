package se.kth.myseries.server.auth.facebook;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import se.kth.myseries.server.auth.IConnector;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;
import com.restfb.types.User;

/**
 * MySeries <br>
 * A connector to get Facebook data
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
public class FacebookConnector implements IConnector {
	
	private static final long		serialVersionUID	= 1L;
	
	private static final Logger		log					= Logger.getLogger(FacebookConnector.class
			.getName());
	
	private final FacebookClient	facebook;
	
	public FacebookConnector(final FacebookAuth auth)
	throws MySeriesFacebookException {
		
		if (auth == null || auth.oauth_token == null) {
			log.warning("User is not logged on Facebook, there's no authentication data!");
			throw new MySeriesFacebookException("You are not logged on Facebook :(");
		}
		
		facebook = new DefaultFacebookClient(auth.oauth_token);
	}
	
	@Override
	public UserDTO getCurrentUser() throws MySeriesFacebookException {
		
		try {
			User user = facebook.fetchObject("me", User.class, Parameter.with(
					"fields", "id, first_name, last_name, birthday"));
			
			UserDTO dto = convert(user);
			log.info("getting \"me\" user from FB: " + user.toString());
			return dto;
		} catch (FacebookException e) {
			log.severe(e.getMessage());
			throw new MySeriesFacebookException(e.getMessage());
		}
		
	}
	
	@Override
	@Cacheable
	public List<UserID> getFriendsThatUseTheApp(final String userID)
	throws MySeriesFacebookException {
		
		long init = new Date().getTime();
		
		String query = "SELECT uid FROM user WHERE uid IN "
			+ "(SELECT uid2 FROM friend WHERE uid1 = " + userID
			+ ") AND is_app_user = 1";
		
		try {
			
			log.fine("Querying Facebook for friends that also use the app...");
			List<UserID> results = facebook.executeQuery(query, UserID.class);
			log.info("Query for friends took " + (new Date().getTime() - init)
					+ "ms");
			
			return results;
			
		} catch (FacebookException e) {
			log.warning(e.toString());
			throw new MySeriesFacebookException(e.getMessage());
		}
	}
	
	private UserDTO convert(final User user) {
		UserDTO dto = new UserDTO();
		{
			dto.birthday = user.getBirthday();
			dto.facebook_id = user.getId();
			dto.firstName = user.getFirstName();
			dto.lastName = user.getLastName();
			if (user.getFirstName() == null)
				dto.firstName = user.getName();
			dto.pictureURL = "http://graph.facebook.com/" + user.getId()
			+ "/picture";
		}
		return dto;
	}
	
}
