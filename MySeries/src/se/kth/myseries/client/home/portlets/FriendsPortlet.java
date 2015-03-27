package se.kth.myseries.client.home.portlets;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.profiles.IProfilesServiceAsync;
import se.kth.myseries.client.utils.navigation.Navigation;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
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
		
		// FIXME
		profilesService.getUserInfo(MySeriesEP.getSignedRequest(),
				new AsyncCallback<UserDTO>() {
			
			private Label getLabel() {
				Label label = new Label();
				label.setAlign(Alignment.CENTER);
				label.setLayoutAlign(VerticalAlignment.CENTER);
				
				// FIXME
				label.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(final ClickEvent event) {
						History.newItem(Navigation.FRIEND_PROFILE.historyToken
								+ "~123");
					}
				});
				
				FriendsPortlet.this.addItem(label);
				return label;
			}
			
			@Override
			public void onSuccess(final UserDTO result) {
				getLabel().setContents(
						result.firstName + " " + result.lastName);
			}

			@Override
			public void onFailure(final Throwable caught) {
				getLabel().setContents(caught.getMessage());
			}
		});
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
