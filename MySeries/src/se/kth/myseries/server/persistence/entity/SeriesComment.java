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
public class SeriesComment extends AComment {
	
	@Persistent
	private Key	series;

	public void setSeries(final Key series) {
		this.series = series;
	}

	public Key getSeries() {
		return series;
	}

}
