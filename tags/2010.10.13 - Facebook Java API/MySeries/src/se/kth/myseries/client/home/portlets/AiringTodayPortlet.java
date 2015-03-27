package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.HeaderControl;


/**
 * MySeries <br>
 * A portlet with all the series that have episodes airing today
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class AiringTodayPortlet extends APortlet {
	
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
		this.setTitle("Airing Today");
		
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
