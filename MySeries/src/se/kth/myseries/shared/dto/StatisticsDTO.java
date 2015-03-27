package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 10/11/2010
 */
public class StatisticsDTO implements IsSerializable, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	public Date					registeredOn;
	public Integer				nbrSeriesOnWatchlist;
	public Integer				nbrSeriesOnOldWatchlist;
	public Integer				nbrEpisodesWatched;
	
}
