package se.kth.myseries.client.watchlist.components;

import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.utils.notifications.Observable;
import se.kth.myseries.client.utils.notifications.Observer;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.client.watchlist.ds.TopSeriesDataSource;
import se.kth.myseries.client.watchlist.ds.UserWatchListDateSource;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 24/11/2010
 */
public class WatchlistChooser extends VLayout {
	
	private final TopSeriesDataSource		topSeriesDataSource;
	private final UserWatchListDateSource	watchlistDataSource;
	
	public WatchlistChooser(final ISeriesServiceAsync seriesService,
			final IWatchlistServiceAsync watchlistService,
			final Observer... observers) {
		
		this.topSeriesDataSource = new TopSeriesDataSource(seriesService);
		this.watchlistDataSource = new UserWatchListDateSource(watchlistService);
		
		watchlistDataSource.addObservers(observers);

		build();
	}
	
	private void build() {
		
		Label tips = new Label(
		"Drag the series from left to right to decide what's shown in the calender ");
		addMember(tips);
		
		{
			HLayout hLayout = new HLayout();
			
			final SeriesGrid topSeries = new SeriesGrid();
			final SeriesGrid watchlist = new SeriesGrid();
			
			{
				topSeries.setDataSource(topSeriesDataSource);
				topSeries.fetchData();
				hLayout.addMember(topSeries);
				
				{
					VStack buttons = new VStack(10);
					{
						buttons.setWidth(32);
						buttons.setHeight(74);
						buttons.setLayoutAlign(Alignment.CENTER);
						
						{
							TransferImgButton toRight = new TransferImgButton(
									TransferImgButton.RIGHT);
							toRight.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									watchlist.transferSelectedData(topSeries);
								}
							});
							buttons.addMember(toRight);
						}
						{
							TransferImgButton toLeft = new TransferImgButton(
									TransferImgButton.LEFT);
							toLeft.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									topSeries.transferSelectedData(watchlist);
								}
							});
							buttons.addMember(toLeft);
						}
					}
					hLayout.addMember(buttons);
				}
				
				watchlist.setDataSource(watchlistDataSource);
				watchlist.fetchData();
				hLayout.addMember(watchlist);
				
			}
			addMember(hLayout);
		}
		
		Label tips2 = new Label(
		"If you can't find the series you want here, use the search!");
		addMember(tips2);
		
	}
	
	public Observable getWatchlist() {
		return this.watchlistDataSource;
	}
}
