package se.kth.myseries.client.series;

import se.kth.myseries.client.series.search.SearchView;
import se.kth.myseries.client.utils.navigation.ANavigableTab;

/**
 * MySeries <br>
 * A tab with a list of series available for the user to "follow". <br>
 * Has a filtering function to filter for free-text (serie's name) or category
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class SeriesTab extends ANavigableTab {
	
	private final ISeriesServiceAsync	seriesService;
	
	public SeriesTab(final ISeriesServiceAsync seriesService) {
		super("Series", "television.png");
		
		this.seriesService = seriesService;
		
		this.build();
	}
	
	private void build() {
		
		SearchView search = new SearchView(this.seriesService, this);
		
		addPage(search);
		addNavBarSeparator();
		
	}
	
}
