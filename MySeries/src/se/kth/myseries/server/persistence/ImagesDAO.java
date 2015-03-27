package se.kth.myseries.server.persistence;

import se.kth.myseries.server.persistence.entity.Image;

/**
 * MySeries <br>
 * @author Marcelo
 * @since  10/11/2010
 */
public class ImagesDAO extends ADAO {
	
	
	/**
	 * @param image
	 */
	public void save(final Image image) {
		pm.makePersistent(image);
	}

}
