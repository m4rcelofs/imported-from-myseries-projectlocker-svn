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
public class OldSeriesDataSource extends ASeriesDataSource {
	
	// for the current user
	public OldSeriesDataSource(final IWatchlistServiceAsync watch) {
		this(watch, null);
	}
	
	// for the friends
	public OldSeriesDataSource(final IWatchlistServiceAsync watch,
			final Long userID) {
		super(watch, userID);
	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		AsyncCallback<List<SeriesDTO>> callback = super.getCallback(requestId,
				request, response);
		
		// if the current user is using the class
		if (userID == null)
			watchlistService.getUserOldWatchlist(MySeriesEP.getSignedRequest(),
					callback);
		else
			watchlistService.getUserOldWatchlist(userID, callback);
	}
	
}
