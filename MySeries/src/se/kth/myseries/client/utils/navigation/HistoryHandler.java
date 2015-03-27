package se.kth.myseries.client.utils.navigation;

import se.kth.myseries.client.MySeriesEP;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 14/12/2010
 */
public class HistoryHandler implements ValueChangeHandler<String>,
TabSelectedHandler {
	
	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		String token = event.getValue();
		
		String tab;
		String param = null;
		
		int position = token.indexOf('~');
		if (position > -1) {
			tab = token.substring(0, position);
			param = token.substring(position + 1);
		} else
			tab = token;
		
		Navigation destination = Navigation.getByName(tab);
		
		MySeriesEP.navigate(destination, param);
		
	}
	
	@Override
	public void onTabSelected(final TabSelectedEvent event) {
		
		String token = "";
		switch (event.getTabNum()) {
			case 0:
				token = Navigation.HOME.historyToken;
				break;
			case 1:
				token = Navigation.WATCHLIST.historyToken;
				break;
			case 2:
				token = Navigation.USER_PROFILE.historyToken;
				break;
			case 3:
				token = Navigation.SEARCH.historyToken;
				break;
		}
		
		History.newItem(token, false);
		
	}
	
}
