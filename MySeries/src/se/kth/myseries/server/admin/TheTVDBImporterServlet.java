package se.kth.myseries.server.admin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.kth.myseries.server.persistence.PMF;
import se.kth.myseries.server.persistence.SeriesDAO;
import se.kth.myseries.server.persistence.entity.Series;
import se.kth.myseries.server.series.tvdb.TheTVDBAdapter;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 09/11/2010
 */
public class TheTVDBImporterServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(TheTVDBImporterServlet.class
			.getName());
	
	private TheTVDBAdapter		tvdb;
	private ImportedSeriesList	importedSeries;
	private SeriesDAO			dao;
	
	private PersistenceManager	pm;
	
	@Override
	public void init() {
		
		log.info("Initializing importer servlet");
		
		tvdb = new TheTVDBAdapter();
		try {
			pm = PMF.getManager();
			Extent<ImportedSeriesList> extent = pm.getExtent(
					ImportedSeriesList.class, false);
			importedSeries = extent.iterator().next();
		} catch (Exception ignore) {}
		
		dao = new SeriesDAO();
		
		if (importedSeries == null)
			importedSeries = new ImportedSeriesList();
		pm.makePersistent(importedSeries);
		
		log.info("Initialization complete");
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		String tvDbId = req.getParameter("id");
		try {
			if (tvDbId == null || tvDbId.isEmpty())
				return;
			
			log.fine("Trying to import data for series id " + tvDbId + "... ");
			
			if (!importedSeries.contains(tvDbId)) {
				
				Series series = tvdb.getSeriesInformation(tvDbId);
				
				if (series.getName() == null || series.getName().isEmpty()
						|| series.getSeasons() == null
						|| series.getSeasons().isEmpty()) {
					log.info("Series id[" + series.getTvDbId()
							+ "] has no name or seasons, avoiding import");
					return;
				}
				
				dao.save(series);
				
				log.fine("Trying to import posters for series id " + tvDbId
						+ "... ");
				tvdb.queueImageDownload(series);
				
				importedSeries.add(tvDbId);
				pm.makePersistent(this.importedSeries);
				
			} else
				log.fine("Series id[" + tvDbId
						+ "] was already imported before");
			
		} catch (Exception e) {
			// avoids retries
			log.warning(e.toString() + " id[" + tvDbId + "]");
		}
	}
	
	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	public static class ImportedSeriesList {
		
		@PrimaryKey
		@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
		private Long		id;
		
		@Persistent
		private Set<String>	tvDbIds;
		
		public void add(final String tvDbId) {
			if (tvDbIds == null)
				tvDbIds = new HashSet<String>();
			this.tvDbIds.add(tvDbId);
		}
		
		public boolean contains(final String tvDbId) {
			if (tvDbIds == null)
				return false;
			
			return this.tvDbIds.contains(tvDbId);
		}
		
		public void setId(final Long id) {
			this.id = id;
		}
		
		public Long getId() {
			return id;
		}
	}
}
