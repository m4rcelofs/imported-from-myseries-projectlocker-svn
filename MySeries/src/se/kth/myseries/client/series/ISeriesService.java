package se.kth.myseries.client.series;

import java.util.List;
import java.util.Set;

import se.kth.myseries.shared.dto.CommentsDTO;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.RankDTO;
import se.kth.myseries.shared.dto.SeasonDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;
import se.kth.myseries.shared.exceptions.DatabaseException;
import se.kth.myseries.shared.exceptions.ImportingInProgressException;
import se.kth.myseries.shared.exceptions.MySeriesFacebookException;
import se.kth.myseries.shared.exceptions.SocialNetworkException;
import se.kth.myseries.shared.exceptions.TvDbException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * MySeries <br>
 * Client side stub for the Series tab
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
@RemoteServiceRelativePath("series")
public interface ISeriesService extends RemoteService {
	
	/**
	 * Returns the complete information about the series with the given ID
	 * 
	 * @param seriesID
	 * @return
	 * @throws MySeriesFacebookException
	 * @throws DatabaseException
	 */
	public SeriesDTO getSeries(Long seriesID, String signed_request)
			throws SocialNetworkException, DatabaseException;
	
	/**
	 * Returns the information for a given season of a series
	 * 
	 * @param seriesID
	 * @param seasonNbr
	 * @return
	 */
	public SeasonDTO getSeason(Long seriesID, Integer seasonNbr);
	
	/**
	 * Returns the information for a given episode from a specific season
	 * 
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @return
	 */
	public EpisodeDTO getEpisode(Long seriesID, Long seasonID, Long episodeID);
	
	/**
	 * Returns the complete information about the series with the given ID. <br />
	 * Uses TheTVDB ID to find it.
	 * 
	 * @param tvdbID
	 * @return
	 * @throws MySeriesFacebookException
	 * @throws DatabaseException
	 * @throws ImportingInProgressException
	 * @throws SocialNetworkException
	 */
	public SeriesDTO getSeriesTheTvDB(String tvdbID, String signed_request)
	throws DatabaseException, ImportingInProgressException,
	SocialNetworkException;
	
	/**
	 * Returns all users that currently watch this series
	 * 
	 * @param seriesID
	 * @return DTOs with only ID, name and picURL fields
	 */
	public Set<UserDTO> getWatchers(Long seriesID);
	
	/**
	 * Returns all users that currently watch this series
	 * 
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @return DTOs with only ID, name and picURL fields
	 */
	public Set<UserDTO> getWatchers(Long seriesID, Long seasonID, Long episodeID);
	
	/**
	 * Returns incomplete DTOs (with only Name, id and picture filled) of the
	 * top series, to be used on the drag'n'drop watchlist widget. <br />
	 * The goal is to improve the user experience, instead of having 23k series
	 * load on a single widget.
	 * 
	 * @return
	 */
	public List<SeriesDTO> getTopSeriesNames();
	
	/**
	 * Returns incomplete DTOs (with only Name, id and picture filled) of the
	 * top series <b>that are not currently on the user's watchlist</b>, to be
	 * used on the drag'n'drop watchlist widget. <br />
	 * 
	 * @param signed_request
	 * @return
	 * @throws MySeriesFacebookException
	 */
	public List<SeriesDTO> getTopSeriesNames(String signed_request)
			throws SocialNetworkException;
	
	/**
	 * Returns incomplete DTOs (with only Name, tvdbID and picture filled) of
	 * the top series that match this search
	 * 
	 * @param seriesName
	 * @return
	 * @throws TvDbException
	 */
	public List<SeriesDTO> search(String seriesName) throws TvDbException;
	
	/**
	 * Gets the comments available for a series
	 * 
	 * @param seriesID
	 * @param pageNumber
	 *            Start by 1, and fetch the rest only if needed
	 * @return
	 */
	public CommentsDTO getComments(Long seriesID, Integer pageNumber);
	
	/**
	 * Gets the comments available for an episode
	 * 
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @param pageNumber
	 *            Start by 1, and fetch the rest only if needed
	 * @return
	 */
	public CommentsDTO getComments(Long seriesID, Long seasonID,
			Long episodeID, Integer pageNumber);
	
	/**
	 * Adds a comment to some series
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @param comment
	 */
	public void addComment(String signedRequest, Long seriesID, String comment);
	
	/**
	 * Adds a comment to some episode
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @param comment
	 */
	public void addComment(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID, String comment);
	
	/**
	 * Adds a ranking for a series, or updates it if the user has already ranked
	 * it
	 * before
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @param rankNum
	 */
	public void addRank(String signedRequest, Long seriesID, Double rankNum);
	
	/**
	 * Adds a ranking for an episode, or updates it if the user has already
	 * ranked it
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @param rankNum
	 */
	public void addRank(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID, Double rankNum);
	
	/**
	 * Gets the ranking for a series, from a user and his friends
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @return
	 */
	public RankDTO getRanks(String signedRequest, Long seriesID);
	
	/**
	 * Gets the ranking for an episode, from a user and his friends
	 * 
	 * @param signedRequest
	 * @param seriesID
	 * @param seasonID
	 * @param episodeID
	 * @return
	 */
	public RankDTO getRanks(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID);
	
}
