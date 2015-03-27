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
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries <br>
 * A portlet with all the series that have episodes airing today
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class AiringTodayPortlet extends APortlet {
	
	private final IWatchlistServiceAsync	watchlistService;
	private VLayout							contents;
	
	public AiringTodayPortlet(final IWatchlistServiceAsync watchlistService) {
		super();
		this.watchlistService = watchlistService;
		this.buildContent();
	}
	
	@Override
	public void buildContent() {
		
		this.contents = new VLayout();
		this.addItem(contents);
		
		final Integer today = Integer.parseInt(DateTimeFormat.getFormat(
		"yyyyMMdd").format(new Date()));
		
		watchlistService.getWatchlist(MySeriesEP.getSignedRequest(), today,
				today, new AsyncCallback<WatchlistEpisodesDTO>() {
			private Label getLabel() {
				Label label = new Label();
				label.setAlign(Alignment.CENTER);
				label.setLayoutAlign(VerticalAlignment.CENTER);
				contents.addMember(label);
				return label;
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				getLabel().setContents(caught.getMessage());
			}
			
			@Override
			public void onSuccess(final WatchlistEpisodesDTO result) {
				
				List<EpisodeDTO> episodesList = result
				.getEpisodes(today);
				StringBuilder episodeNames = new StringBuilder();
				
				if (episodesList != null)
					for (EpisodeDTO dto : episodesList)
						episodeNames.append("<br />").append(
								dto.toReadableString());
				else
					episodeNames.append("No episodes airing today!");
				
				getLabel().setContents(episodeNames.toString());
			}
			
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Airing Today");
		
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL,
				buildSettingsControl() };
		
		return h;
	}
	
	@Override
	public void update() {
		
		this.removeItem(contents);
		this.buildContent();
		
	}
}
