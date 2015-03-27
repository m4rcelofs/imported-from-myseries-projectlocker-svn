package se.kth.myseries.client.auth;

import se.kth.myseries.shared.dto.FBGraphAddress;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  13/10/2010
 */
public interface IFacebookAuthenticationServiceAsync {
	
	void defineSignedRequest(String signedRequest, AsyncCallback<Void> callback);
	
	void getGraphAddress(AsyncCallback<FBGraphAddress> callback);
	
}
