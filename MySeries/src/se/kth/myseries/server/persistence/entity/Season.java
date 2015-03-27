package se.kth.myseries.server.persistence.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import se.kth.myseries.shared.dto.EpisodeDTO;

import com.google.appengine.api.datastore.Key;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Season {
	
	public Season() {}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key					id;
	
	/**
	 * The ID on TheTVDB API
	 */
	@Persistent
	private String				tvDbId;
	
	@Persistent
	private Integer				number;
	
	@Persistent
	private Integer				year;
	
	@Persistent
	private Series				serie;
	
	/**
	 * Keys to <code>image</code> objects <br />
	 * it's the app responsability to keep the relation!
	 */
	@Persistent
	private List<Key>			bannerImages;
	
	@Persistent(mappedBy = "season")
	// no need to keep it ordered on database, only on retrieval
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "number asc"))
	private ArrayList<Episode>	episodes;
	
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
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(final Integer year) {
		this.year = year;
	}
	
	public Series getSeries() {
		return serie;
	}
	
	public void setSeries(final Series serie) {
		this.serie = serie;
	}
	
	public List<Episode> getEpisodes() {
		return episodes == null ? null : Collections.unmodifiableList(episodes);
	}
	
	public Integer getNumberOfEpisodes() {
		return episodes.size();
	}
	
	public void setTvDbId(final String tvDbId) {
		this.tvDbId = tvDbId;
	}
	
	public String getTvDbId() {
		return tvDbId;
	}
	
	public void addEpisode(final Episode episode) {
		if (this.episodes == null)
			this.episodes = new ArrayList<Episode>();
		
		this.episodes.add(episode);
	}
	
	public void addBanner(final Key image) {
		if (this.bannerImages == null)
			this.bannerImages = new ArrayList<Key>();
		
		this.bannerImages.add(image);
	}
	
	public List<Key> getPosters() {
		return bannerImages == null ? null : Collections
				.unmodifiableList(bannerImages);
	}
	
	public List<String> getBannerLinks() {
		List<String> links = new ArrayList<String>(bannerImages.size());
		
		for (Key key : bannerImages)
			links.add("/image?id=" + key.getId());
		
		return links;
	}
	
	/**
	 * @return
	 */
	public List<EpisodeDTO> getEpisodeNames() {
		
		List<EpisodeDTO> dtos = new ArrayList<EpisodeDTO>(episodes.size());
		
		for (Episode p : episodes)
			dtos.add(new EpisodeDTO(p.getId().getId(), p.getName(), p
					.getNumber()));
		
		return dtos;
	}
}
