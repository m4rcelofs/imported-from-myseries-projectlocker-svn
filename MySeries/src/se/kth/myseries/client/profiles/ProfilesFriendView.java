package se.kth.myseries.client.profiles;

import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.UserDTO;

import com.smartgwt.client.widgets.layout.HLayout;

/**
 * MySeries <br>
 * 
 * @author Léna
 * @since 18 oct. 2010
 */
public class ProfilesFriendView extends AUserView {
	
	private final HLayout	topLayout		= new HLayout();
	private final HLayout	middleLayout	= new HLayout();
	private final HLayout	bottomLayout	= new HLayout();
	
	public ProfilesFriendView(final UserDTO dto,
			final ANavigableTab profilesTab,
			final IProfilesServiceAsync profilesService,
			final IWatchlistServiceAsync watchlistService) {
		
		super(dto, profilesTab, profilesService, watchlistService);
		
		build();
	}
	
	public void build() {
		
		buildUser();
		buildStatistics();
		
		topLayout.setMembersMargin(6);
		topLayout.addMember(userInfo);
		topLayout.addMember(statistics);
		
		// Middle part about series
		middleLayout.setMembersMargin(6);
		middleLayout.addMember(watchlist);
		
		// Bottom part
		bottomLayout.setMembersMargin(6);
		bottomLayout.addMember(friends);
		bottomLayout.addMember(oldWatchlist);
		
		this.setMembersMargin(6);
		
		addMember(topLayout);
		addMember(middleLayout);
		addMember(bottomLayout);
		
	}
	
	@Override
	public String getName() {
		return dto.firstName + " " + dto.lastName;
	}
	
	@Override
	public Object getIdentifier() {
		return "_friends_" + dto.id;
	}
}
