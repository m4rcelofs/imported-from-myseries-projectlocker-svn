package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.HeaderControls;

/**
 * MySeries <br>
 * A portlet that shows the latest episodes aired from the user's watchlist,
 * allowing him to mark them as watched
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class LatestEpisodesPortlet extends APortlet {
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#buildContent()
	 */
	@Override
	public void buildContent() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Latest Episodes");
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL };
		
		return h;
	}
}
