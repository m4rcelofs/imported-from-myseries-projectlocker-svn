package se.kth.myseries.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.calendar.CalendarEvent;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 01/11/2010
 */
public class WatchlistEpisodesDTO implements IsSerializable {
	
	/**
	 * A list of all episodes airing on a specific date (also the key) <br />
	 * Key is in the format yyyyMMdd
	 */
	public Map<Integer, List<EpisodeDTO>>	episodes;
	
	public WatchlistEpisodesDTO() {
		episodes = new HashMap<Integer, List<EpisodeDTO>>();
	}
	
	/**
	 * @param date
	 *            String on the format yyyyMMdd
	 * @param episode
	 */
	public void addEpisode(final Integer date, final EpisodeDTO episode) {
		List<EpisodeDTO> list = episodes.get(date);
		if (list == null) {
			list = new ArrayList<EpisodeDTO>();
			episodes.put(date, list);
		}
		list.add(episode);
	}
	
	/**
	 * Returns the episodes for a given day
	 * 
	 * @param String
	 *            on the format yyyyMMdd
	 * @return
	 */
	public List<EpisodeDTO> getEpisodes(final Integer yyyyMMdd) {
		if (episodes.get(yyyyMMdd) == null)
			return null;
		return new ArrayList<EpisodeDTO>(episodes.get(yyyyMMdd));
	}
	
	public Set<Integer> getEpisodeDates() {
		return new HashSet<Integer>(episodes.keySet());
	}
	
	public boolean hasEpisodesFor(final Integer date) {
		return episodes.containsKey(date);
	}
	
	public CalendarEvent[] toCalendarEvents() {
		
		List<CalendarEvent> list = new ArrayList<CalendarEvent>();
		{
			DateTimeFormat formatter = DateTimeFormat.getFormat("yyyyMMdd");
			for (Integer date : episodes.keySet())
				for (EpisodeDTO ep : episodes.get(date))
					try {
						
						String name = ep.toReadableString();
						Date epDate = formatter.parse(Integer.toString(date));
						
						list.add(new CalendarEvent((int) ep.id.longValue(),
								name, name, epDate, epDate));
					} catch (Exception ignore) {}
					
					return list.toArray(new CalendarEvent[0]);
					
		}
		
	}
}
