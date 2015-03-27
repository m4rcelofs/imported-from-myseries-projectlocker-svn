package se.kth.myseries.server.series.tvdb;

import java.net.URL;
import java.util.logging.Logger;

import se.kth.myseries.server.persistence.entity.Image;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * MySeries <br>
 * Handles the images used
 * 
 * @author Marcelo
 * @since 08/11/2010
 */
public class ImagesHelper {
	
	private static final Logger	log	= Logger.getLogger(ImagesHelper.class
			.getName());
	
	/**
	 * Downloads an image <br />
	 * Limit is 1MB, larger images will fail
	 * 
	 * @param url
	 * @return
	 */
	public Image downloadImage(final String url, final boolean createThumbnail) {
		
		URLFetchService fetchService = URLFetchServiceFactory
		.getURLFetchService();
		
		HTTPResponse fetchResponse = null;
		
		try {
			fetchResponse = fetchService.fetch(new URL(url));
			
			Blob data = new Blob(fetchResponse.getContent());
			Blob thumbnail = null;
			if (createThumbnail)
				thumbnail = getThumbnail(data.getBytes());
			
			return new Image(data, thumbnail, url,
					getContentType(fetchResponse));
			
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * @param data
	 * @return
	 */
	private Blob getThumbnail(final byte[] data) {
		
		com.google.appengine.api.images.Image old = ImagesServiceFactory
		.makeImage(data);
		Transform resize = ImagesServiceFactory.makeResize(
				(int) (old.getWidth() * 0.3), (int) (old.getHeight() * 0.3));
		
		com.google.appengine.api.images.Image newI = ImagesServiceFactory
		.getImagesService().applyTransform(resize, old);
		
		return new Blob(newI.getImageData());
		
	}
	
	/**
	 * @param fetchResponse
	 * @return
	 */
	private String getContentType(final HTTPResponse fetchResponse) {
		for (HTTPHeader header : fetchResponse.getHeaders())
			if (header.getName().equalsIgnoreCase("content-type"))
				return header.getValue();
		
		return "image/jpeg"; // just guessing
	}
}