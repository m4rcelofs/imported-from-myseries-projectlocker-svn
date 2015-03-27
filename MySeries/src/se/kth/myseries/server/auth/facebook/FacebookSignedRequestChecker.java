package se.kth.myseries.server.auth.facebook;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import se.kth.myseries.server.ServerConstants;
import se.kth.myseries.server.cache.Cacheable;
import se.kth.myseries.shared.exceptions.AppNotAuthorizedException;
import se.kth.myseries.shared.exceptions.NotOnFacebookException;

import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookJsonMappingException;
import com.restfb.JsonMapper;

/**
 * MySeries <br>
 * Checks if the Signed_request parameter received from Facebook is valid, and
 * the user is authentic
 * 
 * @author Marcelo
 * @since 01/11/2010
 */
public class FacebookSignedRequestChecker {
	
	private static final Logger	log	= Logger.getLogger(FacebookSignedRequestChecker.class
			.getName());
	
	@Cacheable
	public FacebookAuth checkRequest(final String signedRequest)
	throws IllegalArgumentException, NotOnFacebookException,
	AppNotAuthorizedException {
		
		if (signedRequest == null || signedRequest.isEmpty()) {
			log.warning("Application is not being run from inside Facebook");
			throw new NotOnFacebookException();
		}
		
		/* split the string into signature and payload */
		String payload;
		String rawpayload;
		byte[] signature;
		
		int index = signedRequest.indexOf(".");
		String sig = signedRequest.substring(0, index);
		signature = new Base64(true).decode(sig.getBytes());
		rawpayload = signedRequest.substring(index + 1);
		byte[] pl = new Base64(true).decode(rawpayload.getBytes());
		payload = new String(pl);
		
		FacebookAuth req;
		try {
			/* parse the JSON payload and do the signature check */
			JsonMapper mapper = new DefaultJsonMapper();
			req = mapper.toJavaObject(payload, FacebookAuth.class);
		} catch (FacebookJsonMappingException e) {
			log.warning(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		
		if (req.oauth_token == null || req.oauth_token.isEmpty()) {
			log.info("User is not logged in or hasn't authorized the app");
			throw new AppNotAuthorizedException();
		}
		
		if (!req.algorithm.equals("HMAC-SHA256")) {
			log.warning("Unexpected hash algorithm " + req.algorithm);
			throw new IllegalArgumentException("Unexpected hash algorithm "
					+ req.algorithm);
		}
		try {
			/* then check the signature */
			checkSignature(rawpayload, signature);
		} catch (IllegalArgumentException e) {
			log.warning(e.getMessage());
			throw new IllegalArgumentException(
			"Could not check Facebook's signature");
		}
		return req;
	}
	
	/**
	 * @param rawpayload
	 * @param signature
	 */
	private void checkSignature(final String rawpayload, final byte[] signature)
	throws IllegalArgumentException {
		
		String secret = ServerConstants.FB_SECRET;
		
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
					ServerConstants.FB_SIGN_ALGORITHM);
			Mac mac = Mac.getInstance(ServerConstants.FB_SIGN_ALGORITHM);
			mac.init(secretKeySpec);
			byte[] mysig = mac.doFinal(rawpayload.getBytes());
			if (!Arrays.equals(mysig, signature))
				throw new IllegalArgumentException(
				"Non-matching signature for request");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Unknown hash algorithm "
					+ ServerConstants.FB_SIGN_ALGORITHM, e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException("Wrong key for "
					+ ServerConstants.FB_SIGN_ALGORITHM, e);
		}
	}
}
