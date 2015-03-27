package se.kth.myseries.client.watchlist.ds;

import java.util.ArrayList;
import java.util.List;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.utils.notifications.Observable;
import se.kth.myseries.client.utils.notifications.Observer;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * MySeries
 * 
 * @author Xu Liu
 * @author Marcelo
 * @since Nov 12, 2010
 */
public class UserWatchListDateSource extends AWatchlistDataSource implements
		Observable {
	
	private final IWatchlistServiceAsync	watchlistService;
	
	private final List<Observer>			observers;
	
	public UserWatchListDateSource(final IWatchlistServiceAsync watchlistService) {
		
		super();
		this.watchlistService = watchlistService;
		this.observers = new ArrayList<Observer>();
		
	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		watchlistService.getUserWatchlist(MySeriesEP.getSignedRequest(),
				new AsyncCallback<List<SeriesDTO>>() {
					@Override
					public void onFailure(final Throwable caught) {
						System.out.println(caught.toString());
						// response.setStatus(RPCResponse.STATUS_FAILURE);
						// processResponse(requestId, response);
					}
					
					@Override
					public void onSuccess(final List<SeriesDTO> result) {
						ListGridRecord[] list = new ListGridRecord[result
								.size()];
						for (int i = 0; i < list.length; i++) {
							ListGridRecord record = new ListGridRecord();
							copyValues(result.get(i), record);
							list[i] = record;
						}
						
						response.setData(list);
						processResponse(requestId, response);
					}
				});
		
	}
	
	@Override
	protected void executeAdd(final String requestId, final DSRequest request,
			final DSResponse response) {
		
		final ListGridRecord record = new ListGridRecord(request.getData());
		
		Long id = Long.parseLong(record.getAttribute("id"));
		watchlistService.addToUserWatchlist(MySeriesEP.getSignedRequest(), id,
				new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(final Throwable caught) {
						response.setStatus(RPCResponse.STATUS_FAILURE);
						processResponse(requestId, response);
					}
					
					@Override
					public void onSuccess(final Boolean result) {
						response.setData(new ListGridRecord[] { record });
						processResponse(requestId, response);
						notifyObservers();
					}
				});
		
	}
	
	@Override
	protected void executeRemove(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		final ListGridRecord record = new ListGridRecord(request.getData());
		
		Long id = Long.parseLong(record.getAttribute("id"));
		watchlistService.removeFromUserWatchlist(MySeriesEP.getSignedRequest(),
				id, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(final Throwable caught) {
						response.setStatus(RPCResponse.STATUS_FAILURE);
						processResponse(requestId, response);
					}
					
					@Override
					public void onSuccess(final Boolean result) {
						response.setData(new ListGridRecord[] { record });
						processResponse(requestId, response);
						notifyObservers();
					}
				});
	}
	
	@Override
	public void addObservers(final Observer... observers) {
		for (Observer o : observers)
			this.observers.add(o);
	}
	
	@Override
	public void notifyObservers() {
		
		for (Observer o : observers)
			o.update();
		
	}
	
}
