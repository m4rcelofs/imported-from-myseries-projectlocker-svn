package se.kth.myseries.client.utils.navigation;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * MySeries <br>
 * 
 * @author Léna
 * @since 2 nov. 2010
 */
public class NavigationBar extends ToolStrip {
	
	ANavigableTab	mainTab;
	
	public NavigationBar(final ANavigableTab main) {
		
		this.mainTab = main;
		this.setAutoHeight();
		this.setAutoWidth();
		/*
		 * ToolStripButton home = new ToolStripButton();
		 * home.setTitle("Home");
		 * this.addButton(home);
		 * home.disable();
		 */
	}
	
	public NavigationBarButton addButton(final String buttonName,
			final Object buttonID) {
		NavigationBarButton button = new NavigationBarButton(buttonID);
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				mainTab.goBackTo(((NavigationBarButton) event.getSource())
						.getIdentifier());
			}
		});
		
		button.setTitle(buttonName);
		button.setIcon("arrow.png");
		this.addButton(button);
		return button;
	}
	
}
