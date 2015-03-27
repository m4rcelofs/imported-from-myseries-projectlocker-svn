package se.kth.myseries.client.profiles;

import java.util.List;

import se.kth.myseries.shared.dto.StatisticsDTO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.restfb.FacebookException;

/**
 * MySeries <br>
 * Client side stub for the Friends tab
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
@RemoteServiceRelativePath("profiles")
public interface IProfilesService extends RemoteService {
	
	/**
	 * Gets the user currently logged on FB
	 * 
	 * @param signed_request
	 *            Location.getParameter("signed_request");
	 * @throws DatabaseException
	 * @throws FacebookException
	 *             if the user is not logged on Facebook
	 */
	public UserDTO getUserInfo(String signed_request)
	throws MySeriesFacebookException, DatabaseException;
	
	public UserDTO getUserInfoByFacebookID(String facebook_id)
	throws DatabaseException;
	
	/**
	 * Gets the friends of the current user that also use the application
	 * 
	 * @param signed_request
	 *            Location.getParameter("signed_request");
	 * @return
	 * @throws MySeriesFacebookException
	 * @throws SocialNetworkException
	 */
	public List<UserDTO> getFriendsThatUseTheApp(String signed_request)
			throws SocialNetworkException;
	
	/**
	 * Gets the friends of some user that also use the application
	 * 
	 * @param signed_request
	 *            Location.getParameter("signed_request");
	 * @return
	 * @throws MySeriesFacebookException
	 */
	public List<UserDTO> getFriendsThatUseTheApp(String signed_request,
			String facebookID) throws SocialNetworkException;
	
	/**
	 * Gets the current statistics for the current user
	 * 
	 * @param signed_request
	 * @return
	 * @throws MySeriesFacebookException
	 * @throws DatabaseException
	 */
	public StatisticsDTO getStatistics(String signed_request)
			throws SocialNetworkException, DatabaseException;
	
	/**
	 * Gets the current statistics for some user
	 * 
	 * @param signed_request
	 * @return
	 * @throws DatabaseException
	 */
	public StatisticsDTO getStatistics(Long userID) throws DatabaseException;
	
	public StatisticsDTO getStatisticsByFacebookID(String facebook_id)
	throws DatabaseException;
}
