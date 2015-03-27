package se.kth.myseries.client.series.series;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.series.episode.EpisodeView;
import se.kth.myseries.client.utils.CommentsEditor;
import se.kth.myseries.client.utils.Ranker;
import se.kth.myseries.client.utils.navigation.ANavigableTab;
import se.kth.myseries.client.utils.navigation.Page;
import se.kth.myseries.shared.dto.CommentsDTO;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.RankDTO;
import se.kth.myseries.shared.dto.SeasonDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries <br>
 * 
 * @author
 * @since 16/10/2010
 */
public class SeriesView extends VLayout implements Page {
	
	VLayout				topLayout		= new VLayout();
	HLayout				middleLayout	= new HLayout();
	HLayout				bottomLayout	= new HLayout();
	// Window commentsEdit = new Window();
	Window				statsWin		= new Window();
	
	Window				seasonsWin		= new Window();
	// Window topWin= new Window();
	// Window commentsWin= new Window();
	// Window whoElseWin= new Window();
	
	SectionStack		sectionStack	= new SectionStack();
	
	HLayout				leftTopLayout	= new HLayout();
	VLayout				middleTopLayout	= new VLayout();
	
	public Label		nameLabel;
	Img					image;
	// private final Label rank = new Label("Rank");
	Label				description;
	Label				stats;
	Label				watchers;
	Label				averageRankNum	= new Label("Average Ranks: ");
	VLayout				seasons			= new VLayout();
	// VLayout topEpisodes = new VLayout();
	
	// VLayout comments = new VLayout();
	// VLayout whoElse = new VLayout();
	
	ANavigableTab		seriesTab;
	SeriesDTO			series;
	ISeriesServiceAsync	seriesService;
	CommentsEditor		commentsEditor;
	EpisodeDTO			dto;
	Ranker				ranker;
	
	public SeriesView(final ANavigableTab seriesTab, final SeriesDTO series,
			final ISeriesServiceAsync seriesService) {
		super();
		this.seriesService = seriesService;
		this.series = series;
		this.seriesTab = seriesTab;
		commentsEditor = new CommentsEditor(seriesService, series.id,
		"Test User Name");
		this.build();
	}
	
