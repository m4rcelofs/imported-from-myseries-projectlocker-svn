package se.kth.myseries.server.auth;

import java.util.logging.Logger;

import se.kth.myseries.server.auth.facebook.FacebookConnector;
import se.kth.myseries.server.auth.facebook.FacebookSignedRequestChecker;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.server.persistence.UsersDAO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 12/11/2010
 */
public class UserControlHelper {
	
	private static final Logger	log	= Logger.getLogger(UserControlHelper.class
			.getName());
	
	public IConnector getConnector(final String signedRequest) {
		
		try {
			return new FacebookConnector(
					new FacebookSignedRequestChecker()
					.checkRequest(signedRequest));
		} catch (MySeriesFacebookException e) {
			return new FakeConnector();
		}
		
	}
	
	@Cacheable
	public UserDTO getUserInfo(final String signed_request)
			throws SocialNetworkException {
		
		UserDTO dto = getConnector(signed_request).getCurrentUser();
		return dto;
		
	}
	
	@Cacheable
	public Long getUserID(final String signed_request)
			throws SocialNetworkException {
		
		UserDTO dto = getConnector(signed_request).getCurrentUser();
		
		UsersDAO dao = new UsersDAO();
		try {
			return dao.getByFacebookID(dto.facebook_id).getId().getId();
		} finally {
			dao.close();
		}
	}
}
