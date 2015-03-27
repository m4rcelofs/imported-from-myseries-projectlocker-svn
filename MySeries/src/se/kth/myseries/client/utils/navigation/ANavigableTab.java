package se.kth.myseries.client.utils.navigation;

import java.util.Stack;

import se.kth.myseries.client.utils.notifications.AObservableTab;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * MySeries <br>
 * 
 * @author Léna
 * @author Marcelo
 * @since 10 nov. 2010
 */
public abstract class ANavigableTab extends AObservableTab {
	
	private final int							maxNumberOfPages	= 4;
	
	private final NavigationBar					navBar;
	
	private final CardLayoutCanvas				cardLayout;
	private final Stack<NavigationBarButton>	buttonsList;
	
	protected ANavigableTab(final String name, final String picture) {
		
		super(name, picture);
		
		cardLayout = new CardLayoutCanvas();
		this.navBar = new NavigationBar(this);
		buttonsList = new Stack<NavigationBarButton>();
		
		VLayout vLayout = new VLayout();
		{
			vLayout.setMembersMargin(10);
			vLayout.addMember(navBar);
			vLayout.addMember(cardLayout);
		}
		this.setPane(vLayout);
		
	}
	
	protected void addNavBarSeparator() {
		navBar.addSeparator();
	}
	
	public void addPage(final Page page) {
		
		if (buttonsList.size() >= maxNumberOfPages)
			// never remove the first one!
			removePage(buttonsList.remove(1));
		
		buttonsList
				.push(navBar.addButton(page.getName(), page.getIdentifier()));
		
		cardLayout.addCard(page.getIdentifier(), page.getCanvas());
		cardLayout.showCard(page.getIdentifier());
		
	}
	
	public void goBackTo(final Object identifier) {
		
		while (!buttonsList.peek().getIdentifier().equals(identifier))
			removePage(buttonsList.pop());
		
		cardLayout.showCard(buttonsList.peek().getIdentifier());
		
	}
	
	private void removePage(final ToolStripButton button) {
		navBar.removeMember(button);
		cardLayout.removeCard(button.getTitle());
	}
	
}
