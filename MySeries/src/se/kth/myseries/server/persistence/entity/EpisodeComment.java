package se.kth.myseries.server.persistence.entity;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 25/11/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EpisodeComment extends AComment {
	
	@Persistent
	private Key	episode;
	
	public void setEpisode(final Key episode) {
		this.episode = episode;
	}
	
	public Key getEpisode() {
		return episode;
	}

}
