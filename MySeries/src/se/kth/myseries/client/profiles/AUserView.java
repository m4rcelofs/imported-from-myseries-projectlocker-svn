package se.kth.myseries.client.profiles;

import se.kth.myseries.client.profiles.components.ListWindow;
import se.kth.myseries.client.profiles.components.StatisticsArea;
import se.kth.myseries.client.profiles.components.UserInfoArea;
import se.kth.myseries.client.profiles.ds.FriendsDataSource;
import se.kth.myseries.client.profiles.ds.OldSeriesDataSource;
import se.kth.myseries.client.profiles.ds.SeriesDataSource;
import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.utils.navigation.Page;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.StatisticsDTO;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public abstract class AUserView extends VLayout implements Page {
	
	protected UserInfoArea				userInfo;
	protected StatisticsArea			statistics;
	protected ListWindow				watchlist;
	protected ListWindow				oldWatchlist;
	protected ListWindow				friends;
	
	protected IProfilesServiceAsync		profilesService;
	protected IWatchlistServiceAsync	watchlistService;
	
	protected ANavigableTab				profilesTab;
	
	protected UserDTO					dto;
	
	protected AUserView(final UserDTO dto, final ANavigableTab profilesTab,
			final IProfilesServiceAsync profilesService,
			final IWatchlistServiceAsync watchlistService) {
		
		this.dto = dto;
		this.profilesTab = profilesTab;
		this.profilesService = profilesService;
		this.watchlistService = watchlistService;
		
		this.userInfo = new UserInfoArea();
		this.statistics = new StatisticsArea();
		
		this.watchlist = new ListWindow("Series followed", 227, 42);
		this.oldWatchlist = new ListWindow("Series no longer followed", 227, 42);
		this.configureWatchlists();
		
		this.friends = new ListWindow("Friends that also use MySeries", 50, 50);
		this.configureFriends();
	}
	
	@Override
	public Canvas getCanvas() {
		return this;
	}
	
	protected void configureWatchlists() {
		RecordClickHandler handler = new RecordClickHandler() {
			@Override
			public void onRecordClick(final RecordClickEvent event) {
				
				// TODO something needs to happen in here?
			}
		};
		
		watchlist.addRecordClickHandler(handler);
		oldWatchlist.addRecordClickHandler(handler);
	}
	
	protected void configureFriends() {
		friends.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(final RecordClickEvent event) {
				Record friend = event.getRecord();
				String friendUID = friend.getAttribute("facebook_id");
				
				profilesService.getUserInfoByFacebookID(friendUID,
						new AsyncCallback<UserDTO>() {
					
					@Override
					public void onFailure(final Throwable caught) {
						userInfo.setError(caught);
						watchlist.setError(caught);
						oldWatchlist.setError(caught);
						friends.setError(caught);
					}
					
					@Override
					public void onSuccess(final UserDTO result) {
						
						ProfilesFriendView friendView = new ProfilesFriendView(
								result, profilesTab, profilesService,
								watchlistService);
						
						profilesTab.addPage(friendView);
					}
				});
			}
		});
	}
	
	protected void buildUser() {
		userInfo.setUserInfo(dto);
		
		watchlist.setDataSource(new SeriesDataSource(watchlistService, dto.id));
		oldWatchlist.setDataSource(new OldSeriesDataSource(watchlistService,
				dto.id));
		friends.setDataSource(new FriendsDataSource(profilesService,
				dto.facebook_id));
	}
	
	protected void buildStatistics() {
		profilesService.getStatisticsByFacebookID(dto.facebook_id,
				new AsyncCallback<StatisticsDTO>() {
			@Override
			public void onFailure(final Throwable caught) {
				statistics.setError(caught);
			}
			
			@Override
			public void onSuccess(final StatisticsDTO stats) {
				statistics.setStatistics(stats);
			}
		});
	}
}
