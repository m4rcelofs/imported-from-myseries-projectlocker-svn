package se.kth.myseries.server.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import se.kth.myseries.server.persistence.entity.Serie;
import se.kth.myseries.shared.dto.SerieDTO;

/**
 * MySeries <br>
 * Data Access Object for the Series entity
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class SeriesDAO {
	
	public List<SerieDTO> getAllSeries() {
		// TODO not good, optimize after deciding how interface will be
		
		ArrayList<SerieDTO> dtos = new ArrayList<SerieDTO>();
		
		PersistenceManager pm = PMF.getManager();
		try {
			Extent<Serie> extent = pm.getExtent(Serie.class, false);
			
			for (Serie s : extent)
				dtos.add(new SerieDTO(s.getId(), s.getName(), s
						.getDescription().toString(), s.getNumberOfSeasons(), s
						.getNumberOfEpisodes()));
		} finally {
			pm.close();
		}
		
		return dtos;
		
	}
	
	public void deleteSerie(final SerieDTO serie) {
		PersistenceManager pm = PMF.getManager();
		
		try {
			Serie s = pm.getObjectById(Serie.class, serie.id);
			pm.deletePersistent(s);
		} finally {
			pm.close();
		}
	}
}
