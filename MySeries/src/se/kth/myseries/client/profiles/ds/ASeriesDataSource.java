package se.kth.myseries.client.profiles.ds;

import java.util.List;

import se.kth.myseries.client.utils.GwtRpcDataSource;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * MySeries <br>
 * Abstract class for Series DataSources
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public abstract class ASeriesDataSource extends GwtRpcDataSource {
	
	protected final IWatchlistServiceAsync	watchlistService;
	protected final Long					userID;
	
	public ASeriesDataSource(final IWatchlistServiceAsync watchlistService,
			final Long userID) {
		
		this.watchlistService = watchlistService;
		this.userID = userID;
		
		DataSourceIntegerField id = new DataSourceIntegerField("id", "ID");
		{
			id.setPrimaryKey(true);
			id.setHidden(true);
		}
		DataSourceIntegerField tvdbid = new DataSourceIntegerField("tvDbId",
		"TVDBID");
		tvdbid.setHidden(true);
		
		addField(id);
		addField(tvdbid);
		addField(new DataSourceImageField("pictureURL", "PICTUREURL"));
		addField(new DataSourceTextField("name", "NAME"));
		
	}
	
	protected AsyncCallback<List<SeriesDTO>> getCallback(
			final String requestId, final DSRequest request,
			final DSResponse response) {
		
		return super.getFETCHCallback(SeriesDTO.class, requestId, request,
				response);
		
	}
	
	@Override
	protected void copyValues(final Object f, final ListGridRecord to) {
		
		SeriesDTO from = (SeriesDTO) f;
		
		to.setAttribute("name", from.name);
		to.setAttribute("id", from.id);
		to.setAttribute("tvDbId", from.tvDbId);
		if (from.bannerLinks != null && from.bannerLinks.size() > 0)
			to.setAttribute(
					"pictureURL",
					from.bannerLinks.get(Random.nextInt(from.bannerLinks.size()))
					+ "&thumb=1");
		else
			to.setAttribute("pictureURL", "logovs4_small.png");
	}
	
	@Override
	protected void executeAdd(final String requestId, final DSRequest request,
			final DSResponse response) {}
	
	@Override
	protected void executeUpdate(final String requestId,
			final DSRequest request, final DSResponse response) {}
	
	@Override
	protected void executeRemove(final String requestId,
			final DSRequest request, final DSResponse response) {}
}
