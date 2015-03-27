package se.kth.myseries.server.persistence.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 10/11/2010
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Image {
	
	public Image() {}
	
	public Image(final Blob data, final Blob thumbnail,
			final String originalUrl,
			final String contentType) {
		this.data = data;
		this.thumbnail = thumbnail;
		this.originalUrl = originalUrl;
		this.contentType = contentType;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key		id;
	
	@Persistent
	private Blob	data;
	
	@Persistent
	private Blob	thumbnail;
	
	@Persistent
	private String	originalUrl;
	
	@Persistent
	private String	contentType;
	
	public void setId(final Key id) {
		this.id = id;
	}
	
	public Key getId() {
		return id;
	}
	
	public void setData(final Blob data) {
		this.data = data;
	}
	
	public Blob getData() {
		return data;
	}
	
	public void setOriginalUrl(final String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}
	
	public String getContentType() {
		if (contentType == null)
			return "image/jpeg"; // just guessing
		return contentType;
	}
	
	public void setThumbnail(final Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public Blob getThumbnail() {
		return thumbnail;
	}
	
}
