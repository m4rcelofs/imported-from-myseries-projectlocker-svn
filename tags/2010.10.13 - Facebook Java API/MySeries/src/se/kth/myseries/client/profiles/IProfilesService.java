package se.kth.myseries.client.profiles;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * MySeries <br>
 * Client side stub for the Friends tab
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
@RemoteServiceRelativePath("friends")
public interface IProfilesService extends RemoteService {
	
	public String getCurrentUser(); // TODO
	// userDTO
	
	public List<String> getFriends() throws IllegalArgumentException; // TODO
	// FriendsDTO
}
