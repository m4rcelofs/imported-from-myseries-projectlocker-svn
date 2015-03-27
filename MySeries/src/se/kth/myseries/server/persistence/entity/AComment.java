package se.kth.myseries.server.persistence.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 25/11/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class AComment {
	
	public AComment() {}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key		id;
	
	@Persistent
	private Key		user;
	
	@Persistent
	private Date	date;
	
	@Persistent
	private String	text;
	
	public void setId(final Key id) {
		this.id = id;
	}
	
	public Key getId() {
		return id;
	}
	
	public void setUser(final Key user) {
		this.user = user;
	}
	
	public Key getUser() {
		return user;
	}
	
	public void setDate(final Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setText(final String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
