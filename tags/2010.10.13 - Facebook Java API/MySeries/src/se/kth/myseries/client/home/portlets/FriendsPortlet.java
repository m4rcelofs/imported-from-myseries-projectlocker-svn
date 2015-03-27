package se.kth.myseries.client.home.portlets;

import se.kth.myseries.client.profiles.IProfilesServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  07/10/2010
 */
public class FriendsPortlet extends APortlet {
	
	private final IProfilesServiceAsync	profilesService;
	
	public FriendsPortlet(final IProfilesServiceAsync profilesService) {
		super();
		this.profilesService = profilesService;
		this.buildContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#buildContent()
	 */
	@Override
	public void buildContent() {
		
		try {
			profilesService.getCurrentUser(new AsyncCallback<String>() {
				
				private Label getLabel() {
					Label label = new Label();
					label.setAlign(Alignment.CENTER);
					label.setLayoutAlign(VerticalAlignment.CENTER);
					FriendsPortlet.this.addItem(label);
					return label;
				}
				
				@Override
				public void onSuccess(final String result) {
					getLabel().setContents(result);
				}
				
				@Override
				public void onFailure(final Throwable caught) {
					getLabel().setContents(caught.getMessage());
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Friend's Activities");
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL,
				buildSettingsControl() };
		
		return h;
	}
	
	@Override
	public HeaderControl buildSettingsControl() {
		// TODO
		return super.buildSettingsControl();
	}
}
