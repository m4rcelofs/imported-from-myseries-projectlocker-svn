package se.kth.myseries.server.persistence.entity;

import java.util.Date;

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
	
	public Episode(final Integer number, final String name,
			final Text description, final Integer runtimeMinutes,
			final Date airDate, final Season season) {
		super();
		
		this.number = number;
		this.name = name;
		this.description = description;
		this.runtimeMinutes = runtimeMinutes;
		this.airDate = airDate;
		this.season = season;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key		id;
	
	@Persistent
	private Integer	number;
	
	@Persistent
	private String	name;
	
	@Persistent
	private Text	description;
	
	@Persistent
	private Integer	runtimeMinutes;
	
	@Persistent
	private Date	airDate;
	
	@Persistent
	private Season	season;
	
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
	
	public Integer getRuntimeMinutes() {
		return runtimeMinutes;
	}
	
	public void setRuntimeMinutes(final Integer runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}
	
	public Date getAirDate() {
		return airDate;
	}
	
	public void setAirDate(final Date airDate) {
		this.airDate = airDate;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(final Season season) {
		this.season = season;
	}
	
}
