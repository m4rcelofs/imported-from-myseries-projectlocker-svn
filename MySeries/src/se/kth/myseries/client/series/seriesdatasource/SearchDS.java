package se.kth.myseries.client.series.seriesdatasource;

import java.util.List;

import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.utils.GwtRpcDataSource;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;
/**
 * MySeries <br>
 * 
 * @author Zhe
 * @since 2010-11-7
 */
public class SearchDS extends GwtRpcDataSource {
	private final ISeriesServiceAsync	seriesService;
	private String seriesName;
	final DataSourceTextField titleField=new DataSourceTextField("name","Name");
	//final DataSourceBooleanField watchedField=new DataSourceBooleanField("watchedByCurrentUser","watchedByCurrentUser");
	final DataSourceImageField pictureField = new DataSourceImageField("pictureURL", "Picture");  
	public SearchDS(String seriesName,final ISeriesServiceAsync seriesService) {
		super();
		this.seriesName=seriesName;
		this.seriesService = seriesService;		
		setFields(titleField,pictureField);
		//System.out.println("success build ");
	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		seriesService.search(this.seriesName,new AsyncCallback<List<SeriesDTO>>() {
			
			@Override
			public void onFailure(final Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
				System.out.println("failure");
			}
			
			@Override
			public void onSuccess(final List<SeriesDTO> result) {
				int size = result.size();
				ListGridRecord[] list = new ListGridRecord[size];
				if (size > 0)
				{
					for (int i = 0; i < result.size(); i++)
					{
						ListGridRecord record = new ListGridRecord();
						copyValues(result.get(i), record);
						list[i] = record;
					}
					response.setData(list);
					response.setTotalRows(result.size());
					processResponse(requestId, response);
					System.out.println("successful");
				}
				else
				{
					ListGridRecord[] recordnull = new ListGridRecord[1];
					ListGridRecord recordnull2 = new ListGridRecord();
					recordnull2.setAttribute("name","No series");
					//recordnull2.setAttribute("watchedByCurrentUser",false);
					recordnull2.setAttribute("pictureURL","nms.jpg");
					recordnull[0]=recordnull2;						
					response.setData(recordnull);
					processResponse(requestId, response);
					System.out.println("successful but null ");
				}
				
			}
			
		});
	}
	private static void copyValues(final SeriesDTO from, final ListGridRecord to) {
		to.setAttribute("id", from.id);		
		//to.setAttribute("watchedByCurrentUser", from.watchedByCurrentUser);
		to.setAttribute("name",from.name);
		to.setAttribute("pictureURL", from.bannerLinks.get(0));
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
