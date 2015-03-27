package se.kth.myseries.server.persistence;

/**
 * MySeries <br>
 * Persistence Manager Factory
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory	pmfInstance	= JDOHelper
	.getPersistenceManagerFactory("transactions-optional");
	
	private PMF() {}
	
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
	
	public static PersistenceManager getManager() {
		return pmfInstance.getPersistenceManager();
	}
}
