package se.kth.myseries.client.watchlist.components;

import java.util.Date;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.utils.notifications.Observer;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.calendar.CalendarEvent;
import com.smartgwt.client.widgets.calendar.events.DateChangedEvent;
import com.smartgwt.client.widgets.calendar.events.DateChangedHandler;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 22/11/2010
 */
public class SeriesCalendar extends Calendar implements Observer {
	
	private final IWatchlistServiceAsync	watchlistService;
	private final DataSource				ds;
	
	public SeriesCalendar(final IWatchlistServiceAsync watchlistService) {
		this.watchlistService = watchlistService;
		this.ds = new DataSource();
		
		configureDataSource();
		configure();
		fetchData();
	}
	
	private void configure() {
		// layout should control size, but just in case
		// setWidth(710);
		// setHeight(300);
		
		setShowDayView(false);
		setShowWeekView(false);
		setShowDayHeaders(false);
		setShowDatePickerButton(false);
		setShowAddEventButton(false);
		setDisableWeekends(false);
		setShowOtherDays(false);
		setShowDateChooser(false);
		setCanCreateEvents(false);
		setDataSource(ds);
		
		setAutoFetchData(true);
		addDateChangedHandler(new DateChangedHandler() {
			@Override
			public void onDateChanged(final DateChangedEvent event) {
				fetchData();
			}
		});
	}
	
	private void configureDataSource() {
		DataSourceSequenceField id = new DataSourceSequenceField("id");
		DataSourceTextField seriesName = new DataSourceTextField("seriesName");
		DataSourceTextField episodeName = new DataSourceTextField("episodeName");
		DataSourceIntegerField season = new DataSourceIntegerField("season");
		DataSourceIntegerField episodeNbr = new DataSourceIntegerField(
		"episodeNbr");
		DataSourceDateField date = new DataSourceDateField("firstAired");
		
		ds.setFields(id, seriesName, episodeName, season, episodeNbr, date);
		ds.setClientOnly(true);
		
	}
	
	@Override
	public void fetchData() {
		
		String month = DateTimeFormat.getFormat("yyyyMM").format(
				getChosenDate());
		
		Integer initDate = Integer.parseInt(month + "01");
		Integer endDate = Integer.parseInt(month + "31");
		
		watchlistService.getWatchlist(MySeriesEP.getSignedRequest(), initDate,
				endDate, new AsyncCallback<WatchlistEpisodesDTO>() {
			
			@Override
			public void onSuccess(final WatchlistEpisodesDTO result) {
				ds.setTestData(result.toCalendarEvents());
				SeriesCalendar.super.invalidateCache();
				SeriesCalendar.super.fetchData();
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected String getDayBodyHTML(final Date date,
			final CalendarEvent[] events, final Calendar calendar,
			final int rowNum, final int colNum) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(date.getDate());
		
		if (events != null && events.length > 0)
			for (CalendarEvent e : events) {
				builder.append("<br />");
				builder.append(e.getName());
			}
		
		return builder.toString();
		
	}
	
	@Override
	public void update() {
		this.fetchData();
	}
}
