package se.kth.myseries.server.series.tvdb;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import se.kth.myseries.server.ServerConstants;
import se.kth.myseries.server.persistence.entity.Episode;
import se.kth.myseries.server.persistence.entity.Season;
import se.kth.myseries.server.persistence.entity.Series;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;
import com.moviejukebox.thetvdb.TheTVDB;
import com.moviejukebox.thetvdb.model.Banner;
import com.moviejukebox.thetvdb.model.Banners;

/**
 * MySeries <br>
 * A class that uses TheTVDB API to get series data
 * 
 * @author Marcelo
 * @since 05/11/2010
 */
public class TheTVDBAdapter {
	
	private static final Logger	log	= Logger.getLogger(TheTVDBAdapter.class
			.getName());
	
	private final TheTVDB		tvdb;
	
	public TheTVDBAdapter() {
		tvdb = new TheTVDB("8C7A920D3410A42B");
	}
	
	/**
	 * Searches TheTVDB for some series
	 * 
	 * @param text
	 * @return
	 */
	public List<Series> search(final String text) {
		List<com.moviejukebox.thetvdb.model.Series> result = tvdb.searchSeries(
				text, "en");
		return convertSeries(result);
		
	}
	
	/**
	 * Gets the basic Series information (no seasons/episodes)
	 * 
	 * @param tvDbIds
	 * @return
	 */
	public Series getSeriesSimple(final String tvDbId) {
		
		log.info("Getting series simple from TheTVDB: " + tvDbId);
		com.moviejukebox.thetvdb.model.Series series = tvdb.getSeries(tvDbId,
				null); // default en
		return convertSeries(series);
		
	}
	
	public Series getSeriesInformation(final String tvDbId) {
		
		log.fine("Getting series info from TheTVDB: " + tvDbId);
		long msInit = new Date().getTime();
		
		Series series = convertSeries(tvdb.getSeries(tvDbId, "en"));
		
		Map<Integer, List<com.moviejukebox.thetvdb.model.Episode>> episodes = splitSeasons(tvdb
				.getAllEpisodes(series.getTvDbId(), "en"));
		
		for (Integer s : episodes.keySet()) {
			Season season = new Season();
			{
				season.setNumber(s);
				season.setSeries(series);
				try {
					Integer year = Integer.parseInt(tvdb.getSeasonYear(
							series.getTvDbId(), s, "en"));
					season.setYear(year);
				} catch (Exception ignore) {}
			}
			
			for (com.moviejukebox.thetvdb.model.Episode e : episodes.get(s)) {
				Episode episode = convertEpisode(e);
				episode.setSeason(season);
				season.addEpisode(episode);
			}
			series.addSeason(season);
			season.setSeries(series);
		}
		
		long msTotal = new Date().getTime() - msInit;
		log.fine("Getting series info from TheTVDB: " + tvDbId + ", "
				+ series.getName() + " took " + msTotal + "ms");
		
		return series;
	}
	
	/**
	 * Parses all image links for a series from TheTVDB, and adds them to the
	 * task queue to be downloaded at a later time. <br />
	 * Has to be called <b>after</b> the Series is recorded to the database, so
	 * it already has an ID
	 * 
	 * @param series
	 */
	public void queueImageDownload(final Series series) {
		
		log.fine("Getting banners from TheTVDB: " + series.getTvDbId());
		long msInit = new Date().getTime();
		
		Queue queue = QueueFactory.getQueue("images-import");
		
		Banners banners = tvdb.getBanners(series.getTvDbId());
		
		String taskUrl = "/admin/importImages";
		
		for (Banner banner : banners.getSeriesList())
			if ("en".equalsIgnoreCase(banner.getLanguage()))
				queue.add(url(taskUrl)
						.param("id", Long.toString(series.getId().getId()))
						.param("url", banner.getUrl())
						.param("t", ServerConstants.SERIES_BANNER)
						.method(Method.GET));
		
		for (Banner poster : banners.getPosterList())
			if ("en".equalsIgnoreCase(poster.getLanguage()))
				queue.add(url(taskUrl)
						.param("id", Long.toString(series.getId().getId()))
						.param("url", poster.getUrl())
						.param("t", ServerConstants.SERIES_POSTER)
						.method(Method.GET));
		
		for (Banner seasonBanner : banners.getSeasonList())
			if ("en".equalsIgnoreCase(seasonBanner.getLanguage())) {
				Season season = series.getSeason(seasonBanner.getSeason());
				String id = Long.toString(series.getId().getId()) + "|"
				+ Long.toString(season.getId().getId());
				queue.add(url(taskUrl).param("id", id)
						.param("url", seasonBanner.getUrl())
						.param("t", ServerConstants.SEASON_BANNER)
						.method(Method.GET));
			}
		
		int totalSize = banners.getSeriesList().size()
		+ banners.getPosterList().size()
		+ banners.getSeasonList().size();
		
		long msTotal = new Date().getTime() - msInit;
		log.fine("Parsed " + totalSize + " banners from TheTVDB: "
				+ series.getTvDbId() + ", " + series.getName() + " took "
				+ msTotal + "ms");
		
	}
	
