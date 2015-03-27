package se.kth.myseries.client.utils.navigation;

import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 24/11/2010
 */
public class NavigationBarButton extends ToolStripButton {
	
	private final Object	id;
	
	public NavigationBarButton(final Object id) {
		this.id = id;
	}
	
	public Object getIdentifier() {
		return this.id;
	}

}
