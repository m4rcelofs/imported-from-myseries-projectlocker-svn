package se.kth.myseries.client.home.portlets;

import com.smartgwt.client.types.HeaderControls;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class AdsPortlet extends APortlet {
	
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
		this.setTitle("Advertisement");
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL,
				HeaderControls.MINIMIZE_BUTTON };
		
		return h;
	}
}
