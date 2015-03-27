package se.kth.myseries.client.watchlist.ds;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 24/11/2010
 */
public class TopSeriesDataSource extends AWatchlistDataSource {
	
	private final ISeriesServiceAsync	seriesService;
	
	public TopSeriesDataSource(final ISeriesServiceAsync seriesService) {
		super();
		this.seriesService = seriesService;
	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		seriesService.getTopSeriesNames(MySeriesEP.getSignedRequest(),
				super.getFETCHCallback(SeriesDTO.class, requestId, request,
						response));
		
	}
	
	/**
	 * Just continues without contacting the server
	 */
	@Override
	protected void executeAdd(final String requestId, final DSRequest request,
			final DSResponse response) {
		
		final ListGridRecord record = new ListGridRecord(request.getData());
		
		response.setData(new ListGridRecord[] { record });
		processResponse(requestId, response);
		
	}
	
	/**
	 * Just continues without contacting the server
	 */
	@Override
	protected void executeRemove(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		final ListGridRecord record = new ListGridRecord(request.getData());
		
		response.setData(new ListGridRecord[] { record });
		processResponse(requestId, response);
		
	}
}
