package se.kth.myseries.server.admin;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * MySeries <br>
 * Configures Log4J, used by RestFB lib
 * 
 * @author Marcelo
 * @since 13/10/2010
 */
public class Log4jInit extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	@Override
	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		if (file != null)
			DOMConfigurator.configure(prefix + "/" + file);
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {}
}
