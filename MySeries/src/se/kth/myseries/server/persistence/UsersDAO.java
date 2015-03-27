package se.kth.myseries.server.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.Query;

import se.kth.myseries.server.persistence.entity.MySeriesUser;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 03/11/2010
 */
public class UsersDAO extends ADAO {
	
	// private Key createUserKey(final Long key) {
	// return KeyFactory.createKey(MySeriesUser.class.getSimpleName(), key);
	// }
	
	public void save(final MySeriesUser user) {
		super.save(user);
	}
	
	/**
	 * @param dto
	 */
	public void save(final UserDTO dto) {
		
		MySeriesUser user;
		
		try {
			user = getByFacebookID(dto.facebook_id);
		} catch (Exception e) {
			log.fine("User not found, creating a new user on DB: id["
					+ dto.facebook_id + "]");
			user = new MySeriesUser();
		}
		
		{
			user.setBirthday(convert(dto.birthday));
			user.setFacebookId(dto.facebook_id);
			user.setFirstName(dto.firstName);
			user.setLastName(dto.lastName);
			user.setPictureURL(dto.pictureURL);
			
			user.setLastLoggedIn(new Date());
			if (user.getId() == null) // only if new user
				user.setInstallDate(new Date());
		}
		
		super.save(user);
		
	}
	
	public MySeriesUser getByFacebookID(final String facebookID) {
		Query query = pm.newQuery(MySeriesUser.class);
		try {
			query.setFilter("facebook_id == facebookIdParam");
			query.declareParameters("String facebookIdParam");
			
			@SuppressWarnings("unchecked")
			List<MySeriesUser> result = (List<MySeriesUser>) query
			.execute(facebookID);
			if (result != null && !result.isEmpty())
				return result.get(0);
			else
				return new MySeriesUser();
		} finally {
			query.closeAll();
		}
	}
	
	private Date convert(final String ddMMyyyy) {
		if (ddMMyyyy == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return df.parse(ddMMyyyy);
		} catch (ParseException e) {
			log.warning(e.toString());
		}
		return null;
	}
	
	/**
	 * @param date
	 * @return yyyyMMdd
	 */
	private String convert(final Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		return df.format(date);
		
	}
	
	/**
	 * Checks if a given user exists on the database (that is, uses this
	 * application)
	 * 
	 * @param facebookID
	 * @return
	 */
	public Boolean usesApplication(final String facebookID) {
		if (getByFacebookID(facebookID) != null)
			return true;
		return false;
	}
	
	/**
	 * @param watchers
	 * @return
	 */
	public Set<UserDTO> getByID(final Set<Key> ids) {
		Set<UserDTO> dtos = new HashSet<UserDTO>();
		
		for (Key key : ids)
			dtos.add(convert(getByID(key)));
		return dtos;
	}
	
	public MySeriesUser getByID(final Key key) {
		return pm.getObjectById(MySeriesUser.class, key);
	}
	
	/**
	 * @param userID
	 * @return
	 */
	public MySeriesUser getByID(final Long userID) {
		Key key = KeyFactory.createKey(MySeriesUser.class.getSimpleName(),
				userID);
		return getByID(key);
	}
	
	/**
	 * @param objectById
	 * @return
	 */
	public UserDTO convert(final MySeriesUser user) {
		UserDTO dto = new UserDTO();
		{
			dto.birthday = convert(user.getBirthday());
			dto.facebook_id = user.getFacebookId();
			dto.firstName = user.getFirstName();
			dto.id = user.getId().getId();
			dto.lastName = user.getLastName();
			dto.pictureURL = user.getPictureURL();
		}
		return dto;
	}
	
}
