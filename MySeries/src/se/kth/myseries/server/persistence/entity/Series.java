package se.kth.myseries.server.persistence.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import se.kth.myseries.server.ServerConstants;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Series {
	
	public Series() {}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key					id;
	
	/**
	 * The ID on TheTVDB API
	 */
	@Persistent
	private String				tvDbId;
	
	/**
	 * ID on the IMDB website
	 */
	@Persistent
	private String				imdbId;
	
	@Persistent
	private String				name;
	
	@Persistent
	private String				language;
	
	@Persistent
	private Text				description;
	
	/**
	 * aaaaMMdd
	 */
	@Persistent
	private Integer				firstAired;
	
	@Persistent
	private List<String>		actors;
	
	@Persistent
	private String				airsDayOfWeek;
	
	@Persistent
	private String				airsTime;
	
	/**
	 * Can be used for tags
	 */
	@Persistent
	private List<String>		genres;
	
	@Persistent
	private String				network;
	
	@Persistent
	private Integer				runtime;
	
	@Persistent
	private String				status;
	
	/**
	 * Timestamp from TheTVDB?
	 */
	private String				lastUpdated;
	
	/**
	 * Keys to <code>MySeriesUser</code> objects <br />
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private Set<Key>			watchers;
	
	/**
	 * Keys to <code>image</code> objects <br />
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private List<Key>			bannerImages;
	
	/**
	 * Keys to <code>image</code> objects <br />
	 * it's the app responsibility to keep the relation!
	 */
	@Persistent
	private List<Key>			posterImages;
	
	@Persistent
	private List<SeriesComment>	comments;

	@Persistent(mappedBy = "serie")
	// no need to keep it ordered on database, only on retrieval
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "number asc"))
	private List<Season>		seasons;
	
	public Key getId() {
		return id;
	}
	
	public void setId(final Key id) {
		this.id = id;
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
	
	public List<Season> getSeasons() {
		return seasons == null ? null : Collections.unmodifiableList(seasons);
	}
	
	public Season getSeason(final int seasonNumber) {
		for (Season s : seasons)
			if (s.getNumber() == seasonNumber)
				return s;
		return null;
	}
	
	public Integer getNumberOfSeasons() {
		if (seasons != null)
			return seasons.size();
		else
			return 0;
	}
	
	public Integer getNumberOfEpisodes() {
		int episodes = 0;
		if (seasons != null)
			for (Season season : seasons)
				episodes += season.getNumberOfEpisodes();
		return episodes;
	}
	
	/**
	 * The sum of all episodes' runtimes, in minutes
	 * 
	 * @return
	 */
	public Integer getTotalRuntimeMin() {
		
		int nbrEpisodes = 0;
		
		for (Season s : seasons)
			nbrEpisodes += s.getNumberOfEpisodes();
		
		return nbrEpisodes * runtime;
	}
	
	public void addSeason(final Season season) {
		if (this.seasons == null)
			this.seasons = new ArrayList<Season>();
		this.seasons.add(season);
	}
	
	/**
	 * @return An immutable copy of the watchers
	 */
	public Set<Key> getWatchers() {
		return watchers == null ? null : Collections.unmodifiableSet(watchers);
	}
	
	public void addWatcher(final Key key) {
		if (this.watchers == null)
			this.watchers = new HashSet<Key>();
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
	
	public void setLanguage(final String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setFirstAired(final Integer firstAired) {
		this.firstAired = firstAired;
	}
	
	public Integer getFirstAired() {
		return firstAired;
	}
	
	public void addActors(final List<String> actor) {
		if (this.actors == null)
			this.actors = new ArrayList<String>();
		this.actors.addAll(actor);
	}
	
	public void removeActor(final String actor) {
		actors.remove(actor);
	}
	
	/**
	 * @return Unmodifiable list
	 */
	public List<String> getActors() {
		return actors == null ? null : Collections.unmodifiableList(actors);
	}
	
	public void setImdbId(final String imdbId) {
		this.imdbId = imdbId;
	}
	
	public String getImdbId() {
		return imdbId;
	}
	
	public void setAirsDayOfWeek(final String airsDayOfWeek) {
		this.airsDayOfWeek = airsDayOfWeek;
	}
	
	public String getAirsDayOfWeek() {
		return airsDayOfWeek;
	}
	
	public void setAirsTime(final String airsTime) {
		this.airsTime = airsTime;
	}
	
	public String getAirsTime() {
		return airsTime;
	}
	
	public void addGenres(final List<String> genres) {
		if (this.genres == null)
			this.genres = new ArrayList<String>();
		this.genres.addAll(genres);
	}
	
	public void removeRenre(final String genre) {
		genres.remove(genre);
	}
	
	/**
	 * Unmodifiable list
	 * 
	 * @return
	 */
	public List<String> getGenres() {
		return genres == null ? null : Collections.unmodifiableList(genres);
	}
	
	public void setNetwork(final String network) {
		this.network = network;
	}
	
	public String getNetwork() {
		return network;
	}
	
	public void setRuntime(final Integer runtime) {
		this.runtime = runtime;
	}
	
	public Integer getRuntime() {
		return runtime;
	}
	
	public void setStatus(final String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	public void addBanner(final Key image) {
		if (this.bannerImages == null)
			this.bannerImages = new ArrayList<Key>();
		
		this.bannerImages.add(image);
	}
	
	public List<Key> getBanners() {
		return bannerImages == null ? null : Collections
				.unmodifiableList(bannerImages);
	}
	
	public List<String> getBannerLinks() {
		
		if (bannerImages == null)
			return null;

		List<String> links = new ArrayList<String>(bannerImages.size());
		
		for (Key key : bannerImages)
			links.add(ServerConstants.IMAGE_SERVLET_URL + key.getId());
		
		return links;
	}
	
	public void addPoster(final Key image) {
		if (this.posterImages == null)
			this.posterImages = new ArrayList<Key>();
		
		this.posterImages.add(image);
	}
	
	public List<Key> getPosters() {
		return posterImages == null ? null : Collections
				.unmodifiableList(posterImages);
	}
	
	public List<String> getPosterLinks() {
		
		if (posterImages == null)
			return null;

		List<String> links = new ArrayList<String>(posterImages.size());
		
		if (posterImages != null)
			for (Key key : posterImages)
				links.add(ServerConstants.IMAGE_SERVLET_URL + key.getId());
		
		return links;
	}
	
	public void addComment(final SeriesComment comment) {
		if (this.comments == null)
			this.comments = new ArrayList<SeriesComment>();
		
		this.comments.add(comment);
	}
	
	public List<SeriesComment> getComments() {
		return comments == null ? null : Collections.unmodifiableList(comments);
	}
}
