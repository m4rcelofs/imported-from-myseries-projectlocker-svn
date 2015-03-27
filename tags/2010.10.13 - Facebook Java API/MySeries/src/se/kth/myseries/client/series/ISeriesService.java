package se.kth.myseries.client.series;

import java.util.List;

import se.kth.myseries.shared.dto.SerieDTO;

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
	public List<SerieDTO> getSeries() throws IllegalArgumentException;
}
