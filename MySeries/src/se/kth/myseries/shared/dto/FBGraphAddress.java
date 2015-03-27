package se.kth.myseries.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * DTO for Facebook Graph API address. <br />
 * As of 2010.Oct, using the new oAuth 2.0 API, authorization of an app follows
 * these steps:
 * <ul>
 * <li>Request {@link https
 * ://graph.facebook.com/oauth/authorize?client_id=MY_API_KEY&
 * redirect_uri=http://www.something.com&scope=publish_stream,create_event&
 * display=page}
 * <li>Facebook will redirect to {@link http
 * ://www.something.com?code=MY_VERIFICATION_CODE}
 * <li>Request {@link https
 * ://graph.facebook.com/oauth/access_token?client_id=MY_API_KEY
 * &redirect_uri=http://www.something.com&client_secret=MY_API_SECRET&code=
 * MY_VERIFICATION_CODE}
 * <li>Facebook will respond with access_token=MY_ACCESS_TOKEN
 * </ul>
 * 
 * @author Marcelo
 * @since 15/10/2010
 */
public class FBGraphAddress implements IsSerializable {
	
	public String	authorizeURL;
	
	public String	clientId;
	
	public FBGraphAddress() {}
	
	public FBGraphAddress(final String authorizeURL, final String clientId) {
		this.authorizeURL = authorizeURL;
		this.clientId = clientId;
	}
	
	public String getFirstAuthorizationURL() {
		return authorizeURL.replace("CLIENTID", clientId);
	}
	
}
