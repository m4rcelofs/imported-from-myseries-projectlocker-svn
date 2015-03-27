package se.kth.myseries.client.profiles.ds;

import java.util.List;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;

/**
 * MySeries <br />
 * 
 * @author Léna
 * @since 15 nov. 2010
 */
public class SeriesDataSource extends ASeriesDataSource {
	
	// for the current user
	public SeriesDataSource(final IWatchlistServiceAsync watch) {
		this(watch, null);
	}
	
	// for the friends
	public SeriesDataSource(final IWatchlistServiceAsync watch,
			final Long userID) {
		super(watch, userID);
	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		AsyncCallback<List<SeriesDTO>> callback = super.getCallback(requestId,
				request, response);
		
		if (userID == null)
			this.watchlistService.getUserWatchlist(
					MySeriesEP.getSignedRequest(), callback);
		else
			this.watchlistService.getUserWatchlist(userID, callback);
		
	}

}
