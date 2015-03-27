package se.kth.myseries.client;

import se.kth.myseries.client.auth.FacebookAuthorizer;
import se.kth.myseries.client.auth.IFacebookAuthenticationService;
import se.kth.myseries.client.auth.IFacebookAuthenticationServiceAsync;
import se.kth.myseries.client.home.HomeTab;
import se.kth.myseries.client.profiles.IProfilesService;
import se.kth.myseries.client.profiles.IProfilesServiceAsync;
import se.kth.myseries.client.profiles.ProfilesTab;
import se.kth.myseries.client.series.ISeriesService;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.series.SeriesTab;
import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.utils.navigation.HistoryHandler;
import se.kth.myseries.client.utils.navigation.Navigation;
import se.kth.myseries.client.utils.notifications.AObservableTab;
import se.kth.myseries.client.watchlist.IWatchlistService;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.client.watchlist.WatchlistTab;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * MySeries <br>
 * Entry point for the application
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class MySeriesEP implements EntryPoint {
	
	private final IFacebookAuthenticationServiceAsync	authService			= GWT.create(IFacebookAuthenticationService.class);
	private final ISeriesServiceAsync					seriesService		= GWT.create(ISeriesService.class);
	private final IProfilesServiceAsync					profilesService		= GWT.create(IProfilesService.class);
	private final IWatchlistServiceAsync				watchlistService	= GWT.create(IWatchlistService.class);
	
	private static final TabSet							topTabSet			= new TabSet();										;
	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		new FacebookAuthorizer(authService).authorize();
		
		configureComponents();
		
		RootPanel.get().add(getViewPanel());
		
		HistoryHandler listener = new HistoryHandler();
		History.addValueChangeHandler(listener);
		topTabSet.addTabSelectedHandler(listener);
		
		// initial token
		if (History.getToken().length() == 0)
			History.newItem(Navigation.HOME.historyToken, false);
		else
			// some token already exists, process it
			History.fireCurrentHistoryState();
		
	}
	
	private void configureComponents() {
		Label selectable = new Label();
		selectable.setCanSelectText(true);
		Label.setDefaultProperties(selectable);
	}
	
	private Canvas getViewPanel() {
		VLayout layout = new VLayout();
		{
			layout.setWidth(750); // hard limit by Facebook
			layout.setHeight(1250);
			layout.setMembersMargin(15);
		}
		
		Img logo = new Img("logovs4.png");
		layout.addMember(logo);
		
		{
			topTabSet.setTabBarPosition(Side.TOP);
			AObservableTab homeTab = new HomeTab(seriesService,
					profilesService, watchlistService);
			AObservableTab watchlistTab = new WatchlistTab(watchlistService,
					seriesService);
			AObservableTab profilesTab = new ProfilesTab(profilesService,
					watchlistService);
			AObservableTab seriesTab = new SeriesTab(seriesService);
			{
				topTabSet.addTab(homeTab);
				topTabSet.addTab(watchlistTab);
				topTabSet.addTab(profilesTab);
				topTabSet.addTab(seriesTab);
			}
			{
				watchlistTab.addObservers(homeTab, profilesTab);
			}
		}
		
		layout.addMember(topTabSet);
		
		return layout;
	}
	
	public static String getSignedRequest() {
		return Location.getParameter("signed_request");
	}
	
	public static void navigate(final Navigation destination, final String param) {
		// TODO
		topTabSet.selectTab(destination.tabNumber);
		
		if (destination.isNavigable && param != null) {
			ANavigableTab tab = (ANavigableTab) topTabSet
			.getTab(destination.tabNumber);
			// tab.goToPage or tab.addPage
			;
		}
	}
}
