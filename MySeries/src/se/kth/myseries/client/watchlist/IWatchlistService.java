package se.kth.myseries.client.watchlist;

import java.util.List;

import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
@RemoteServiceRelativePath("watchlist")
public interface IWatchlistService extends RemoteService {
	
	/**
	 * Returns the episodes on the user watchlist for a given date
	 * 
	 * @param signed_request
	 * @param initialDate
	 *            Representation of a date, yyyyMMdd
	 * @param finalDate
	 *            Representation of a date, yyyyMMdd
	 * @return
	 * @throws DatabaseException
	 */
	public WatchlistEpisodesDTO getWatchlist(String signed_request,
			Integer initialDate, Integer finalDate)
	throws SocialNetworkException, DatabaseException;
	
	/**
	 * Only the name and image of the series are filled by this
	 * method for performance reasons
	 * 
	 * @param signed_request
	 * @return
	 * @throws DatabaseException
	 * @throws SocialNetworkException
	 */
	public List<SeriesDTO> getUserWatchlist(String signed_request)
	throws DatabaseException, SocialNetworkException;
	
	/**
	 * Gets the watchlist from a specific user <br />
	 * Only the name and image of the series are filled by this
	 * method for performance reasons
	 * 
	 * @param signed_request
	 * @return
	 * @throws DatabaseException
	 */
	public List<SeriesDTO> getUserWatchlist(Long userID)
	throws DatabaseException;
	
	/**
	 * Returns the series that were once on the user watchlist, have some
	 * episode watched, but are not on the watchlist anymore <br />
	 * Only the name and image of the series are filled by this
	 * method for performance reasons
	 * 
	 * @param signed_request
	 * @return
	 * @throws DatabaseException
	 */
	public List<SeriesDTO> getUserOldWatchlist(String signed_request)
	throws SocialNetworkException, DatabaseException;
	
	/**
	 * Returns the series that were once on the user watchlist, have some
	 * episode watched, but are not on the watchlist anymore <br />
	 * Only the name and image of the series are filled by this
	 * method for performance reasons
	 * 
	 * @param signed_request
	 * @return
	 * @throws DatabaseException
	 */
	public List<SeriesDTO> getUserOldWatchlist(Long userID)
	throws DatabaseException;
	
	public Boolean addToUserWatchlist(String signed_request, Long seriesID)
	throws SocialNetworkException, DatabaseException;
	
	public Boolean removeFromUserWatchlist(String signed_request, Long seriesID)
	throws SocialNetworkException, DatabaseException;
}
