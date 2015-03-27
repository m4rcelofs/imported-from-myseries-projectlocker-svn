package se.kth.myseries.server.cache;

import java.util.Collections;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import se.kth.myseries.server.ServerConstants;

/**
 * Aspect for handling cacheable methods.
 * 
 * @author http://urmincek.wordpress.com/2009/09/06/simple-caching-with-aspectj/
 * @since 31/10/2010
 */
@Aspect
public class CacheAspect {
	
	private final Logger	logger	= Logger.getLogger(CacheAspect.class
			.getName());
	private Cache			cache;
	
	public CacheAspect() {
		
		CacheManager manager = CacheManager.getInstance();
		
		try { // TODO google doesn't use manager?
			if (manager.getCache(ServerConstants.CACHE_NAME) == null) {
				logger.fine("Cache is null, creating one");
				cache = manager.getCacheFactory().createCache(
						Collections.EMPTY_MAP);
				manager.registerCache(ServerConstants.CACHE_NAME, cache);
			}
		} catch (CacheException e) {
			logger.severe(e.getMessage());
		}
	}
	
	/**
	 * Pointcut for all methods annotated with <code>@Cacheable</code>
	 */
	@Pointcut("execution(@Cacheable * *.*(..))")
	@SuppressWarnings("unused")
	private void cache() {}
	
	@SuppressWarnings("unchecked")
	@Around("cache()")
	public Object aroundCachedMethods(final ProceedingJoinPoint thisJoinPoint)
	throws Throwable {
		
		if (ServerConstants.CACHE_ENABLED) {
			logger.fine("Execution of Cacheable method catched");
			
			String key = createKey(thisJoinPoint);
			logger.fine("Key = " + key);
			
			Object result;
			if (!cache.containsKey(key)) {
				logger.fine("Result not yet cached. Must be calculated...");
				result = thisJoinPoint.proceed();
				logger.fine("Storing calculated value '" + result
						+ "' to cache");
				cache.put(key, result);
			} else {
				result = cache.get(key);
				logger.fine("Result '" + result + "' was found in cache");
			}
			
			return result;
		} else
			return thisJoinPoint.proceed();
	}
	
	private String createKey(final ProceedingJoinPoint thisJoinPoint) {
		// generate the key under which cached value is stored
		// will look like caching.aspectj.Calculator.sum(Integer=1;Integer=2;)
		StringBuilder keyBuff = new StringBuilder();
		
		// append name of the class
		keyBuff.append(thisJoinPoint.getTarget().getClass().getName());
		
		// append name of the method
		keyBuff.append(".").append(thisJoinPoint.getSignature().getName());
		
		keyBuff.append("(");
		// loop through cacheable method arguments
		for (final Object arg : thisJoinPoint.getArgs())
			if (arg != null)
				// append argument type and value
				keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
		keyBuff.append(")");
		
		return keyBuff.toString();
	}
	
}
