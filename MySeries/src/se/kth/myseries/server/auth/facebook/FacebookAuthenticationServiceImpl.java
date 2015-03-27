package se.kth.myseries.server.auth.facebook;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import se.kth.myseries.client.auth.IFacebookAuthenticationService;
import se.kth.myseries.shared.dto.FBGraphAddress;
import se.kth.myseries.shared.exceptions.AppNotAuthorizedException;
import se.kth.myseries.shared.exceptions.NotOnFacebookException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
public class FacebookAuthenticationServiceImpl extends RemoteServiceServlet
implements IFacebookAuthenticationService {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(FacebookAuthenticationServiceImpl.class
			.getName());
	
	// private String api_key;
	private String				clientId;
	// private String secret;
	private FBGraphAddress		graph;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// api_key = getInitParameter("api_key");
		// secret = getInitParameter("secret");
		clientId = getInitParameter("client_id");
		
		try {
			graph = new FBGraphAddress(URLDecoder.decode(
					getInitParameter("graphAuthorizeURL1"), "UTF-8"), clientId);
		} catch (UnsupportedEncodingException e) {
			log.severe(e.getMessage());
		}
		
	}
	
	/**
	 * See {@link http://developers.facebook.com/docs/authentication/canvas}
	 */
	@Override
	public void defineSignedRequest(final String signedRequest)
	throws IllegalArgumentException, AppNotAuthorizedException,
	NotOnFacebookException {
		
		// adds to cache
		new FacebookSignedRequestChecker().checkRequest(signedRequest);
		
		// Enumeration num = getThreadLocalRequest().getSession()
		// .getAttributeNames();
		// while (num.hasMoreElements()) {
		// String atrib = num.nextElement().toString();
		// getThreadLocalRequest().getSession().removeAttribute(atrib);
		// }
	}
	
	
	
	@Override
	public FBGraphAddress getGraphAddress() {
		return graph;
	}
}
