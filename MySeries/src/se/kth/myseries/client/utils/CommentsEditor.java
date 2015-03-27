/**
 * 
 */
package se.kth.myseries.client.utils;

import java.util.Date;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.series.ISeriesServiceAsync;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseMoveEvent;
import com.smartgwt.client.widgets.events.MouseMoveHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries
 * 
 * @author Xu Liu
 * @since Nov 25, 2010
 */
public class CommentsEditor extends VLayout {
	private RichTextEditor		commentsEditor	= new RichTextEditor();
	private IButton				button			= new IButton("Add");
	private ISeriesServiceAsync	seriesService;
	private long				seriesID;
	private long				seasonID;
	private long				episodeID;
	private String				userName		= "";
	
	public CommentsEditor(ISeriesServiceAsync seriesService, long seriesID,
			String userName) {
		super();
		this.setMembersMargin(10);
		this.seriesService = seriesService;
		this.seriesID = seriesID;
		this.userName = userName;
		build();
		buildSerieCommentEditor();
		this.addMember(commentsEditor);
		this.addMember(button);
	}
	
	public CommentsEditor(ISeriesServiceAsync seriesService, long seriesID,
			long seasonID, long episodeID, String userName) {
		super();
		this.setMembersMargin(10);
		this.seriesService = seriesService;
		this.seriesID = seriesID;
		this.seasonID = seasonID;
		this.episodeID = episodeID;
		this.userName = userName;
		build();
		buildEpisodeCommentEditor();
		this.addMember(commentsEditor);
		this.addMember(button);
	}
	
	private void build() {
		commentsEditor.setHeight(150);
		commentsEditor.setOverflow(Overflow.HIDDEN);
		commentsEditor.setCanDragResize(true);
		commentsEditor.setShowEdges(true);
		commentsEditor.setControlGroups();
		commentsEditor.setValue("Please add your comment here");
		
		commentsEditor.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				commentsEditor.setValue("");
			}
			
		});
		button.setWidth(100);
		button.setHeight(20);
	}
	
	private void buildSerieCommentEditor() {
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (commentsEditor.getValue().length() > 5
						&& commentsEditor.getValue().length() < 500) {
					Date now = new Date();
					DateTimeFormat dtf = DateTimeFormat
							.getFormat("yyyy-MM-dd HH:mm");
					Label comment = new Label(commentsEditor.getValue()
							+ "<br><p align=\"right\">By: " + userName
							+ "<br>@" + dtf.format(now) + "</p>");
					comment.setHeight(100);
					comment.setPadding(5);
					comment.setBorder("3px solid #c0c0c0");
					addMember(comment);
					addComments(commentsEditor.getValue());
					commentsEditor.setValue("");
				} else
					SC.say("Comments not long enough!");
			}
		});
		
	}
	
	private void buildEpisodeCommentEditor() {
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (commentsEditor.getValue().length() > 5
						&& commentsEditor.getValue().length() < 500) {
					Date now = new Date();
					DateTimeFormat dtf = DateTimeFormat
							.getFormat("yyyy-MM-dd HH:mm");
					Label comment = new Label(commentsEditor.getValue()
							+ "<br><p align=\"right\">By: " + userName
							+ "<br>@" + dtf.format(now) + "</p>");
					comment.setHeight(100);
					comment.setPadding(10);
					comment.setBorder("3px solid #c0c0c0");
					addMember(comment);
					addComments(commentsEditor.getValue(), seasonID, episodeID);
					commentsEditor.setValue("");
				} else
					SC.say("Comments not long enough!");
			}
		});
		
	}
	
	private void addComments(String comment) {
		seriesService.addComment(MySeriesEP.getSignedRequest(), seriesID,
				comment, new AsyncCallback<Void>() {
					
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
					
					@Override
					public void onSuccess(Void result) {

					}
					
				});
		
	}
	
	private void addComments(String comment, long seasonID, long episodeID) {
		seriesService.addComment(MySeriesEP.getSignedRequest(), seriesID,
				seasonID, episodeID, comment, new AsyncCallback<Void>() {
					
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
					
					@Override
					public void onSuccess(Void result) {

					}
					
				});
		
	}
	
}
