package se.kth.myseries.client;

import se.kth.myseries.client.home.HomeTab;
import se.kth.myseries.client.profiles.IProfilesService;
import se.kth.myseries.client.profiles.IProfilesServiceAsync;
import se.kth.myseries.client.profiles.ProfilesTab;
import se.kth.myseries.client.series.ISeriesService;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.series.SeriesTab;
import se.kth.myseries.client.watchlist.WatchlistTab;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
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
	
	private final ISeriesServiceAsync	seriesService	= GWT.create(ISeriesService.class);
	private final IProfilesServiceAsync	profilesService	= GWT.create(IProfilesService.class);
	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		RootPanel.get().add(getViewPanel());
		
	}
	
	private Canvas getViewPanel() {
		VLayout layout = new VLayout();
		{
			layout.setWidth(750); // hard limit by Facebook
			layout.setHeight(1250);
			layout.setMembersMargin(15);
		}
		
		Img logo = new Img("myseries1.png");
		layout.addMember(logo);
		
		final TabSet topTabSet = new TabSet();
		{
			topTabSet.setTabBarPosition(Side.TOP);
			
			topTabSet.addTab(new HomeTab(seriesService, profilesService));
			topTabSet.addTab(new WatchlistTab());
			topTabSet.addTab(new ProfilesTab());
			topTabSet.addTab(new SeriesTab());
			
		}
		
		layout.addMember(topTabSet);
		
		return layout;
	}
}
