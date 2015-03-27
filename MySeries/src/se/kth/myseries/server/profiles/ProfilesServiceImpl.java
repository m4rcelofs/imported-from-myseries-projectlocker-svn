package se.kth.myseries.server.profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import se.kth.myseries.client.profiles.IProfilesService;
import se.kth.myseries.server.auth.IConnector.UserID;
import se.kth.myseries.server.auth.UserControlHelper;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.server.persistence.UsersDAO;
import se.kth.myseries.server.persistence.entity.MySeriesUser;
import se.kth.myseries.shared.dto.StatisticsDTO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * Implementation of the Profiles service. Mostly a façade for Facebook
 * IConnector
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class ProfilesServiceImpl extends RemoteServiceServlet implements
IProfilesService {
	
	private static final long		serialVersionUID	= 1L;
	
	private static final Logger		log					= Logger.getLogger(ProfilesServiceImpl.class
			.getName());
	
	private final UserControlHelper	userHelper			= new UserControlHelper();
	
	@Override
	@Cacheable
	public UserDTO getUserInfo(final String signed_request)
	throws MySeriesFacebookException, DatabaseException {
		
		UsersDAO dao = new UsersDAO();
		
		try {
			UserDTO dto = userHelper.getUserInfo(signed_request);
			dao.save(dto);
			return dto;
		} catch (MySeriesFacebookException e) {
			throw e;
		} catch (Exception e) {
			// prevents the app from breaking in case of exception
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			dao.close();
		}
	}
	
	
	@Override
	@Cacheable
	public UserDTO getUserInfoByFacebookID(final String facebook_id)
	throws DatabaseException {
		
		UsersDAO dao = new UsersDAO();
		
		try {
			MySeriesUser user = dao.getByFacebookID(facebook_id);
			return dao.convert(user);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			dao.close();
		}
	}
	
	@Override
	public List<UserDTO> getFriendsThatUseTheApp(final String signed_request)
	throws SocialNetworkException {
		return this.getFriendsThatUseTheApp(signed_request,
				userHelper.getUserInfo(signed_request).facebook_id);
	}
	
	@Override
	public List<UserDTO> getFriendsThatUseTheApp(final String signed_request,
			final String facebookID) throws SocialNetworkException {
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		{
			
			List<UserID> friends = userHelper.getConnector(signed_request)
			.getFriendsThatUseTheApp(facebookID);
			
			for (UserID s : friends) {
				UserDTO dto = new UserDTO();
				dto.facebook_id = Long.toString(s.uid);
				dto.pictureURL = "http://graph.facebook.com/" + s.uid
				+ "/picture";
				dtos.add(dto);
			}
		}
		
		log.fine("Returned " + dtos.size() + " friends that also use the app");
		return dtos;
	}
	
	@Override
	public StatisticsDTO getStatistics(final String signed_request)
	throws SocialNetworkException, DatabaseException {
		return getStatistics(userHelper.getUserID(signed_request));
	}
	
	@Override
	public StatisticsDTO getStatisticsByFacebookID(final String facebook_id)
	throws DatabaseException {
		
		UsersDAO uDao = new UsersDAO();
		try {
			MySeriesUser user = uDao.getByFacebookID(facebook_id);
			return getStatistics(user);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
		}
	}
	
	@Override
	public StatisticsDTO getStatistics(final Long userID)
	throws DatabaseException {
		
		UsersDAO uDao = new UsersDAO();
		try {
			MySeriesUser user = uDao.getByID(userID);
			return getStatistics(user);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new DatabaseException(e.toString());
		} finally {
			uDao.close();
		}
		
	}
	
	public StatisticsDTO getStatistics(final MySeriesUser user) {
		
		StatisticsDTO dto = new StatisticsDTO();
		
		dto.registeredOn = user.getInstallDate();
		dto.nbrSeriesOnWatchlist = user.getWatchlist() == null ? 0 : user
				.getWatchlist().size();
		dto.nbrSeriesOnOldWatchlist = user.getOldWatchlist() == null ? 0 : user
				.getOldWatchlist().size();
		dto.nbrEpisodesWatched = user.getEpisodesWatched() == null ? 0 : user
				.getEpisodesWatched().size();
		
		// TODO time spend in front of the computer
		
		return dto;
		
	}
}
