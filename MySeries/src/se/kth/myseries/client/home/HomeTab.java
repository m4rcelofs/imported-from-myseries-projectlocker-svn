package se.kth.myseries.client.home;

import se.kth.myseries.client.home.portlets.APortlet;
import se.kth.myseries.client.home.portlets.AiringTodayPortlet;
import se.kth.myseries.client.home.portlets.FriendsPortlet;
import se.kth.myseries.client.home.portlets.HighlightsPortlet;
import se.kth.myseries.client.home.portlets.OtherEpisodesPortlet;
import se.kth.myseries.client.profiles.IProfilesServiceAsync;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.utils.notifications.AObservableTab;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * MySeries <br>
 * Homepage - consists of a Portal with many Portlets that the user can choose
 * 
 * @author Marcelo
 * @since 06/10/2010
 */
public class HomeTab extends AObservableTab {
	
	private final VLayout					vLayout	= new VLayout();
	private final PortalLayout				pLayout	= new PortalLayout(1);
	
	private final ISeriesServiceAsync		seriesService;
	private final IProfilesServiceAsync		profilesService;
	private final IWatchlistServiceAsync	watchlistService;
	
	public HomeTab(final ISeriesServiceAsync seriesService,
			final IProfilesServiceAsync profilesService,
			final IWatchlistServiceAsync watchlistService) {
		super("Home", "house.png");
		
		this.seriesService = seriesService;
		this.profilesService = profilesService;
		this.watchlistService = watchlistService;
		
		{
			vLayout.setMembersMargin(6);
			
			{
				VLayout vLayout2 = new VLayout();
				vLayout2.setMembersMargin(6);
				// vLayout2.addMember(new AdsPortlet());
				{
					HLayout hLayout = new HLayout();
					hLayout.setMembersMargin(6);
					{
						APortlet airingToday = new AiringTodayPortlet(
								watchlistService);
						APortlet friends = new FriendsPortlet(profilesService);
						{
							hLayout.addMember(airingToday);
							hLayout.addMember(friends);
						}
						// receives updates for the watchlist
						this.addObservers(airingToday);
					}
					
					vLayout2.addMember(hLayout);
				}
				vLayout2.setHeight(150);
				vLayout.addMember(vLayout2);
			}
			
		}
		buildPortals();
		
		vLayout.addMember(pLayout);
		
		this.setPane(vLayout);
	}
	
	/**
	 * Builds the Portal with default portlets
	 * //TODO load user's settings
	 */
	private void buildPortals() {
		
		{
			APortlet otherEpisodes = new OtherEpisodesPortlet(watchlistService);
			pLayout.addPortlet(otherEpisodes);
			// receives updates for the watchlist
			this.addObservers(otherEpisodes);
		}

		pLayout.addPortlet(new HighlightsPortlet());
		// pLayout.addPortlet(new MostWatchedPortlet(seriesService));
		// pLayout.addPortlet(new TopSeriesPortlet(seriesService));
		// pLayout.addPortlet(new AdsPortlet());
		// pLayout.addPortlet(new NewsPortlet());
		
		// look at
		// http://forums.smartclient.com/showthread.php?t=3116
		// http://forums.smartclient.com/showthread.php?t=3876
		// for multiple column portlets
		
	}
	
	/**
	 * PortalColumn settings
	 */
	private class PortalColumn extends VStack {
		
		public PortalColumn() {
			
			setMembersMargin(6);
			setAnimateMembers(true);
			setAnimateMemberTime(300);
			setCanAcceptDrop(true);
			setDropLineThickness(4);
			
			Canvas dropLineProperties = new Canvas();
			dropLineProperties.setBackgroundColor("blue");
			setDropLineProperties(dropLineProperties);
			
			setShowDragPlaceHolder(true);
			Canvas placeHolderProperties = new Canvas();
			placeHolderProperties.setBorder("2px solid #8289A6");
			setPlaceHolderProperties(placeHolderProperties);
		}
	}
	
	/**
	 * PortalLayout settings
	 */
	private class PortalLayout extends HLayout {
		public PortalLayout(final int numColumns) {
			setMembersMargin(6);
			for (int i = 0; i < numColumns; i++)
				addMember(new PortalColumn());
		}
		
		/**
		 * Adds a new portlet on the column with the fewest portlets
		 * 
		 * @param portlet
		 *            The portlet to be added
		 * @return The column with the fewest portlets
		 */
		public Layout addPortlet(final Portlet portlet) {
			int fewestPortlets = Integer.MAX_VALUE;
			Layout fewestPortletsColumn = null;
			for (int i = 0; i < getMembers().length; i++) {
				int numPortlets = ((Layout) getMember(i)).getMembers().length;
				if (numPortlets < fewestPortlets) {
					fewestPortlets = numPortlets;
					fewestPortletsColumn = (Layout) getMember(i);
				}
			}
			fewestPortletsColumn.addMember(portlet);
			return fewestPortletsColumn;
		}
	}
	
}
