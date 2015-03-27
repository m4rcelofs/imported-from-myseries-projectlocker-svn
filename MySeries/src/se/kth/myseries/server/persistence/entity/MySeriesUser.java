package se.kth.myseries.server.persistence.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 02/11/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MySeriesUser implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key					id;
	
	/**
	 * ID on facebook
	 */
	@Persistent
	private String				facebook_id;
	
	@Persistent
	private Date				installDate;
	
	@Persistent
	private Date				lastLoggedIn;
	
	@Persistent
	private String				firstName;
	
	@Persistent
	private String				lastName;
	
	@Persistent
	private Date				birthday;
	
	@Persistent
	private String				pictureURL;
	
	/**
	 * Keys to <code>Series</code> objects
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private Set<Key>			watchlist;
	
	/**
	 * Series that the user used to watch, but doesn't anymore
	 */
	@Persistent
	private Set<Key>			oldWatchlist;
	
	/**
	 * Keys to <code>Episode</code> objects
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private Set<Key>			episodesWatched;
	
	@Persistent
	private Set<Key>			commentsOnSeries;
	
	@Persistent
	private Set<Key>			commentsOnEpisodes;
	
	/**
	 * Don't use to create objects :p
	 */
	public MySeriesUser() {}
	
	public Key getId() {
		return id;
	}
	
	public String getFacebookId() {
		return facebook_id;
	}
	
	public void setFacebookId(final String facebookId) {
		this.facebook_id = facebookId;
	}
	
	public Date getInstallDate() {
		return installDate;
	}
	
	public void setInstallDate(final Date installDate) {
		this.installDate = installDate;
	}
	
	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}
	
	public void setLastLoggedIn(final Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}
	
	/**
	 * @return An immutable copy of the watchlist
	 */
	public Set<Key> getWatchlist() {
		return watchlist == null ? null : Collections
				.unmodifiableSet(watchlist);
	}
	
	public void addToWatchlist(final Key key) {
		if (watchlist == null)
			watchlist = new HashSet<Key>();
		watchlist.add(key);
		
		if (oldWatchlist != null && oldWatchlist.contains(key))
			removeFromOldWatchlist(key);
	}
	
	/**
	 * If a series is removed from the watchlist but the user has watched some
	 * episodes, it goes to the oldWatchlist
	 * 
	 * @param key
	 * @param hasWatchedEpisodes
	 */
	public void removeFromWatchlist(final Key key) {
		watchlist.remove(key);
		
		for (Key ep : episodesWatched)
			// TODO test this
			if (ep.getParent().getParent().equals(key)) {
				// this series has at least one watched episode
				addToOldWatchlist(key);
				break;
			}
	}
	
	public void addToOldWatchlist(final Key key) {
		if (oldWatchlist == null)
			oldWatchlist = new HashSet<Key>();
		this.oldWatchlist.add(key);
	}
	
	public boolean watches(final SeriesDTO series) {
		Key key = KeyFactory.createKey(Series.class.getSimpleName(), series.id);
		return this.watchlist.contains(key);
	}
	
	public void removeFromOldWatchlist(final Key key) {
		this.oldWatchlist.remove(key);
	}
	
	public Set<Key> getOldWatchlist() {
		return oldWatchlist == null ? null : Collections
				.unmodifiableSet(oldWatchlist);
	}
	
	/**
	 * @return An immutable collection
	 */
	public Set<Key> getEpisodesWatched() {
		return episodesWatched == null ? null : Collections
				.unmodifiableSet(episodesWatched);
	}
	
	public void addEpisodeWatched(final Key key) {
		if (episodesWatched == null)
			episodesWatched = new HashSet<Key>();
		episodesWatched.add(key);
		
		Key seriesKey = key.getParent().getParent();
		if (watchlist != null && !watchlist.contains(seriesKey))
			// user has seen episode but the series is not on the watchlist
			addToOldWatchlist(seriesKey);
	}
	
	public void removeEpisodeWatched(final Key key) {
		episodesWatched.remove(key);
		
		Key seriesKey = key.getParent().getParent();
		boolean hasOneEpisodeWatched = false;
		for (Key ep : episodesWatched)
			if (ep.getParent().getParent().equals(seriesKey)) {
				hasOneEpisodeWatched = true;
				break;
			}
		if (!hasOneEpisodeWatched)
			removeFromOldWatchlist(seriesKey);
	}
	
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setPictureURL(final String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	public String getPictureURL() {
		return pictureURL;
	}
	
	public void addCommentOnSeries(final Key commentOnSeries) {
		if (this.commentsOnSeries == null)
			commentsOnSeries = new HashSet<Key>();
		
		this.commentsOnSeries.add(commentOnSeries);
	}

	public Set<Key> getCommentsOnSeries() {
		return commentsOnSeries == null ? null : Collections
				.unmodifiableSet(commentsOnSeries);
	}
	
	public void addCommentOnEpisodes(final Key commentOnEpisodes) {
		if (this.commentsOnEpisodes == null)
			commentsOnEpisodes = new HashSet<Key>();
		
		this.commentsOnEpisodes.add(commentOnEpisodes);
	}

	public Set<Key> getCommentsOnEpisodes() {
		return commentsOnEpisodes == null ? null : Collections
				.unmodifiableSet(commentsOnEpisodes);
	}
}
