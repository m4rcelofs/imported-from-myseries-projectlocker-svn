package se.kth.myseries.client.home.portlets;

import java.util.Date;
import java.util.List;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.watchlist.IWatchlistServiceAsync;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.WatchlistEpisodesDTO;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * MySeries <br>
 * A portlet that shows the latest episodes aired from the user's watchlist,
 * allowing him to mark them as watched
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class OtherEpisodesPortlet extends APortlet {
	
	private final IWatchlistServiceAsync	watchlistService;
	
	private HLayout							hLayout;
	
	public OtherEpisodesPortlet(final IWatchlistServiceAsync watchlistService) {
		super();
		this.watchlistService = watchlistService;
		this.buildContent();
	}
	
	@Override
	public void buildContent() {
		
		hLayout = new HLayout();
		
		{
			hLayout.setHeight(100);
			hLayout.setWidth(724);
			hLayout.setMembersMargin(2);
			hLayout.setAlign(Alignment.CENTER);
			hLayout.setLayoutAlign(VerticalAlignment.CENTER);
		}
		this.addItem(hLayout);
		
		final Date today = new Date();
		// only because GWT has no support for Calendar...
		// and date handling on java s***
		long oneDay = 1000 * 60 * 60 * 24;
		
		final Integer yesterdayInt = format(new Date(today.getTime() - oneDay));
		final Integer tomorrowInt = format(new Date(today.getTime() + oneDay));
		
		watchlistService.getWatchlist(MySeriesEP.getSignedRequest(),
				yesterdayInt, tomorrowInt,
				new AsyncCallback<WatchlistEpisodesDTO>() {
			
			private Label getLabel() {
				Label label = new Label();
				label.setAlign(Alignment.CENTER);
				label.setLayoutAlign(VerticalAlignment.CENTER);
				label.setWidth(362);
				hLayout.addMember(label);
				
				return label;
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				getLabel().setContents(caught.getMessage());
			}
			
			@Override
			public void onSuccess(final WatchlistEpisodesDTO result) {
				
				if (result.hasEpisodesFor(yesterdayInt))
					getLabel().setContents(
							getEpisodeNames("Yesterday:",
									result.getEpisodes(yesterdayInt)));
				else
					getLabel().setContents(
					"No episodes aired yesterday!");
				
				if (result.hasEpisodesFor(tomorrowInt))
					getLabel().setContents(
							getEpisodeNames("Tomorrow:",
									result.getEpisodes(tomorrowInt)));
				else
					getLabel().setContents(
					"No episodes will air tomorrow!");
				
			}
			
		});
		
	}
	
	private Integer format(final Date date) {
		return Integer.parseInt(DateTimeFormat.getFormat("yyyyMMdd").format(
				date));
	}
	
	private String getEpisodeNames(final String name,
			final List<EpisodeDTO> episodes) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(name);
		if (episodes != null)
			for (EpisodeDTO dto : episodes)
				builder.append("<br />").append(dto.toReadableString());
		
		return builder.toString();
	}
	
	@Override
	public void setTitle() {
		this.setTitle("Other Episodes");
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL };
		
		return h;
	}
	
	@Override
	public void update() {
		this.removeItem(hLayout);
		this.buildContent();
	}
}
