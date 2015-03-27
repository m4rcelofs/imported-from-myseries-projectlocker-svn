package se.kth.myseries.client.profiles;

import java.util.List;

import se.kth.myseries.shared.dto.StatisticsDTO;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public interface IProfilesServiceAsync {
	
	public void getUserInfo(String signed_request,
			AsyncCallback<UserDTO> callback);
	
	void getUserInfoByFacebookID(String facebook_id,
			AsyncCallback<UserDTO> asyncCallback);

	public void getFriendsThatUseTheApp(String signed_request,
			AsyncCallback<List<UserDTO>> callback);
	
	void getStatistics(String signed_request,
			AsyncCallback<StatisticsDTO> callback);
	
	void getStatistics(Long userID, AsyncCallback<StatisticsDTO> callback);
	
	void getStatisticsByFacebookID(String facebook_id,
			AsyncCallback<StatisticsDTO> callback);
	
	void getFriendsThatUseTheApp(String signed_request, String facebookID,
			AsyncCallback<List<UserDTO>> callback);
}
