/**
 * 
 */
package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries
 * 
 * @author Xu Liu
 * @author Marcelo
 * @since Nov 25, 2010
 */
public class RankDTO implements IsSerializable, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * The arithmetic mean of all the rankings
	 */
	public Double				averageRanking;
	
	/**
	 * The rank given by the current user to some series
	 */
	public Double				currentUserRanking;
	
	/**
	 * A list of ranks given to this series by the friends of the current user
	 */
	public List<RankContents>	ranks;
	
	public class RankContents implements IsSerializable, Serializable {
		
		private static final long	serialVersionUID	= 1L;
		public UserDTO				user;
		public Double				ranking;
	}
}
