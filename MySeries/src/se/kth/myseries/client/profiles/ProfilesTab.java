package se.kth.myseries.client.profiles;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class ProfilesTab extends ANavigableTab {
	
	private final IProfilesServiceAsync		profilesService;
	private final IWatchlistServiceAsync	watchlistService;
	
	public ProfilesTab(final IProfilesServiceAsync profilesService,
			final IWatchlistServiceAsync watchlistService) {
		super("Profiles", "group.png");
		
		this.profilesService = profilesService;
		this.watchlistService = watchlistService;
		
		this.build();
	}
	
	private void build() {
		
		profilesService.getUserInfo(MySeriesEP.getSignedRequest(),
				new AsyncCallback<UserDTO>() {
			
			@Override
			public void onSuccess(final UserDTO result) {
				ProfilesPersonalView userProfile = new ProfilesPersonalView(
						result, ProfilesTab.this, profilesService,
						watchlistService);
				
				addObservers(userProfile);
				
				addPage(userProfile);
				addNavBarSeparator();
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				// TODO warn the user that his info could not be fetched
			}
		});
		
	}
}
