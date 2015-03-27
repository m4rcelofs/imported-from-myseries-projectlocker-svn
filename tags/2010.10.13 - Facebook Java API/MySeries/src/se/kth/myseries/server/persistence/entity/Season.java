package se.kth.myseries.server.persistence.entity;

import java.util.ArrayList;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Season {
	
	public Season(final Integer number, final Integer year,
			final Serie serie, final ArrayList<Episode> episodes) {
		super();
		this.number = number;
		this.year = year;
		this.serie = serie;
		this.episodes = episodes;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key					id;
	
	@Persistent
	private Integer				number;
	
	@Persistent
	// TODO keep as int or date?
	private Integer				year;
	
	@Persistent
	private Serie				serie;
	
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
	
	public Serie getSerie() {
		return serie;
	}
	
	public void setSerie(final Serie serie) {
		this.serie = serie;
	}
	
	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}
	
	public Integer getNumberOfEpisodes() {
		return episodes.size();
	}
	
	/**
	 * The sum of all episodes' runtimes, in minutes
	 * 
	 * @return
	 */
	public Integer getTotalRuntimeMin() {
		Integer runtime = 0;
		for (Episode e : episodes)
			runtime += e.getRuntimeMinutes();
		return runtime;
	}
	
	public void setEpisodes(final ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}
}
