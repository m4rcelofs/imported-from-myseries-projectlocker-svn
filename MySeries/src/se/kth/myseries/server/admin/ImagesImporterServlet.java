package se.kth.myseries.server.admin;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.kth.myseries.server.ServerConstants;
import se.kth.myseries.server.persistence.ImagesDAO;
import se.kth.myseries.server.persistence.SeriesDAO;
import se.kth.myseries.server.persistence.entity.Image;
import se.kth.myseries.server.persistence.entity.Season;
import se.kth.myseries.server.persistence.entity.Series;
import se.kth.myseries.server.series.tvdb.ImagesHelper;

/**
 * MySeries <br>
 * Task that can be invoked to download an image on the background
 * 
 * @author Marcelo
 * @since 10/11/2010
 */
public class ImagesImporterServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(ImagesImporterServlet.class
			.getName());
	
	private ImagesHelper		helper;
	
	@Override
	public void init() {
		helper = new ImagesHelper();
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		try {
			
			long init = new Date().getTime();
			log.fine("Starting image import job");
			
			boolean importedSomething = false;
			String id = req.getParameter("id");
			String imgUrl = req.getParameter("url");
			String type = req.getParameter("t");
			
			if (id == null || imgUrl == null || type == null) {
				log.warning("parameter is null: id[" + id + "] imgUrl["
						+ imgUrl + "] t[" + type + "]");
				return;
			}
			
			importedSomething = importImage(id, imgUrl, type);
			if (importedSomething)
				log.fine("Finished image import task, took "
						+ (new Date().getTime() - init) + "ms; imported: "
						+ imgUrl);
			
		} catch (Exception e) {
			// avoids retries
			log.warning(e.toString());
		}
	}
	
	private boolean importImage(final String id, final String imgUrl,
			final String type) {
		
		boolean response = false;
		
		boolean createThumb = true;
		
		Image image = download(imgUrl, createThumb);
		if (image != null)
			response = true;
		
		SeriesDAO dao = new SeriesDAO();
		ImagesDAO img = new ImagesDAO();
		
		img.save(image);
		
		if (ServerConstants.SERIES_BANNER.equals(type))
			saveSeriesBanner(dao, image, id);
		else
			if (ServerConstants.SERIES_POSTER.equals(type))
				saveSeriesPoster(dao, image, id);
			else
				if (ServerConstants.SEASON_BANNER.equals(type))
					saveSeasonBanner(dao, image, id);
		
		img.close();
		dao.close();
		
		return response;
		
	}
	
	/**
	 * @param link
	 */
	private Image download(final String link, final boolean createThumbnail) {
		log.fine("Downloading " + link);
		return helper.downloadImage(link, createThumbnail);
	}
	
	/**
	 * @param dao
	 * @param image
	 * @param link
	 */
	private void saveSeasonBanner(final SeriesDAO dao, final Image image,
			final String id) {
		String seriesId = id.substring(0, id.indexOf("|"));
		String seasonId = id.substring(id.indexOf("|") + 1);
		Season season = dao.getSeasonByID(seriesId, seasonId);
		season.addBanner(image.getId());
		dao.save(season);
	}
	
	/**
	 * @param dao
	 * @param image
	 * @param link
	 */
	private void saveSeriesPoster(final SeriesDAO dao, final Image image,
			final String id) {
		Series series = dao.getSeriesByID(id);
		series.addPoster(image.getId());
		dao.save(series);
	}
	
	/**
	 * @param dao
	 * @param image
	 * @param link
	 */
	private void saveSeriesBanner(final SeriesDAO dao, final Image image,
			final String id) {
		Series series = dao.getSeriesByID(id);
		series.addBanner(image.getId());
		dao.save(series);
	}
	
}
