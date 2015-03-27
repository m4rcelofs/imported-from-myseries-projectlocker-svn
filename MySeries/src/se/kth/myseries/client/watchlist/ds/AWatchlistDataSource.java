package se.kth.myseries.client.watchlist.ds;

import se.kth.myseries.client.utils.GwtRpcDataSource;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 24/11/2010
 */
public abstract class AWatchlistDataSource extends GwtRpcDataSource {
	
	public AWatchlistDataSource() {
		
		DataSourceTextField idField = new DataSourceTextField("id");
		idField.setPrimaryKey(true);
		idField.setHidden(true);
		
		DataSourceTextField nameField = new DataSourceTextField("name");
		
		setFields(idField, nameField);

	}
	
	@Override
	protected void copyValues(final Object from, final ListGridRecord to) {
		SeriesDTO dto = (SeriesDTO) from;
		
		to.setAttribute("id", dto.id);
		to.setAttribute("name", dto.name);
		
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
