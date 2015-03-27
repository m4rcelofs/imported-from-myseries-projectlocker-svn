package se.kth.myseries.server.auth.facebook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MySeries <br>
 * Second step of the oAuth authorization mechanism
 * 
 * @author Marcelo
 * @since 15/10/2010
 */
public class OAuthRedirect extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	private String				graphAuthorizeURL2;
	
	private static final Logger	log					= Logger.getLogger(OAuthRedirect.class
			.getName());
	
	@Override
	public void init() {
		try {
			graphAuthorizeURL2 = URLDecoder.decode(
					getInitParameter("graphAuthorizeURL2"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.severe(e.getMessage());
		}
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		try {
			res.sendRedirect(graphAuthorizeURL2);
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}
	
}
