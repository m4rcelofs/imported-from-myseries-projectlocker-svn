package se.kth.myseries.server.series;

import java.util.List;

import se.kth.myseries.client.series.ISeriesService;
import se.kth.myseries.server.persistence.SeriesDAO;
import se.kth.myseries.shared.dto.SerieDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * MySeries <br>
 * Server side implementation of ISeriesService
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeriesServiceImpl extends RemoteServiceServlet implements
ISeriesService {
	
	private static final long	serialVersionUID	= 1L;
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.series.ISeriesService#getSeries()
	 */
	@Override
	public List<SerieDTO> getSeries() throws IllegalArgumentException {
		
		// FakeDataCreator.createFakeData(); // TODO remove later
		
		return new SeriesDAO().getAllSeries();
	}
	
}
