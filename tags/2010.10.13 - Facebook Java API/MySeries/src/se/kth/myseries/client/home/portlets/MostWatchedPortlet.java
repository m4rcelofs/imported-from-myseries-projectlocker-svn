package se.kth.myseries.client.home.portlets;

import se.kth.myseries.client.series.ISeriesServiceAsync;

import com.smartgwt.client.types.HeaderControls;

/**
 * MySeries <br>
 * A portlet that shows the most watched episodes on the last week
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class MostWatchedPortlet extends APortlet {
	
	private final ISeriesServiceAsync	seriesService;
	
	public MostWatchedPortlet(final ISeriesServiceAsync seriesService) {
		super();
		this.seriesService = seriesService;
		this.buildContent();
	}
	
	/* (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#buildContent()
	 */
	@Override
	public void buildContent() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Most Watched Episodes (Last Week)");
		
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL };
		
		return h;
	}
	
}
