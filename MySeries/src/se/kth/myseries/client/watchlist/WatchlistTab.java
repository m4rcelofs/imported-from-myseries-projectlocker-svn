package se.kth.myseries.client.watchlist;

import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.utils.notifications.AObservableTab;
import se.kth.myseries.client.watchlist.components.SeriesCalendar;
import se.kth.myseries.client.watchlist.components.WatchlistChooser;

import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries <br>
 * A tab with the full calendar for the user. <br>
 * Shows all episodes from the user's list that aired/will air in XX weeks. <br>
 * 
 * @author Xu Liu
 * @author Marcelo
 * @since 07/10/2010
 */
public class WatchlistTab extends AObservableTab {
	
	private final IWatchlistServiceAsync	watchlistService;
	private final ISeriesServiceAsync		seriesService;
	
	public WatchlistTab(final IWatchlistServiceAsync watchlistService,
			final ISeriesServiceAsync seriesService) {
		
		super("Watchlist", "calendar.png");
		
		this.watchlistService = watchlistService;
		this.seriesService = seriesService;
		build();
	}
	
	/**
	 * Builds the user interface
	 */
	private void build() {
		
		// mainLayout: main framework
		VLayout mainLayout = new VLayout();
		
		// set mainLayout
		// mainLayout.setWidth(740);
		// mainLayout.setHeight(760);
		// mainLayout.setShowEdges(true);
		mainLayout.setLayoutMargin(10);
		mainLayout.setMembersMargin(10);
		
		// combination
		SeriesCalendar calendar = new SeriesCalendar(watchlistService);
		WatchlistChooser watchlistChooser = new WatchlistChooser(seriesService,
				watchlistService, this, calendar);
		
		mainLayout.addMember(calendar);
		mainLayout.addMember(watchlistChooser);
		
		this.setPane(mainLayout);
		
	}
}
