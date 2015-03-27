package se.kth.myseries.server.admin;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.kth.myseries.server.admin.TheTVDBImporterServlet.ImportedSeriesList;
import se.kth.myseries.server.persistence.PMF;
import se.kth.myseries.server.persistence.entity.Episode;
import se.kth.myseries.server.persistence.entity.Image;
import se.kth.myseries.server.persistence.entity.Season;
import se.kth.myseries.server.persistence.entity.Series;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 11/11/2010
 */
public class SeriesDatabaseCleanerServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(SeriesDatabaseCleanerServlet.class
			.getName());
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		long init = new Date().getTime();
		
		if (!"XmGPal1iGq0x0hm".equals(req.getParameter("key"))) {
			log.warning("attempt to clean database with wrong key");
			try {
				res.getWriter().write("wrong key");
			} catch (IOException ignore) {}
			return;
		}
		
		log.warning("Cleaning series database...");
		
		PersistenceManager pm = PMF.getManager();
		
		Query query = pm.newQuery(Series.class);
		query.deletePersistentAll();
		
		Query query2 = pm.newQuery(Season.class);
		query2.deletePersistentAll();
		
		Query query3 = pm.newQuery(Episode.class);
		query3.deletePersistentAll();
		
		Query query4 = pm.newQuery(ImportedSeriesList.class);
		query4.deletePersistentAll();
		
		Query query5 = pm.newQuery(Image.class);
		query5.deletePersistentAll();

		log.warning("Finished cleaning database, took "
				+ (new Date().getTime() - init) + "ms");
		
	}
	
}
