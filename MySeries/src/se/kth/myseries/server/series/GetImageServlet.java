package se.kth.myseries.server.series;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.kth.myseries.server.persistence.PMF;
import se.kth.myseries.server.persistence.entity.Image;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * MySeries <br>
 * Returns series images, such as banners or posters <br />
 * Parameters:
 * <ul>
 * <li>id = image id, integer
 * <li>thumb = '1' to return image thumbnail, if it exists
 * </ul>
 * 
 * @author Marcelo
 * @since 12/11/2010
 */
public class GetImageServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;
	
	private static final Logger	log					= Logger.getLogger(GetImageServlet.class
			.getName());
	
	private PersistenceManager	pm;
	private long				expires;
	
	@Override
	public void init() {
		this.pm = PMF.getManager();
		this.expires = Long.valueOf(getInitParameter("expires"));
	}
	
	@Override
	public void doGet(final HttpServletRequest req,
			final HttpServletResponse res) throws IOException {
		
		String id = req.getParameter("id");
		boolean thumb50 = req.getParameter("thumb") != null
		&& "1".equals(req.getParameter("thumb")) ? true : false;
		
		if (id == null || id.isEmpty()) {
			res.sendRedirect("/images/404kitten.jpg");
			return;
		}
		
		try {
			
			Image image = getImage(id);
			
			if (image == null) {
				res.sendRedirect("/images/404kitten.jpg");
				return;
			}
			
			try {
				res.setDateHeader("Expires", (new Date().getTime() + expires));
				res.setContentType(image.getContentType());
				
				Blob data = thumb50 && image.getThumbnail() != null ? image
						.getThumbnail() : image.getData();
						
						res.getOutputStream().write(data.getBytes());
			} catch (Exception e) {
				log.warning("Error writing image, redirecting to TheTvDb: "
						+ e.toString());
				res.sendRedirect(image.getOriginalUrl());
			}
			
		} catch (Exception e) {
			log.severe(e.toString());
		}
	}
	
	private Image getImage(final String id) {
		try {
			Key key = KeyFactory.createKey(Image.class.getSimpleName(),
					Long.valueOf(id));
			return pm.getObjectById(Image.class, key);
		} catch (JDOObjectNotFoundException ignore) {
			log.info(ignore.toString());
		}
		
		return null;
	}
	
	@Override
	public long getLastModified(final HttpServletRequest req) {
		// never modified, always cache images on client
		return 1;
	}
	
}
