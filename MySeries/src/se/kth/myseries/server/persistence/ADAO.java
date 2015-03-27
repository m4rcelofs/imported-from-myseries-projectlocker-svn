package se.kth.myseries.server.persistence;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

/**
 * MySeries <br>
 * Abstract Data Access Object class
 * 
 * @author Marcelo
 * @since 10/11/2010
 */
public abstract class ADAO {
	
	protected final PersistenceManager	pm	= PMF.getManager();
	
	protected final Logger				log	= Logger.getLogger(this.getClass()
													.getName());

	public ADAO() {}
	
	protected void save(final Object o) {
		pm.makePersistent(o);
	}
	
	/**
	 * Closes this DAO connection
	 */
	public void close() {
		pm.close();
	}
	
}
