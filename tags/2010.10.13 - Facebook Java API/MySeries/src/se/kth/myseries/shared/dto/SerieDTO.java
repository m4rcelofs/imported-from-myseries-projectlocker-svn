package se.kth.myseries.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * Data Transfer Object for the Series entity
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SerieDTO implements IsSerializable {
	
	public SerieDTO() {}
	
	public SerieDTO(final Long id, final String name, final String description,
			final Integer nbrSeasons, final Integer nbrEpisodes) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.nbrSeasons = nbrSeasons;
		this.nbrEpisodes = nbrEpisodes;
	}
	
	public Long		id;
	
	public String	name;
	
	public String	description;
	
	// TODO image
	// private Blob image;
	
	public Integer	nbrSeasons;
	
	public Integer	nbrEpisodes;
	
	@Override
	public String toString() {
		return "<b>ID:</b> " + id + "<br />" + "<b>Name:</b> " + name
		+ "<br />" + "<b>Number of Seasons:</b> " + nbrSeasons
		+ "<br />" + "<b>Number of Episodes:</b> " + nbrEpisodes
		+ "<br />" + "<b>Description:</b> " + description + "<br / >";
	}
	
}
