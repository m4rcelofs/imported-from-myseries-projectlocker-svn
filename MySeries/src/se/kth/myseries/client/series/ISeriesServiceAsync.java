package se.kth.myseries.client.series;

import java.util.List;
import java.util.Set;

import se.kth.myseries.shared.dto.CommentsDTO;
import se.kth.myseries.shared.dto.EpisodeDTO;
import se.kth.myseries.shared.dto.RankDTO;
import se.kth.myseries.shared.dto.SeasonDTO;
import se.kth.myseries.shared.dto.SeriesDTO;
import se.kth.myseries.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * Asynchronous version of ISeriesService
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public interface ISeriesServiceAsync {
	
	void getTopSeriesNames(AsyncCallback<List<SeriesDTO>> callback);
	
	void getSeriesTheTvDB(String tvdbID, String signed_request,
			AsyncCallback<SeriesDTO> callback);
	
	void getSeries(Long seriesID, String signed_request,
			AsyncCallback<SeriesDTO> callback);
	
	void getWatchers(Long seriesID, AsyncCallback<Set<UserDTO>> callback);
	
	void getSeason(Long seriesID, Integer seasonNbr,
			AsyncCallback<SeasonDTO> callback);
	
	void getEpisode(Long seriesID, Long seasonID, Long episodeID,
			AsyncCallback<EpisodeDTO> callback);
	
	void getWatchers(Long seriesID, Long seasonID, Long episodeID,
			AsyncCallback<Set<UserDTO>> callback);
	
	void search(String seriesName, AsyncCallback<List<SeriesDTO>> callback);
	
	void getTopSeriesNames(String signed_request,
			AsyncCallback<List<SeriesDTO>> callback);
	
	void getComments(Long seriesID, Integer pageNumber,
			AsyncCallback<CommentsDTO> callback);
	
	void getComments(Long seriesID, Long seasonID, Long episodeID,
			Integer pageNumber, AsyncCallback<CommentsDTO> callback);
	
	void addComment(String signedRequest, Long seriesID, String comment,
			AsyncCallback<Void> callback);
	
	void addComment(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID, String comment, AsyncCallback<Void> callback);
	
	void addRank(String signedRequest, Long seriesID, Double rankNum,
			AsyncCallback<Void> callback);
	
	void addRank(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID, Double rankNum, AsyncCallback<Void> callback);
	
	void getRanks(String signedRequest, Long seriesID,
			AsyncCallback<RankDTO> callback);
	
	void getRanks(String signedRequest, Long seriesID, Long seasonID,
			Long episodeID, AsyncCallback<RankDTO> callback);
	
}
