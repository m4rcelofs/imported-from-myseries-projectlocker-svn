package se.kth.myseries.server.auth;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import se.kth.myseries.server.persistence.UsersDAO;
import se.kth.myseries.server.persistence.entity.MySeriesUser;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

/**
 * MySeries <br>
 * A fake connector used when the app is not run inside a social network, mainly
 * for testing
 * 
 * @author Marcelo
 * @since 13/12/2010
 */
public class FakeConnector implements IConnector {
	
	private static final Logger	log	= Logger.getLogger(FakeConnector.class
			.getName());
	
	@Override
	public UserDTO getCurrentUser() throws SocialNetworkException {
		log.warning("not logged on fb, returning fake user");
		
		UsersDAO dao = new UsersDAO();
		
		try {
			MySeriesUser user = dao.getByFacebookID("somethingInexistent");
			return dao.convert(user);
		} catch (Exception e) {
			UserDTO user = new UserDTO();
			{
				user.firstName = "";
				user.lastName = "Not logged in";
				user.facebook_id = "-1";
				user.birthday = "01/01/1970";
				user.pictureURL = "https://graph.facebook.com/cocacola/picture";
			}
			return user;
		} finally {
			dao.close();
		}
		
	}
	
	@Override
	public List<UserID> getFriendsThatUseTheApp(final String userID)
	throws SocialNetworkException {
		return Arrays.asList(new UserID[] { new UserID(-1L) });
	}
	
}
