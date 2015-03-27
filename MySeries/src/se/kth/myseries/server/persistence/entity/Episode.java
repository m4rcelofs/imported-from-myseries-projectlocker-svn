package se.kth.myseries.server.persistence.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Episode {
	
	public Episode() {}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key						id;
	
	/**
	 * The ID on TheTVDB API
	 */
	@Persistent
	private String					tvDbId;
	
	/**
	 * From TheTVDB API
	 */
	@Persistent
	private String					lastUpdated;
	
	@Persistent
	private Integer					number;
	
	@Persistent
	private String					name;
	
	@Persistent
	private Text					description;
	
	@Persistent
	private Key						series;
	
	@Persistent
	private Season					season;
	
	@Persistent
	private List<String>			directors;
	
	@Persistent
	private List<String>			writers;
	
	/**
	 * yyyyMMdd
	 */
	@Persistent
	private Integer					firstAired;
	
	@Persistent
	private List<String>			guestStars;
	
	@Persistent
	private String					imdbId;
	
	@Persistent
	private String					language;
	
	/**
	 * Keys to <code>MySeriesUser</code> objects
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private Set<Key>				watchers;
	
	@Persistent
	private List<EpisodeComment>	comments;
	
	public Key getId() {
		return id;
	}
	
	public void setId(final Key id) {
		this.id = id;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(final Integer number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public Text getDescription() {
		return description;
	}
	
	public void setDescription(final Text description) {
		this.description = description;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(final Season season) {
		this.season = season;
	}
	
	/**
	 * @return An immutable copy of the watchers
	 */
	public Set<Key> getWatchers() {
		return watchers == null ? null : Collections.unmodifiableSet(watchers);
	}
	
	public void addWatcher(final Key key) {
		watchers.add(key);
	}
	
	public void removeWatcher(final Key key) {
		watchers.remove(key);
	}
	
	public void setTvDbId(final String tvDbId) {
		this.tvDbId = tvDbId;
	}
	
	public String getTvDbId() {
		return tvDbId;
	}
	
	public void addDirector(final List<String> directors) {
		if (this.directors == null)
			this.directors = new ArrayList<String>();
		this.directors.addAll(directors);
	}
	
	public void removeDirector(final String director) {
		this.directors.remove(director);
	}
	
	public List<String> getDirectors() {
		return directors == null ? null : Collections
				.unmodifiableList(directors);
	}
	
	public void setFirstAired(final Integer firstAired) {
		this.firstAired = firstAired;
	}
	
	public Integer getFirstAired() {
		return firstAired;
	}
	
	public void addGuestStars(final List<String> guestStars) {
		if (this.guestStars == null)
			this.guestStars = new ArrayList<String>();
		this.guestStars.addAll(guestStars);
	}
	
	public List<String> getGuestStars() {
		return guestStars == null ? null : Collections
				.unmodifiableList(guestStars);
	}
	
	public void setImdbId(final String imdbId) {
		this.imdbId = imdbId;
	}
	
	public String getImdbId() {
		return imdbId;
	}
	
	public void setLanguage(final String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void addWriters(final List<String> writers) {
		if (this.writers == null)
			this.writers = new ArrayList<String>();
		this.writers.addAll(writers);
	}
	
	public void removeWriter(final String writer) {
		this.writers.remove(writer);
	}
	
	public List<String> getWriters() {
		return writers == null ? null : Collections.unmodifiableList(writers);
	}
	
	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	public void setSeries(final Key series) {
		this.series = series;
	}
	
	public Key getSeries() {
		return series;
	}
	
	public void addComment(final EpisodeComment comment) {
		if (this.comments == null)
			this.comments = new ArrayList<EpisodeComment>();
		
		this.comments.add(comment);
	}
	
	public List<EpisodeComment> getComments() {
		return comments == null ? null : Collections.unmodifiableList(comments);
	}
}
