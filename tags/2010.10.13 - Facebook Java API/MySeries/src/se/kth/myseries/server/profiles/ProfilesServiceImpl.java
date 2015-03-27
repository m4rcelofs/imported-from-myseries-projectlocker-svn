package se.kth.myseries.server.profiles;

import java.util.List;

import se.kth.myseries.client.profiles.IProfilesService;
import se.kth.myseries.server.FacebookConnector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  08/10/2010
 */
public class ProfilesServiceImpl extends RemoteServiceServlet implements
IProfilesService {
	
	private static final long	serialVersionUID	= 1L;
	
	@Override
	public String getCurrentUser() {
		
		return new FacebookConnector().getCurrentUser();
		
	}
	
	/* (non-Javadoc)
	 * @see se.kth.myseries.client.profiles.IFriendsService#getFriends()
	 */
	@Override
	public List<String> getFriends() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
		
	}
	
}
