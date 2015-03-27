package se.kth.myseries.server.series;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;

import se.kth.myseries.client.series.ISeriesService;
import se.kth.myseries.server.auth.UserControlHelper;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.server.persistence.SeriesDAO;
import se.kth.myseries.server.persistence.UsersDAO;
import se.kth.myseries.server.persistence.entity.Episode;
import se.kth.myseries.server.persistence.entity.MySeriesUser;
import se.kth.myseries.server.persistence.entity.Series;
import se.kth.myseries.server.series.tvdb.TheTVDBAdapter;
import se.kth.myseries.shared.dto.CommentsDTO;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.RankDTO;
import se.kth.myseries.shared.dto.SeasonDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.ImportingInProgressException;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;
import se.kth.myseries.shared.exceptions.TvDbException;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * Server side implementation of ISeriesService
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeriesServiceImpl extends RemoteServiceServlet implements
ISeriesService {
	
	private static final long		serialVersionUID	= 1L;
	
	private final UserControlHelper	userHelper			= new UserControlHelper();
	
	private final TheTVDBAdapter	tvdbAdapter			= new TheTVDBAdapter();
	
	private static final Logger		log					= Logger.getLogger(SeriesServiceImpl.class
			.getName());
	
	@Cacheable
	public SeriesDTO getSeries(final Long seriesID) throws DatabaseException {
		SeriesDAO dao = new SeriesDAO();
		
		try {
			Series s = dao.getSeriesByID(seriesID);
			return dao.convert(s);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			dao.close();
		}
	}
	
	@Override
	public SeriesDTO getSeries(final Long seriesID, final String signed_request)
	throws SocialNetworkException, DatabaseException {
		return markAsWatched(getSeries(seriesID), signed_request);
	}
	
	@Override
	@Cacheable
	public Set<UserDTO> getWatchers(final Long seriesID) {
		SeriesDAO sDao = new SeriesDAO();
		UsersDAO uDao = new UsersDAO();
		Set<UserDTO> set = null;
		try {
			Series s = sDao.getSeriesByID(seriesID);
			set = uDao.getByID(s.getWatchers());
		} catch (Exception e) {
			log.severe(e.toString());
			set = new HashSet<UserDTO>();
		} finally {
			sDao.close();
			uDao.close();
		}
		return set;
	}
	
	@Override
	@Cacheable
	public Set<UserDTO> getWatchers(final Long seriesID, final Long seasonID,
			final Long episodeID) {
		SeriesDAO sDao = new SeriesDAO();
		UsersDAO uDao = new UsersDAO();
		Set<UserDTO> set = null;
		try {
			Episode e = sDao.getEpisodeByID(seriesID, seasonID, episodeID);
			set = uDao.getByID(e.getWatchers());
		} catch (Exception e) {
			log.severe(e.toString());
			set = new HashSet<UserDTO>();
		} finally {
			sDao.close();
			uDao.close();
		}
		return set;
	}
	
	@Cacheable
	public SeriesDTO getSeriesTheTvDB(final String tvdbID)
	throws DatabaseException, ImportingInProgressException {
		SeriesDAO dao = new SeriesDAO();
		
		try {
			
			Series s = dao.getSeriesByTvDbID(tvdbID);
			if (s == null)
				throw new JDOObjectNotFoundException();
			return dao.convert(s);
			
		} catch (JDOObjectNotFoundException e) {
			
			// only happens because search uses TheTVDB
			log.warning("Series id[" + tvdbID
					+ "] was not found on DB, starting importing task");
			Queue queue = QueueFactory.getQueue("series-import");
			queue.add(url("/admin/tvdbImport").param("id", tvdbID).method(
					Method.GET));
			throw new ImportingInProgressException();
			
		} catch (JDOException e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			dao.close();
		}
	}
	
	@Override
	public SeriesDTO getSeriesTheTvDB(final String tvdbID,
			final String signed_request) throws SocialNetworkException,
			DatabaseException, ImportingInProgressException {
		
		return markAsWatched(getSeriesTheTvDB(tvdbID), signed_request);
	}
	
	@Override
	@Cacheable
	public List<SeriesDTO> getTopSeriesNames() {
		
		SeriesDAO dao = new SeriesDAO();
		try {
			return dao.convert(dao.getTopSeries());
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			dao.close();
		}
		return new ArrayList<SeriesDTO>();
	}
	
	@Override
	public List<SeriesDTO> getTopSeriesNames(final String signed_request)
	throws MySeriesFacebookException {
		
		// FIXME get only the more important ones? how to rank them?
		SeriesDAO sDao = new SeriesDAO();
		UsersDAO uDao = new UsersDAO();
		try {
			Set<Series> series = sDao.getTopSeries();
			Set<Series> watchlist = sDao.getSeriesByID(getCurrentUser(uDao,
					signed_request).getWatchlist());
			
			series.removeAll(watchlist);
			return sDao.convert(series);
			
		} catch (MySeriesFacebookException e) {
			log.severe(e.toString());
			throw e;
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			sDao.close();
			uDao.close();
		}
		return new ArrayList<SeriesDTO>();
	}
	
	@Override
	@Cacheable
	public SeasonDTO getSeason(final Long seriesID, final Integer seasonNbr) {
		
		SeriesDAO dao = new SeriesDAO();
		
		try {
			return dao.convert(dao.getSeasonByID(seriesID, seasonNbr));
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			dao.close();
		}
		
		return new SeasonDTO();
	}
	
	@Override
	@Cacheable
	public EpisodeDTO getEpisode(final Long seriesID, final Long seasonID,
			final Long episodeID) {
		SeriesDAO dao = new SeriesDAO();
		
		try {
			return dao.convert(dao
					.getEpisodeByID(seriesID, seasonID, episodeID));
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			dao.close();
		}
		
		return new EpisodeDTO();
	}
	
	@Override
	public List<SeriesDTO> search(final String seriesName) throws TvDbException {
		
		if (seriesName == null || seriesName.isEmpty())
			return getTopSeriesNames();
		else
			return doSearch(seriesName);
		
	}
	
	/**
	 * @param seriesName
	 * @throws TvDbException
	 */
	@Cacheable
	private List<SeriesDTO> doSearch(final String seriesName)
	throws TvDbException {
		SeriesDAO sDao = new SeriesDAO();
		try {
			List<SeriesDTO> result = sDao.convert(tvdbAdapter
					.search(seriesName));
			log.fine("Search for [" + seriesName + "], returned ["
					+ result.size() + "] results");
			return result;
		} catch (Exception e) {
			log.severe(e.toString());
			throw new TvDbException(e.toString());
		} finally {
			sDao.close();
		}
	}
	
	/**
	 * @param series
	 * @param signed_request
	 * @return
	 * @throws MySeriesFacebookException
	 */
	private SeriesDTO markAsWatched(final SeriesDTO dto,
			final String signed_request) throws SocialNetworkException {
		UsersDAO dao = new UsersDAO();
		try {
			dto.watchedByCurrentUser = getCurrentUser(dao, signed_request)
			.watches(dto);
		} finally {
			dao.close();
		}
		return dto;
	}
	
	private MySeriesUser getCurrentUser(final UsersDAO dao,
			final String signed_request) throws SocialNetworkException {
		UserDTO userDTO = userHelper.getUserInfo(signed_request);
		return dao.getByFacebookID(userDTO.facebook_id);
	}
	
	@Override
	public CommentsDTO getComments(final Long seriesID, final Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CommentsDTO getComments(final Long seriesID, final Long seasonID,
			final Long episodeID, final Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addComment(final String signedRequest, final Long seriesID,
			final String comment) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addComment(final String signedRequest, final Long seriesID,
			final Long seasonID, final Long episodeID, final String comment) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addRank(final String signedRequest, final Long seriesID,
			final Double rankNum) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addRank(final String signedRequest, final Long seriesID,
			final Long seasonID, final Long episodeID, final Double rankNum) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public RankDTO getRanks(final String signedRequest, final Long seriesID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RankDTO getRanks(final String signedRequest, final Long seriesID,
			final Long seasonID, final Long episodeID) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
