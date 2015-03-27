package se.kth.myseries.server.admin;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

/**
 * MySeries <br>
 * Reads a configuration file to get series ids to be imported. <br />
 * Queues one importing task for each id parsed.
 * 
 * @author Marcelo
 * @since 10/11/2010
 */
public class SeriesToImportParserInit extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	private static final Logger	log					= Logger.getLogger(SeriesToImportParserInit.class
			.getName());
	
	private final String		taskUrl				= "/admin/tvdbImport";
	private int					parsedNbr			= 0;
	private Queue				queue;
	
	/**
	 * Tries avoid duplicated tasks on the queue if the init is run more than
	 * once <br / >
	 * It's just supposed to avoid overload, as duplicated series won't be added
	 * to the database anyway
	 */
	private Set<String>			parsed;
	
	@Override
	public void init() {
		
		parsed = new HashSet<String>();
		queue = QueueFactory.getQueue("series-import");
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) {
		
		long init = new Date().getTime();
		log.info("Parsing series to import... ");
		
		boolean importedSomething = false;
		
		try {
			
			importedSomething = parseFile(getInitParameter("filename"));
			
		} catch (Exception e) {
			log.severe(e.toString());
			
		}
		
		if (importedSomething)
			log.info("Parsing and queuing of " + parsedNbr + " ids took "
					+ (new Date().getTime() - init) + "ms");
	}
	
	private boolean parseFile(final String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line = null;
		while ((line = reader.readLine()) != null)
			if (line.startsWith("<!--")) {
				log.warning("Ignoring import list...");
				return false;
			} else
				if (line.startsWith("//"))
					continue;
				else
					if (!line.isEmpty())
						queueImport(line);
		
		return true;
	}
	
	private void queueImport(final String id) {
		
		if (!parsed.contains(id)) {
			parsedNbr++;
			queue.add(url(taskUrl).param("id", id).method(Method.GET));
			parsed.add(id);
		}
		
	}
}
