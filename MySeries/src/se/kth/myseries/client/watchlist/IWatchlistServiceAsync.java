package se.kth.myseries.client.watchlist;

import java.util.List;

import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public interface IWatchlistServiceAsync {
	
	void getWatchlist(String signed_request, Integer initialDate,
			Integer finalDate, AsyncCallback<WatchlistEpisodesDTO> callback);
	
	void getUserWatchlist(String signed_request,
			AsyncCallback<List<SeriesDTO>> callback);
	
	void addToUserWatchlist(String signed_request, Long seriesID,
			AsyncCallback<Boolean> callback);
	
	void removeFromUserWatchlist(String signed_request, Long seriesID,
			AsyncCallback<Boolean> callback);
	
	void getUserOldWatchlist(String signed_request,
			AsyncCallback<List<SeriesDTO>> callback);
	
	void getUserWatchlist(Long user_id, AsyncCallback<List<SeriesDTO>> callback);
	
	void getUserOldWatchlist(Long userID,
			AsyncCallback<List<SeriesDTO>> callback);
	
}