	private List<Series> convertSeries(
			final List<com.moviejukebox.thetvdb.model.Series> series) {
		List<Series> list = new ArrayList<Series>(series.size());
		
		if (series != null)
			for (com.moviejukebox.thetvdb.model.Series s : series)
				try {
					list.add(convertSeries(s));
				} catch (Exception e) {
					log.severe("Could not parse series at all: tvdbid["
							+ s.getId());
				}
				
				return list;
	}
	
	private Series convertSeries(
			final com.moviejukebox.thetvdb.model.Series series) {
		
		Series ret = new se.kth.myseries.server.persistence.entity.Series();
		{
			ret.setTvDbId(series.getId());
			ret.setAirsDayOfWeek(series.getAirsDayOfWeek());
			ret.setAirsTime(series.getAirsTime());
			ret.setDescription(new Text(series.getOverview()));
			try {
				String firstAired = series.getFirstAired();
				if (firstAired != null && !firstAired.isEmpty()) {
					firstAired = firstAired.replace("-", "");
					ret.setFirstAired(Integer.valueOf(firstAired));
				}
			} catch (NumberFormatException e) {
				log.warning("Could not parse series first aired; tvdbid["
						+ series.getId() + "]: " + e.toString());
			}
			ret.setImdbId(series.getImdbId());
			ret.setLanguage(series.getLanguage());
			ret.setLastUpdated(series.getLastUpdated());
			ret.setName(series.getSeriesName());
			ret.setNetwork(series.getNetwork());
			try {
				if (series.getRuntime() != null
						&& !series.getRuntime().isEmpty())
					ret.setRuntime(Integer.parseInt(series.getRuntime()));
			} catch (NumberFormatException e) {
				log.warning("Could not parse series runtime; tvdbid["
						+ series.getId() + "]: " + e.toString());
			}
			ret.setStatus(series.getStatus());
			ret.addActors(series.getActors());
			ret.addGenres(series.getGenres());
		}
		
		return ret;
	}
	
	/**
	 * Splits a list of episodes in a Map<SeasonNbr, Episode>
	 * 
	 * @param episodes
	 * @return
	 */
	private Map<Integer, List<com.moviejukebox.thetvdb.model.Episode>> splitSeasons(
			final List<com.moviejukebox.thetvdb.model.Episode> episodes) {
		
		Map<Integer, List<com.moviejukebox.thetvdb.model.Episode>> split = new HashMap<Integer, List<com.moviejukebox.thetvdb.model.Episode>>();
		
		for (com.moviejukebox.thetvdb.model.Episode e : episodes) {
			if (split.get(e.getSeasonNumber()) == null)
				split.put(e.getSeasonNumber(),
						new ArrayList<com.moviejukebox.thetvdb.model.Episode>());
			
			split.get(e.getSeasonNumber()).add(e);
		}
		
		return split;
	}
	
	private Episode convertEpisode(
			final com.moviejukebox.thetvdb.model.Episode episode) {
		Episode e = new Episode();
		{
			e.setDescription(new Text(episode.getOverview()));
			try {
				String firstAired = episode.getFirstAired();
				firstAired = firstAired.replace("-", "");
				e.setFirstAired(Integer.parseInt(firstAired));
			} catch (NumberFormatException ignore) {
				log.warning("Could not parse firstAired from series id["
						+ episode.getSeriesId() + "] episode id["
						+ episode.getId() + "]");
			}
			e.setImdbId(episode.getImdbId());
			e.setLanguage(e.getLanguage());
			e.setLastUpdated(episode.getLastUpdated());
			e.setName(episode.getEpisodeName());
			e.setNumber(episode.getEpisodeNumber());
			e.setTvDbId(episode.getId());
			e.addDirector(episode.getDirectors());
			e.addGuestStars(episode.getGuestStars());
			e.addWriters(episode.getWriters());
		}
		
		return e;
	}
	
}
