package se.kth.myseries.client.utils.navigation;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 14/12/2010
 */
public enum Navigation {
	
	HOME(0, false, "home"), WATCHLIST(1, false, "watchlist"), USER_PROFILE(2,
			true, "me"), FRIEND_PROFILE(2, true, "friend"), SEARCH(3, true,
			"search"), SERIES(3, true, "series"), EPISODE(3, true, "episode");
	
	public final int		tabNumber;
	public final boolean	isNavigable;
	public final String		historyToken;
	
	private Navigation(final int tabNumber, final boolean isNavigable,
			final String historyToken) {
		this.tabNumber = tabNumber;
		this.isNavigable = isNavigable;
		this.historyToken = historyToken;
	}
	
	public static Navigation getByName(final String name) {
		for (Navigation n : Navigation.values())
			if (n.historyToken.equalsIgnoreCase(name))
				return n;
		
		// default
		return Navigation.HOME;
	}

}
