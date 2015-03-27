package se.kth.myseries.shared.dto;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * Data Transfer Object for the Facebook User entity
 * 
 * @author Marcelo
 * @since 28/10/2010
 */
public class UserDTO implements IsSerializable, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	public Long					id;
	
	public String				facebook_id;
	
	public String				firstName;
	
	public String				lastName;
	
	public String				birthday;
	
	public String				pictureURL;
	
	public String getSmallPictureURL() {
		return pictureURL + "?type=small";
	}
	
	public String getSquarePictureURL() {
		return pictureURL + "?type=square";
	}
	
	

	/**
	 * @return
	 */
	public String getLargePictureURL() {
		return pictureURL + "?type=large";
	}
}
