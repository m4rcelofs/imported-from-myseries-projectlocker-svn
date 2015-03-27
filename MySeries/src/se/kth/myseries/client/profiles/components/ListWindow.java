package se.kth.myseries.client.profiles.components;

import se.kth.myseries.client.utils.GwtRpcDataSource;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public class ListWindow extends Window {
	
	private final TileGrid	list;
	
	/**
	 * @param title
	 */
	public ListWindow(final String title, final int tileWidth,
			final int tileHeight) {
		
		this.setTitle(title);
		this.list = new TileGrid();
		{
			list.setShowEdges(false);
			list.setTileMargin(10);
			list.setAutoFetchData(true);
			
			list.setTileWidth(tileWidth);
			list.setTileHeight(tileHeight);
			
			this.addItem(list);
		}
		
	}
	
	public void setDataSource(final GwtRpcDataSource ds) {
		this.list.setDataSource(ds);
		list.fetchData();
	}
	
	public void addRecordClickHandler(final RecordClickHandler handler) {
		list.addRecordClickHandler(handler);
	}
	
	public void setTileSize(final Integer width, final Integer height) {
		if (width != null)
			list.setTileWidth(width);
		
		if (height != null)
			list.setTileHeight(height);
	}
	
	/**
	 * @param caught
	 */
	public void setError(final Throwable caught) {
		
		this.list.destroy();
		this.addItem(new Label(caught.toString()));
		
	}
	
	/**
	 * 
	 */
	public void fetchData() {
		this.list.invalidateCache();
		this.list.fetchData();
	}
	
}
