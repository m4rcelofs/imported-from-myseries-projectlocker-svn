package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * Data Trannsfer Object for the Episode entity
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class EpisodeDTO implements IsSerializable, Serializable {
	
	public EpisodeDTO() {}
	
	/**
	 * @param id
	 * @param name
	 * @param number
	 */
	public EpisodeDTO(final long id, final String name, final Integer number) {
		this.id = id;
		this.episodeName = name;
		this.episodeNbr = number;
	}
	
	private static final long	serialVersionUID	= 1L;
	
	public Long					id;
	
	public String				seriesName;
	
	public String				episodeName;
	
	public Integer				episodeNbr;
	
	/**
	 * yyyyMMdd
	 */
	public Integer				firstAired;
	
	public String				description;
	
	public Integer				season;
	
	public List<String>			directors;
	
	public List<String>			writers;
	
	public List<String>			guestStars;
	
	// TODO calculate this
	public Integer				nbrOfWatchers;
	
	public String toReadableString() {
		
		StringBuilder builder = new StringBuilder();
		{
			builder.append(seriesName).append(" ").append(season).append("x");
			if (episodeNbr < 10)
				builder.append("0");
			builder.append(episodeNbr);
			builder.append(": ");
			builder.append(episodeName);
		}
		return builder.toString();

	}

	@Override
	public String toString() {
		return "EpisodeDTO [id=" + id + ", seriesName=" + seriesName
		+ ", episodeName=" + episodeName + ", episodeNbr=" + episodeNbr
		+ ", firstAired=" + firstAired + ", season=" + season + "]";
	}

}
