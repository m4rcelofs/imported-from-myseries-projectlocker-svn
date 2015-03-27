package se.kth.myseries.client.auth;

import se.kth.myseries.shared.dto.FBGraphAddress;
import se.kth.myseries.shared.exceptions.AppNotAuthorizedException;
import se.kth.myseries.shared.exceptions.NotOnFacebookException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
@RemoteServiceRelativePath("auth")
public interface IFacebookAuthenticationService extends RemoteService {
	
	public void defineSignedRequest(String signedRequest)
	throws IllegalArgumentException, AppNotAuthorizedException,
	NotOnFacebookException;
	
	public FBGraphAddress getGraphAddress();
	
}
