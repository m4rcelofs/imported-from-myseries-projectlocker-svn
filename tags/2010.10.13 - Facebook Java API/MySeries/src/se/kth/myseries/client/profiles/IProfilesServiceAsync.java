package se.kth.myseries.client.profiles;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public interface IProfilesServiceAsync {
	
	public void getCurrentUser(AsyncCallback<String> callback);
	
	public void getFriends(AsyncCallback<List<String>> callback)
	throws IllegalArgumentException; // TODO FriendsDTO
	
}
