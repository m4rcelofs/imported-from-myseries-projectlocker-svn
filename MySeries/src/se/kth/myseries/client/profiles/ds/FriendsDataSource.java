package se.kth.myseries.client.profiles.ds;

import java.util.List;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.profiles.IProfilesServiceAsync;
import se.kth.myseries.client.utils.GwtRpcDataSource;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 02/11/2010
 */
public class FriendsDataSource extends GwtRpcDataSource {
	
	private final IProfilesServiceAsync	profilesService;
	private final String				facebook_id;
	
	public FriendsDataSource(final IProfilesServiceAsync profilesService) {
		this(profilesService, null);
	}
	
	public FriendsDataSource(final IProfilesServiceAsync profilesService,
			final String facebook_id) {

		super();
		
		this.profilesService = profilesService;
		this.facebook_id = facebook_id;
		
		addField(new DataSourceImageField("pictureURL", "PICTUREURL"));
		addField(new DataSourceTextField("firstName", "FIRSTNAME"));
		addField(new DataSourceTextField("lastName", "LASTNAME"));

	}
	
	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {
		
		AsyncCallback<List<UserDTO>> callback = super.getFETCHCallback(
				UserDTO.class, requestId, request, response);
		
		if (facebook_id == null)
			profilesService.getFriendsThatUseTheApp(
					MySeriesEP.getSignedRequest(), callback);
		else
			profilesService.getFriendsThatUseTheApp(
					MySeriesEP.getSignedRequest(), facebook_id, callback);
	}
	
	@Override
	protected void copyValues(final Object f, final ListGridRecord to) {
		
		UserDTO from = (UserDTO) f;
		
		to.setAttribute("id", from.id);
		to.setAttribute("facebook_id", from.facebook_id);
		to.setAttribute("firstName", from.firstName);
		to.setAttribute("lastName", from.lastName);
		to.setAttribute("pictureURL", from.pictureURL);
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
