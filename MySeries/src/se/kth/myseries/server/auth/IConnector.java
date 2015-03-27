package se.kth.myseries.server.auth;

import java.io.Serializable;
import java.util.List;

import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

import com.restfb.Facebook;

/**
 * MySeries <br>
 * An interface for all actions that can be executed on social networking
 * websites
 * 
 * @author Marcelo
 * @since 13/12/2010
 */
public interface IConnector {
	
	public UserDTO getCurrentUser() throws SocialNetworkException;
	
	public List<UserID> getFriendsThatUseTheApp(final String userID)
	throws SocialNetworkException;
	
	public static class UserID implements Serializable {
		private static final long	serialVersionUID	= 1L;
		
		public UserID() {}
		
		public UserID(final Long uid) {
			this.uid = uid;
		}

		@Facebook
		public Long	uid;
	}
	
}
