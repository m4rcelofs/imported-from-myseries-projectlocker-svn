package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Portlet;

/**
 * MySeries <br>
 * * Portlet class definition
 * 
 * @author Marcelo
 * @since 06/10/2010
 */
public abstract class APortlet extends Portlet {
	
	/**
	 * Constructor. <b>Do not</b> override!
	 */
	public APortlet() {
		
		setAnimateMinimize(true);
		setDragAppearance(DragAppearance.TARGET);
		setCanDrop(true);
		setHeaderControls(getHeaders());
		setDragOpacity(30);
		
		// these settings enable the portlet to autosize its height only to
		// fit its contents
		// (since width is determined from the containing layout, not the
		// portlet contents)
		setVPolicy(LayoutPolicy.NONE);
		setOverflow(Overflow.VISIBLE);
		
		this.setTitle();

	}
	
	/**
	 * Builds the interface content
	 */
	public abstract void buildContent();
	
	/**
	 * Sets the title of the portlet
	 */
	public abstract void setTitle();
	
	/**
	 * Defines the headers to be used on the portlet
	 * 
	 * @return An array of headers
	 */
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL,
				HeaderControls.MINIMIZE_BUTTON, buildSettingsControl(),
				HeaderControls.CLOSE_BUTTON };
		
		return h;
	}
	
	/**
	 * Builds a HeadControl for the Settings button
	 * 
	 * @return A HeadControl for the Settings button
	 */
	public HeaderControl buildSettingsControl() {
		
		ClickHandler clickHandler = new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				String src = ((HeaderControl) event.getSource()).getSrc();
				SC.say("Control " + src + " clicked");
			}
		};
		
		return new HeaderControl(HeaderControl.SETTINGS, clickHandler);
	}
}