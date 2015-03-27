package se.kth.myseries.client.profiles;

import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.utils.notifications.Observer;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.UserDTO;

import com.smartgwt.client.widgets.layout.HLayout;

/**
 * MySeries <br>
 * 
 * @author Léna
 * @since 18 oct. 2010
 */
public class ProfilesPersonalView extends AUserView implements Observer {
	
	private final HLayout	topLayout		= new HLayout();
	private final HLayout	bottomLayout	= new HLayout();
	
	public ProfilesPersonalView(final UserDTO dto,
			final ANavigableTab profilesTab,
			final IProfilesServiceAsync profilesService,
			final IWatchlistServiceAsync watchlistService) {
		
		super(dto, profilesTab, profilesService, watchlistService);
		
		build();
	}
	
	public void build() {
		
		this.buildUser();
		this.buildStatistics();
		
		topLayout.setMembersMargin(6);
		topLayout.addMember(userInfo);
		topLayout.addMember(statistics);
		
		// Bottom part
		bottomLayout.setMembersMargin(6);
		bottomLayout.addMember(friends);
		bottomLayout.addMember(oldWatchlist);
		
		this.setMembersMargin(6);
		
		addMember(topLayout);
		addMember(watchlist);
		addMember(bottomLayout);
		
	}
	
	@Override
	public String getName() {
		return "Me";
	}
	
	@Override
	public Object getIdentifier() {
		return "_me_" + dto.id;
	}
	
	@Override
	public void update() {
		buildStatistics();
		watchlist.fetchData();
	}
}
