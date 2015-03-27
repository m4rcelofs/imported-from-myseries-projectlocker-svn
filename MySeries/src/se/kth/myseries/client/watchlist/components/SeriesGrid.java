package se.kth.myseries.client.watchlist.components;

import com.smartgwt.client.widgets.grid.ListGrid;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 24/11/2010
 */
public class SeriesGrid extends ListGrid {
	
	public SeriesGrid() {
		configure();
	}
	
	private void configure() {
		
		setWidth(325);
		setHeight(300);
		setCellHeight(24);
		// setShowEdges(true);
		// setBodyStyleName("normal");
		setShowHeader(false);
		setLeaveScrollbarGap(true);
		setEmptyMessage("Drag &amp; drop series here");
		
		setCanDragRecordsOut(true);
		setCanAcceptDroppedRecords(true);
		
		setCanReorderRecords(false);
		
		// TODO show the thumbnail of the series when dragging?
		// setTrackerImage(new ImgProperties("pieces/24/cubes_all.png", 24,
		// 24));
	}
	
}
