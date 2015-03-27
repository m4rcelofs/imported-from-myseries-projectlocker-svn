/**
 * 
 */
package se.kth.myseries.client.series.episode;

import java.util.ArrayList;
import java.util.Set;

import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.utils.CommentsEditor;
import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.utils.navigation.NavigationBar;
import se.kth.myseries.client.utils.navigation.Page;
import se.kth.myseries.shared.dto.CommentsDTO;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries
 * 
 * @author Xu Liu
 * @since Nov 11, 2010
 */
public class EpisodeView extends VLayout implements Page {
	private HLayout				topLayout				= new HLayout();
	// private final HLayout middleLayout = new HLayout();
	
	private VLayout				topLeftLayout			= new VLayout();
	private HLayout				topLeftTopLayout		= new HLayout();
	private VLayout				topLeftTopRightLayout	= new VLayout();
	// private final VLayout middleRightLayout = new VLayout();
	
	// private final Window comments = new Window();
	// private final Window commentsEdit = new Window();
	
	// private final Window tags = new Window();
	// private final Window topEpisodes = new Window();
	
	private Label				episodeName;
	// private final Img image ;
	// private final Label rank = new Label("Rank");
	private Label				description;
	private Label				airDate;
	private Label				stats;
	private Label				watchers;
	NavigationBar				bar;
	private ANavigableTab		seriesTab;
	private EpisodeDTO			episode;
	
	private ISeriesServiceAsync	seriesService;
	private SeriesDTO			serie;
	private long				seasonID;
	private CommentsEditor		commentsEditor;
	
	public EpisodeView(final EpisodeDTO episode, final SeriesDTO serie,
			final long seasonID, final ANavigableTab seriesTab,
			final ISeriesServiceAsync seriesService) {
		super();
		this.seriesService = seriesService;
		this.serie = serie;
		this.seasonID = seasonID;
		this.seriesTab = seriesTab;
		this.episode = episode;
		this.episodeName = new Label(episode.episodeName);
		commentsEditor = new CommentsEditor(seriesService, serie.id, seasonID,
				this.episode.id, "Test User Name");
		this.build();
		
	}
	
