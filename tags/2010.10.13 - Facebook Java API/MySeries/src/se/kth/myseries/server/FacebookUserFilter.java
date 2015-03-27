package se.kth.myseries.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;

/**
 * MySeries <br>
 * Filter to make sure the user is logged in on facebook
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
public class FacebookUserFilter implements Filter {
	
	private String				api_key;
	private String				secret;
	
	private static final Logger	log						= Logger.getLogger(FacebookUserFilter.class
			.getName());
	
	private static final String	FACEBOOK_USER_CLIENT	= "facebook.user.client";
	
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		api_key = filterConfig.getInitParameter("facebook_api_key");
		secret = filterConfig.getInitParameter("facebook_secret");
		if (api_key == null || secret == null)
			throw new ServletException(
					"Cannot initialise Facebook User Filter because the "
					+ "facebook_api_key or facebook_secret context init "
					+ "params have not been set. Check that they're there "
					+ "in your servlet context descriptor.");
		else
			;
		log.info("initialization of FacebookUserFilter complete");
	}
	
	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		
		log.info("FacebookUserFilter#doFilter");
		
		try {
			
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			
			HttpSession session = request.getSession(true);
			IFacebookRestClient<Document> userClient = getUserClient(session);
			if (userClient == null) {
				userClient = new FacebookXmlRestClient(api_key, secret);
				session.setAttribute(FACEBOOK_USER_CLIENT, userClient);
			}
			
			FacebookWebappHelper<Document> facebook = new FacebookWebappHelper<Document>(
					request, response, api_key, secret, userClient);
			
			{
				//				String nextPage = request.getRequestURI();
				//				nextPage = nextPage.substring(nextPage.indexOf("/", 1) + 1);
				//				
				//				boolean redirectOccurred = facebook.requireLogin(nextPage);
				//				if (redirectOccurred)
				//					return;
				//				redirectOccurred = facebook.requireFrame(nextPage);
				//				if (redirectOccurred)
				//					return;
			}
			
			if (!facebook.isLogin())
				log.warning("not logged in on facebook");
			else
				log.warning("logged in on facebook");
			
			log.warning("facebook inFrame? " + facebook.inFrame());
			
			chain.doFilter(request, response);
		} finally {}
	}
	
	public static FacebookXmlRestClient getUserClient(final HttpSession session) {
		return (FacebookXmlRestClient) session
		.getAttribute(FACEBOOK_USER_CLIENT);
	}
}