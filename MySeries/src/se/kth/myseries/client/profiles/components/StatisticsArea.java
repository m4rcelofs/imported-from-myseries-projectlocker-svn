package se.kth.myseries.client.profiles.components;

import se.kth.myseries.shared.dto.StatisticsDTO;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public class StatisticsArea extends Window {
	
	private final Label	statsData;
	
	public StatisticsArea() {
		this.setTitle("Statistics");
		this.statsData = new Label();
		this.addItem(statsData);
	}
	
	public void setStatistics(final StatisticsDTO stats) {
		
		StringBuilder string = new StringBuilder();
		{
			
			string.append("Registered on: ").append(
					DateTimeFormat.getFormat("dd/MM/yyyy").format(
							stats.registeredOn));
			string.append("<br />");
			
			string.append("Currently watching ")
			.append(stats.nbrSeriesOnWatchlist).append(" series");
			string.append("<br />");
			
			string.append("Already watched ")
			.append(stats.nbrSeriesOnOldWatchlist)
			.append(" other series");
			string.append("<br />");
			
			string.append("Watched a total of ")
			.append(stats.nbrEpisodesWatched).append(" episodes");
			string.append("<br />");
			
		}
		statsData.setContents(string.toString());
		
	}
	
	public void setError(final Throwable error) {
		statsData
		.setContents("Could not get statistics from the server: <br />"
				+ error.getMessage());
	}
}
