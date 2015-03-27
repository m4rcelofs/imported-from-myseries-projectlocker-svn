package se.kth.myseries.server.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MySeries <br>
 * Defines a method as cacheable; will have cached results and
 * will be called only if result is not in the cache. <br>
 * <ul>
 * <li>Cannot cache static methods
 * <li>memcache accepts only returning objects that implement Serializable
 * </ul>
 * 
 * @author Marcelo
 * @since 31/10/2010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {
	
}
