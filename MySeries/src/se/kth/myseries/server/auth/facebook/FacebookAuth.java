package se.kth.myseries.server.auth.facebook;

import java.io.Serializable;

import com.restfb.Facebook;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  14/10/2010
 */
public class FacebookAuth implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	@Facebook
	public String				algorithm;
	@Facebook
	public Long					issued_at;
	@Facebook
	public Long					user_id;
	@Facebook
	public String				oauth_token;
	@Facebook
	public Integer				expires;
	@Facebook
	public String				profile_id;
	
	
}
