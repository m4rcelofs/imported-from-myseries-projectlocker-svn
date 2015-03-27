package se.kth.myseries.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.Query;

import se.kth.myseries.server.persistence.entity.Episode;
import se.kth.myseries.server.persistence.entity.Season;
import se.kth.myseries.server.persistence.entity.Series;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.SeasonDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * MySeries <br>
 * Data Access Object for the Series entity; don't forget to use
 * <code>close()</code> after all the desired operations
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeriesDAO extends ADAO {
	
	/**
	 * @param seriesId
	 * @return
	 */
	public Series getSeriesByID(final Key seriesId) {
		return pm.getObjectById(Series.class, seriesId);
	}
	
	public Set<Series> getSeriesByID(final Set<Key> series) {
		Set<Series> set = new HashSet<Series>(series.size());
		
		if (series != null)
			for (Key key : series)
				try {
					set.add(getSeriesByID(key));
				} catch (Exception ignore) {
					log.warning(ignore.toString());
				}
				
				return set;
	}
	
	public Series getSeriesByID(final Long id) {
		Key key = KeyFactory.createKey(Series.class.getSimpleName(), id);
		return getSeriesByID(key);
	}
	
	public Series getSeriesByID(final String id) {
		return getSeriesByID(Long.valueOf(id));
	}
	
	/**
	 * @param tvdbID
	 */
	public Series getSeriesByTvDbID(final String tvdbID) {
		Query query = pm.newQuery(Series.class);
		try {
			query.setFilter("tvDbId == tvDbIdParam");
			query.declareParameters("String tvDbIdParam");
			
			@SuppressWarnings("unchecked")
			List<Series> result = (List<Series>) query.execute(tvdbID);
			if (result != null)
				return result.get(0);
			else
				return null;
		} finally {
			query.closeAll();
		}
	}
	
	/**
	 * @param series
	 */
	public void save(final Series series) {
		super.save(series);
		
		// gambiarra
		for (Season s : series.getSeasons())
			for (Episode e : s.getEpisodes())
				e.setSeries(series.getId());
		
		super.save(series);
	}
	
	/**
	 * @param season
	 */
	public void save(final Season season) {
		super.save(season);
	}
	
	/**
	 * @param seasonId
	 * @return
	 */
	public Season getSeasonByID(final Key seasonId) {
		return pm.getObjectById(Season.class, seasonId);
	}
	
	public Season getSeasonByID(final String seriesId, final String seasonId) {
		Key key = new KeyFactory.Builder(Series.class.getSimpleName(),
				Long.valueOf(seriesId)).addChild(Season.class.getSimpleName(),
						Long.valueOf(seasonId)).getKey();
		return getSeasonByID(key);
	}
	
	/**
	 * @param seriesID
	 * @param seasonNbr
	 * @return
	 */
	public Season getSeasonByID(final Long seriesID, final Integer seasonNbr) {
		Series s = getSeriesByID(seriesID);
		return s.getSeason(seasonNbr);
	}
	
	public Set<Series> getTopSeries() {
		
		// FIXME not good, optimize after deciding what's a "top serie"
		Set<Series> list = new HashSet<Series>();
		
		Extent<Series> extent = pm.getExtent(Series.class, false);
		
		for (Series s : extent)
			list.add(s);
		
		return list;
		
	}
	
	/**
	 * @param watchlist
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public WatchlistEpisodesDTO getEpisodesFromWatchlist(
			final Set<Key> watchlist, final Integer initialDate,
			final Integer finalDate) {
		
		WatchlistEpisodesDTO dto = new WatchlistEpisodesDTO();
		
		Query query = pm.newQuery(Episode.class);
		{
			query.setFilter("firstAired >=" + initialDate + " && "
					+ "firstAired <= " + finalDate + " && "
					+ "series == :seriesParam");
			// could use an IN query aswell?
		}
		
		try {
			
			@SuppressWarnings("unchecked")
			List<Episode> result = (List<Episode>) query.execute(watchlist);
			
			for (Episode e : result)
				dto.addEpisode(e.getFirstAired(), convertSimple(e));
			
			return dto;
		} finally {
			query.closeAll();
		}
	}
	
	/**
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @return
	 */
	public Episode getEpisodeByID(final Long seriesID, final Long seasonID,
			final Long episodeID) {
		
		Key key = new KeyFactory.Builder(Series.class.getSimpleName(), seriesID)
				.addChild(Season.class.getSimpleName(), seasonID)
				.addChild(Episode.class.getSimpleName(), episodeID).getKey();
		
		return pm.getObjectById(Episode.class, key);
	}
	
	/**
	 * @param allSeries
	 * @return
	 * @throws MySeriesFacebookException
	 */
	public List<SeriesDTO> convert(final Collection<Series> allSeries) {
		List<SeriesDTO> dtos = new ArrayList<SeriesDTO>();
		
		if (allSeries != null)
			for (Series s : allSeries)
				dtos.add(convert(s));
		
		return dtos;
		
	}
	
	/**
	 * @param e
	 * @return
	 */
	public EpisodeDTO convertSimple(final Episode e) {
		EpisodeDTO dto = new EpisodeDTO();
		{
			dto.id = e.getId().getId();
			dto.episodeName = e.getName();
			dto.seriesName = e.getSeason().getSeries().getName();
			dto.season = e.getSeason().getNumber();
			dto.episodeNbr = e.getNumber();
			dto.firstAired = e.getFirstAired();
		}
		return dto;
	}
	
	/**
	 * @param episodeByID
	 * @return
	 */
	public EpisodeDTO convert(final Episode episode) {
		EpisodeDTO dto = new EpisodeDTO();
		{
			dto.description = episode.getDescription().toString();
			dto.directors = new ArrayList<String>(episode.getDirectors());
			dto.episodeName = episode.getName();
			dto.episodeNbr = episode.getNumber();
			dto.firstAired = episode.getFirstAired();
			dto.guestStars = new ArrayList<String>(episode.getGuestStars());
			dto.id = episode.getId().getId();
			dto.season = episode.getSeason().getNumber();
			dto.seriesName = episode.getSeason().getSeries().getName();
			dto.writers = new ArrayList<String>(episode.getWriters());
		}
		return dto;
	}
	
	public SeriesDTO convert(final Series series) {
		SeriesDTO dto = new SeriesDTO();
		if (series == null)
			return null;
		
		{
			dto.actors = series.getActors() == null ? null
					: new ArrayList<String>(series.getActors());
			dto.airsDayOfWeek = series.getAirsDayOfWeek();
			dto.airsTime = series.getAirsTime();
			dto.description = series.getDescription() == null ? "" : series
					.getDescription().getValue();
			dto.firstAired = series.getFirstAired();
			dto.genres = series.getGenres() == null ? null
					: new ArrayList<String>(series.getGenres());
			dto.id = series.getId() == null ? null : series.getId().getId();
			dto.imdbId = series.getImdbId();
			dto.name = series.getName();
			dto.nbrEpisodes = series.getNumberOfEpisodes();
			dto.nbrSeasons = series.getNumberOfSeasons();
			dto.network = series.getNetwork();
			dto.runtime = series.getRuntime();
			dto.status = series.getStatus();
			dto.tvDbId = series.getTvDbId();
			dto.bannerLinks = series.getBannerLinks();
			dto.posterLinks = series.getPosterLinks();
		}
		return dto;
	}
	
	/**
	 * @param seasonByID
	 * @return
	 */
	public SeasonDTO convert(final Season season) {
		SeasonDTO dto = new SeasonDTO();
		{
			dto.id = season.getId().getId();
			dto.seasonNbr = season.getNumber();
			dto.year = season.getYear();
			dto.bannerURLs = season.getBannerLinks();
			dto.episodeNames = season.getEpisodeNames();
		}
		return dto;
	}
}
