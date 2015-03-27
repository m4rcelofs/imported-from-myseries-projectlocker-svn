package se.kth.myseries.server.admin;

import java.util.Collections;
import java.util.logging.Logger;

import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public class CacheFlusherServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(CacheFlusherServlet.class
			.getName());
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		log.warning("Flushing memcache...");
		try {
			CacheFactory factory = CacheManager.getInstance().getCacheFactory();
			factory.createCache(Collections.EMPTY_MAP).clear();
		} catch (CacheException e) {
			log.severe("could not clear cache: " + e.toString());
		}
		
		log.warning("Successfully cleared memcache");
		
	}
	
}
