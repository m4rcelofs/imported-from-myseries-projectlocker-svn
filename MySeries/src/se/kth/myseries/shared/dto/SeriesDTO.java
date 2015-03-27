package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * Data Transfer Object for the Series entity
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeriesDTO implements IsSerializable, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	public Long					id;
	
	public String				tvDbId;
	
	public String				imdbId;
	
	public String				name;
	
	public String				description;
	
	/**
	 * yyyyMMdd
	 */
	public Integer				firstAired;
	
	public List<String>			actors;
	
	public String				airsDayOfWeek;
	
	public String				airsTime;
	
	public List<String>			genres;
	
	public String				network;
	
	public Integer				runtime;
	
	public String				status;
	
	/**
	 * Can get thumbnails by adding <code>&thumb=50</code> to the end of the url
	 */
	public List<String>			bannerLinks;
	
	/**
	 * Can get thumbnails by adding <code>&thumb=50</code> to the end of the url
	 */
	public List<String>			posterLinks;
	
	public Integer				nbrSeasons;
	
	public Integer				nbrEpisodes;
	
	public boolean				watchedByCurrentUser;
	
	// TODO calculate this
	public Integer				nbrOfAiredEpisodes;
	
	// TODO calculate this
	public Integer				nbrOfWatchers;
	
	@Override
	public String toString() {
		return "SeriesDTO [id=" + id + ", tvDbIds=" + tvDbId + ", name=" + name
		+ ", watchedByCurrentUser=" + watchedByCurrentUser + "]";
	}
	
}
