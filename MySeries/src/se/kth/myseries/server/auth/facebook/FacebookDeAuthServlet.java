package se.kth.myseries.server.auth.facebook;

import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MySeries <br>
 * Servlet is pinged when the user de-authorizes the application
 * 
 * @author Marcelo
 * @since 14/11/2010
 */
public class FacebookDeAuthServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(FacebookDeAuthServlet.class
			.getName());
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		// TODO deauthorize
		Enumeration n = req.getParameterNames();
		
		log.warning("app deauthorized");
		while (n.hasMoreElements())
			log.warning(n.nextElement().toString());
		
	}
	
}
