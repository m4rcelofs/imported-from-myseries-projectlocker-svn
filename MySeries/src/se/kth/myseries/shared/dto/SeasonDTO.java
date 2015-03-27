package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * Data Transfer Object for the Season entity
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeasonDTO implements IsSerializable, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	public Long					id;
	
	public Integer				year;
	
	public Integer				seasonNbr;
	
	/**
	 * Episodes will only have ID, number, firstAired and name filled
	 */
	public List<EpisodeDTO>		episodeNames;
	
	public List<String>			bannerURLs;
	
}