	private void getData() {
		try {
			seriesService.getEpisode(serie.id, seasonID, episode.id,
					new AsyncCallback<EpisodeDTO>() {
						
						@Override
						public void onFailure(final Throwable caught) {
							caught.printStackTrace();
							SC.say("getEpisode failed");
						}
						
						@Override
						public void onSuccess(final EpisodeDTO result) {
							if (result != null) {
								description.setContents(result.description);
								airDate.setContents("Date of air: "
										+ result.firstAired);
								
								String directors = "", writers = "", guestStars = "";
								if (result.directors != null)
									for (int i = 0; i < result.directors.size(); i++)
										directors += result.directors.get(i);
								if (result.writers != null)
									for (int i = 0; i < result.writers.size(); i++)
										writers += result.writers.get(i);
								if (result.guestStars != null)
									for (int i = 0; i < result.guestStars
											.size(); i++)
										guestStars += result.guestStars.get(i);
								int nbrOfWatchers = 0;
								if (result.nbrOfWatchers != null)
									nbrOfWatchers = result.nbrOfWatchers;
								stats.setContents("Statistics:<br>This is No."
										+ result.episodeNbr + " episode of "
										+ result.seriesName + " Season "
										+ result.season + "<br>Director: "
										+ directors + "<br>Writers: " + writers
										+ "<br>Guest Stars: " + guestStars
										+ "<br>Watched by " + nbrOfWatchers
										+ " people");
							}
						}
						
					});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
	
	private void getWatchers() {
		
		seriesService.getWatchers(serie.id, seasonID, episode.id,
				new AsyncCallback<Set<UserDTO>>() {
					
					@Override
					public void onFailure(final Throwable caught) {
						caught.printStackTrace();
						
					}
					
					@Override
					public void onSuccess(final Set<UserDTO> result) {
						if (result != null) {
							String watcherNames = "Watched by:<br>";
							ArrayList<UserDTO> list = new ArrayList<UserDTO>();
							list.addAll(result);
							for (UserDTO u : list)
								watcherNames = watcherNames + u.firstName + " "
										+ u.lastName + "<br>";
							watchers.setContents(watcherNames);
						}
					}
				});
	}
	
	private void getComments(final int pageNum) {
		this.seriesService.getComments(serie.id, seasonID, episode.id, pageNum,
				new AsyncCallback<CommentsDTO>() {
					
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
					
					@Override
					public void onSuccess(CommentsDTO result) {
						if (result != null
								&& pageNum <= result.totalNumberOfPages) {
							if (result.comments != null) {
								for (int i = 0; i < result.comments.size(); i++) {
									Label comment = new Label(
											result.comments.get(i).text
													+ "<p align=\"right\">By: "
													+ result.comments.get(i).user.firstName
													+ result.comments.get(i).user.lastName
													+ "<br>@"
													+ result.comments.get(i).date
													+ "</p>");
									comment.setHeight(100);
									comment.setPadding(10);
									comment.setBorder("3px solid #c0c0c0");
									commentsEditor.addMember(comment);
								}
								
							}
							
						}
					}
					
				});
	}
	
	private void build() {
		watchers = new Label("");
		description = new Label("");
		airDate = new Label("");
		stats = new Label("");
		getData();
		getWatchers();
		episodeName.setAutoHeight();
		// top layout
		// topLeftTopRightLayout.addMember(episodeName);
		// topLeftTopRightLayout.addMember(rank);
		// topLeftTopRightLayout.addMember(airDate);
		// topLeftTopLayout.addMember(image);
		// topLeftTopLayout.addMember(topLeftTopRightLayout);
		// topLeftLayout.addMember(topLeftTopLayout);
		// topLeftLayout.addMember(description);
		topLayout.setHeight(350);
		
		topLayout.setShowEdges(true);
		topLayout.setMargin(10);
		topLayout.setMembersMargin(10);
		topLeftLayout.addMember(episodeName);
		topLeftLayout.addMember(description);
		topLeftLayout.addMember(airDate);
		topLayout.addMember(topLeftLayout);
		// Window statisticsWin = new Window();
		// statisticsWin.setTitle("Statistics");
		// statisticsWin.setHeight(200);
		// statisticsWin.setWidth(250);
		// statisticsWin.setShowMaximizeButton(false);
		// statisticsWin.setShowMinimizeButton(false);
		// statisticsWin.setShowHeaderIcon(false);
		// statisticsWin.setShowCloseButton(false);
		// topLayout.setAlign(Alignment.CENTER);
		// statisticsWin.setLayoutMargin(5);
		// statisticsWin.setLayoutAlign(Alignment.CENTER);
		// statisticsWin.setLayoutAlign(VerticalAlignment.CENTER);
		// topLayout.setAlign(VerticalAlignment.CENTER);
		// stats.setAutoHeight();
		// statisticsWin.addMember(stats);
		// topLayout.addMember(statisticsWin);
		stats.setWidth(250);
		stats.setBorder("2px solid #c0c0c0");
		stats.setBackgroundColor("white");
		stats.setShowShadow(true);
		stats.setShadowSoftness(10);
		stats.setShadowOffset(5);
		watchers.setWidth(150);
		watchers.setBorder("2px solid #c0c0c0");
		watchers.setBackgroundColor("white");
		watchers.setShowShadow(true);
		watchers.setShadowSoftness(10);
		watchers.setShadowOffset(5);
		// topLayout.setAlign(VerticalAlignment.CENTER);
		topLayout.addMember(stats);
		topLayout.addMember(watchers);
		getComments(1);
		topLayout.addMember(commentsEditor);
		//
		// middle layout
		// comments.setTitle("comments");
		// middleLayout.addMember(comments);
		// tags.setTitle("tags");
		// topEpisodes.setTitle("top Episodes");
		// middleRightLayout.addMember(tags);
		// middleRightLayout.addMember(topEpisodes);
		// middleLayout.addMember(middleRightLayout);
		
		// bottom: comments edit window
		this.addMember(bar);
		this.addMember(topLayout);
	}
	
	public EpisodeDTO getEpisode() {
		return episode;
	}
	
	@Override
	public String getName() {
		return episode.episodeName;
	}
	
	@Override
	public Canvas getCanvas() {
		return this;
	}
	
	@Override
	public Object getIdentifier() {
		return "_episode_" + episode.id;
	}
}
