package se.kth.myseries.server.persistence.entity;

import java.util.ArrayList;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Serie {
	
	
	public Serie(final String name, final Text description,
			final ArrayList<Season> seasons) {
		super();
		this.name = name;
		this.description = description;
		this.seasons = seasons;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long				id;
	
	@Persistent
	private String				name;
	
	@Persistent
	private Text				description;
	
	// TODO image
	// private Blob image;
	
	@Persistent(mappedBy = "serie")
	// no need to keep it ordered on database, only on retrieval
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "number asc"))
	private ArrayList<Season>	seasons;
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
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
	
	public ArrayList<Season> getSeasons() {
		return seasons;
	}
	
	public Integer getNumberOfSeasons() {
		return seasons.size();
	}
	
	public Integer getNumberOfEpisodes() {
		int episodes = 0;
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
		Integer runtime = 0;
		for (Season s : seasons)
			runtime += s.getTotalRuntimeMin();
		return runtime;
	}
	
	public void setSeasons(final ArrayList<Season> seasons) {
		this.seasons = seasons;
	}
	
}
