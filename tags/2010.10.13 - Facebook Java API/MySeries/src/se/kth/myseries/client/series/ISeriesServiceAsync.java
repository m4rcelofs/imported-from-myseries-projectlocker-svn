package se.kth.myseries.client.series;

import java.util.List;

import se.kth.myseries.shared.dto.SerieDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * MySeries <br>
 * Asynchronous version of ISeriesService
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public interface ISeriesServiceAsync {
	
	public void getSeries(AsyncCallback<List<SerieDTO>> callback)
	throws IllegalArgumentException;
}
