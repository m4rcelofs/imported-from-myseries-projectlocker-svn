package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;

/**
 * MySeries <br>
 * A portlet with "hot" news
 * //TODO tips on how to use the system, news, badges, etc
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class HighlightsPortlet extends APortlet {
	
	public HighlightsPortlet() {
		super();
		this.buildContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#buildContent()
	 */
	@Override
	public void buildContent() {
		
		Label label = new Label();
		{
			label.setAlign(Alignment.CENTER);
			label.setLayoutAlign(VerticalAlignment.CENTER);
			label.setContents("Welcome to MySeries! This is an early beta release! <br /> "
					+ "This means that not all features are available, and that no data is saved.");
		}
		addItem(label);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Must-See");
		
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL };
		
		return h;
	}
	
}
