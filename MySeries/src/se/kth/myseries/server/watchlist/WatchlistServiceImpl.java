package se.kth.myseries.server.watchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import se.kth.myseries.client.watchlist.IWatchlistService;
import se.kth.myseries.server.auth.UserControlHelper;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.server.persistence.SeriesDAO;
import se.kth.myseries.server.persistence.UsersDAO;
import se.kth.myseries.server.persistence.entity.MySeriesUser;
import se.kth.myseries.server.persistence.entity.Series;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class WatchlistServiceImpl extends RemoteServiceServlet implements
IWatchlistService {
	
	private static final long		serialVersionUID	= 1L;
	
	private final UserControlHelper	userHelper			= new UserControlHelper();
	
	private static final Logger		log					= Logger.getLogger(WatchlistServiceImpl.class
			.getName());
	
	@Override
	public WatchlistEpisodesDTO getWatchlist(final String signed_request,
			final Integer initialDate, final Integer finalDate)
	throws MySeriesFacebookException, DatabaseException {
		
		UsersDAO uDao = new UsersDAO();
		SeriesDAO sDao = new SeriesDAO();
		
		try {
			
			MySeriesUser user = getCurrentUser(uDao, signed_request);
			
			if (user.getWatchlist() == null || user.getWatchlist().isEmpty())
				return new WatchlistEpisodesDTO();
			
			WatchlistEpisodesDTO dto = sDao.getEpisodesFromWatchlist(
					user.getWatchlist(), initialDate, finalDate);
			
			log.fine("getWatchlist (episodes) uid[" + user.getFacebookId()
					+ "], initialDate[" + initialDate + "], finalDate["
					+ finalDate + "], episodes returned for ["
					+ dto.episodes.keySet().size() + "] days");
			
			return dto;
		} catch (MySeriesFacebookException e) {
			log.severe(e.toString());
			throw e;
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
			sDao.close();
		}
		
	}
	
	@Override
	public List<SeriesDTO> getUserWatchlist(final String signed_request)
	throws SocialNetworkException, DatabaseException {
		return this.getUserWatchlist(userHelper.getUserID(signed_request));
	}
	
	@Override
	public List<SeriesDTO> getUserOldWatchlist(final String signed_request)
	throws SocialNetworkException, DatabaseException {
		return this.getUserOldWatchlist(userHelper.getUserID(signed_request));
	}
	
	@Override
	public List<SeriesDTO> getUserWatchlist(final Long userID)
	throws DatabaseException {
		UsersDAO uDao = new UsersDAO();
		SeriesDAO sDao = new SeriesDAO();
		
		try {
			MySeriesUser user = uDao.getByID(userID);
			Set<Series> watchlist = sDao.getSeriesByID(user.getWatchlist());
			List<SeriesDTO> dtos = convertSimple(watchlist);
			log.fine("returning watchlist for uid[" + user.getFacebookId()
					+ "], " + dtos);
			return dtos;
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
			sDao.close();
		}
	}
	
	@Override
	@Cacheable
	public List<SeriesDTO> getUserOldWatchlist(final Long userID)
	throws DatabaseException {
		UsersDAO uDao = new UsersDAO();
		SeriesDAO sDao = new SeriesDAO();
		
		try {
			MySeriesUser user = uDao.getByID(userID);
			Set<Series> oldWatchlist = sDao.getSeriesByID(user
					.getOldWatchlist());
			log.fine("returning old watchlist for uid[" + user.getFacebookId()
					+ "], size[" + oldWatchlist.size() + "]");
			return convertSimple(oldWatchlist);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
			sDao.close();
		}
	}
	
	@Override
	public Boolean addToUserWatchlist(final String signed_request,
			final Long seriesID) throws MySeriesFacebookException,
			DatabaseException {
		
		UsersDAO uDao = new UsersDAO();
		SeriesDAO sDao = new SeriesDAO();
		
		try {
			
			MySeriesUser user = getCurrentUser(uDao, signed_request);
			Series series = sDao.getSeriesByID(seriesID);
			
			log.fine("adding series id[" + series.getId()
					+ "], to user's watchlist: uid[" + user.getFacebookId()
					+ "]");
			
			user.addToWatchlist(series.getId());
			series.addWatcher(user.getId());
			
			uDao.save(user);
			sDao.save(series);
			
		} catch (MySeriesFacebookException e) {
			log.severe(e.toString());
			throw e;
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
			sDao.close();
		}
		
		return true;
	}
	
	@Override
	public Boolean removeFromUserWatchlist(final String signed_request,
			final Long seriesID) throws MySeriesFacebookException,
			DatabaseException {
		UsersDAO uDao = new UsersDAO();
		SeriesDAO sDao = new SeriesDAO();
		
		try {
			MySeriesUser user = getCurrentUser(uDao, signed_request);
			Series series = sDao.getSeriesByID(seriesID);
			
			log.fine("removing series id[" + series.getId()
					+ "], to user's watchlist: uid[" + user.getFacebookId()
					+ "]");
			
			user.removeFromWatchlist(series.getId());
			series.removeWatcher(user.getId());
			
			uDao.save(user);
			sDao.save(series);
			
		} catch (MySeriesFacebookException e) {
			log.severe(e.toString());
			throw e;
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
			sDao.close();
		}
		
		return true;
	}
	
	private MySeriesUser getCurrentUser(final UsersDAO dao,
			final String signed_request) throws SocialNetworkException {
		UserDTO userDTO = userHelper.getUserInfo(signed_request);
		return dao.getByFacebookID(userDTO.facebook_id);
	}
	
	/**
	 * @param oldWatchlist
	 * @return
	 */
	private List<SeriesDTO> convertSimple(final Set<Series> watchlist) {
		List<SeriesDTO> dtos = new ArrayList<SeriesDTO>();
		
		if (watchlist != null)
			for (Series s : watchlist)
				dtos.add(convertSimple(s));
		
		return dtos;
	}
	
	/**
	 * @param s
	 * @return
	 */
	private SeriesDTO convertSimple(final Series s) {
		SeriesDTO dto = new SeriesDTO();
		{
			dto.id = s.getId().getId();
			dto.tvDbId = s.getTvDbId();
			dto.name = s.getName();
			dto.bannerLinks = s.getBannerLinks();
			dto.posterLinks = s.getPosterLinks();
		}
		return dto;
	}
	
}