	public void build() {
		
		// Top Layout
		nameLabel = new Label("<b>" + series.name + "</b>");
		nameLabel.setHeight(15);
		image = new Img(series.bannerLinks.get(0));
		// image.setWidth(750);
		// middleTopLayout.addMember(rank);
		description = new Label(series.description);
		description.setBorder("1px solid #c0c0c0");
		description.setBackgroundColor("white");
		description.setShowShadow(true);
		description.setShadowSoftness(10);
		description.setShadowOffset(5);
		description.setAutoHeight();
		
		int nbrOfWatchers = 0;
		if (series.nbrOfWatchers != null)
			nbrOfWatchers = series.nbrOfWatchers;
		stats = new Label("the serie contains " + series.nbrSeasons
				+ " seasons,<br>" + "the serie contains " + series.nbrEpisodes
				+ " episodes," + "<br>" + "the serie is watched by "
				+ nbrOfWatchers + " people");
		
		// middleTopLayout.addMember(description);
		// descriWin.setTitle("description");
		// descriWin.addItem(middleTopLayout);
		statsWin.setTitle("Statistics");
		statsWin.addItem(stats);
		statsWin.setWidth(230);
		
		statsWin.setShowMaximizeButton(false);
		statsWin.setShowMinimizeButton(false);
		statsWin.setShowHeaderIcon(false);
		statsWin.setShowCloseButton(false);
		watchers = new Label();
		getWatchers();
		topLayout.setMargin(10);
		topLayout.setMembersMargin(10);
		
		leftTopLayout.addMember(watchers);
		getRanks("id");
		leftTopLayout.addMember(averageRankNum);
		
		topLayout.addMember(nameLabel);
		topLayout.addMember(image);
		topLayout.addMember(description);
		topLayout.addMember(leftTopLayout);
		topLayout.addMember(middleTopLayout);
		
		// Middle Layout
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth(470);
		sectionStack.setHeight(600);
		
		for (int i = 1; i <= series.nbrSeasons; i++) {
			// call while create interface
			final int nbr = i;
			seriesService.getSeason(series.id, i,
					new AsyncCallback<SeasonDTO>() {
				@Override
				public void onSuccess(final SeasonDTO result) {
					// buildEpisodes(result.episodeNames, nbr,
					// result.id);
					// create a section for each season
					SectionStackSection section = new SectionStackSection(
							"Season " + nbr);
					section.setCanCollapse(true);
					// create a space to store the episode
					VLayout episodes = new VLayout();
					for (int j = 0; j < result.episodeNames.size(); j++) {
						dto = result.episodeNames.get(j);
						HLayout pan = new HLayout();
						pan.setLayoutRightMargin(50);
						IButton episodeBut = new IButton();
						episodeBut.setIcon("eye.png");
						episodeBut.setAutoFit(true);
						String info = "episode " + dto.episodeNbr
						+ ": " + dto.episodeName;
						Label episodeInfo = new Label(info);
						episodeInfo.setWidth(info.length() * 6);
						episodeInfo
						.addStyleName("color: blue; font-size:11px; font-family:Arial, Helvetica, sans-serif;  text-decoration:underline;font-style: italic;");
						episodeBut.setWidth(450);
						episodeBut.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								try {
									EpisodeView episodeView = new EpisodeView(
											dto, series, result.id,
											seriesTab, seriesService);
									seriesTab.addPage(episodeView);
								} catch (Exception e) {
									SC.say("add\n" + e.toString());
								}
							}
						});
						pan.addMember(episodeBut);
						pan.addMember(episodeInfo);
						episodes.addMember(pan);
					}
					section.addItem(episodes);
					section.setExpanded(false);
					sectionStack.addSection(section);
				}
				
				@Override
				public void onFailure(final Throwable caught) {
					caught.printStackTrace();
				}
			});
			
		}
		
		seasonsWin.setTitle("Seasons");
		seasonsWin.setWidth(500);
		
		seasonsWin.addItem(this.sectionStack);
		
		middleLayout.addMember(seasonsWin);
		middleLayout.addMember(statsWin);
		
		this.getComments(1);
		addMember(topLayout);
		addMember(middleLayout);
		addMember(commentsEditor);
	}
	
	private void getRanks(final String facebook_id) {
		this.seriesService.getRanks(MySeriesEP.getSignedRequest(), series.id,
				new AsyncCallback<RankDTO>() {
			
			@Override
			public void onFailure(final Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(final RankDTO result) {
				if (result != null) {
					NumberFormat nf = NumberFormat.getFormat("0.00");
					averageRankNum.setContents("Average Ranks: "
							+ nf.format(result.averageRanking));
					if (result.currentUserRanking == null) {
						// not ranked by this user
						ranker = new Ranker();
						leftTopLayout.addMember(ranker);
						ranker.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								SC.say("this is "
										+ Double.toString(ranker
												.getRankNum()));
								addRank();
								// ranker.setVisible(false);
								// ranker.setDisabled(true);
								// TODO show to user even if he has
								// already ranked, so it can be changed
							}
						});
					}
				}
			}
		});
	}
	
	private void addRank() {
		seriesService.addRank(MySeriesEP.getSignedRequest(), series.id,
				ranker.getRankNum(), new AsyncCallback<Void>() {
			
			@Override
			public void onFailure(final Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(final Void result) {
				// TODO show "ok" img to user
			}
			
		});
	}
	
	private void getWatchers() {
		this.seriesService.getWatchers(series.id,
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
		this.seriesService.getComments(series.id, pageNum,
				new AsyncCallback<CommentsDTO>() {
			
			@Override
			public void onFailure(final Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(final CommentsDTO result) {
				if (result != null
						&& pageNum <= result.totalNumberOfPages)
					if (result.comments != null)
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
			
		});
	}
	
	// FIXME why isnt it called from anywhere?
	private void buildEpisodes(final List<EpisodeDTO> list, final int i,
			final long seasonID) {
		// create a section for each season
		SectionStackSection section = new SectionStackSection("Season " + i);
		section.setCanCollapse(true);
		// create a space to store the episode
		VLayout episodes = new VLayout();
		for (int j = 0; j < list.size(); j++) {
			dto = list.get(j);
			IButton episode = new IButton(dto.episodeNbr + ": "
					+ dto.episodeName);
			episode.setAlign(Alignment.LEFT);
			episode.setWidth(470);
			episode.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					EpisodeView episode = new EpisodeView(dto, series,
							seasonID, seriesTab, seriesService);
					seriesTab.addPage(episode);
				}
			});
			episodes.addMember(episode);
		}
		section.addItem(episodes);
		section.setExpanded(false);
		sectionStack.addSection(section);
		
	}
	
	@Override
	public String getName() {
		return series.name;
	}
	
	@Override
	public Canvas getCanvas() {
		return this;
	}
	
	@Override
	public Object getIdentifier() {
		return "_series_" + series.id;
	}
	
}
