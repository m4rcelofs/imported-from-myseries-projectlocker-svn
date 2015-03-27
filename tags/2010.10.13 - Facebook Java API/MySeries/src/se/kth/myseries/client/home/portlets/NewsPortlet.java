package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.HeaderControl;

/**
 * MySeries <br>
 * A portlet for series' news
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
// TODO possible sources
// http://www.tvfanatic.com/
// http://www.tvrage.com/
public class NewsPortlet extends APortlet {
	
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
		this.setTitle("News");
		
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL,
				buildSettingsControl() };
		
		return h;
	}
	
	@Override
	public HeaderControl buildSettingsControl() {
		// TODO
		return super.buildSettingsControl();
	}
	
}
