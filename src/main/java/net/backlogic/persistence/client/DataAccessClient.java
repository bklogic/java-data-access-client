package net.backlogic.persistence.client;

import net.backlogic.persistence.client.auth.JwtProvider;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.proxy.ProxyFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The main class of data access client.
 */
public class DataAccessClient {
	/*
	 * proxy proxyFactory
	 */
	private final ProxyFactory proxyFactory;
	/*
	 * Proxy cache. Key=interface class, value = interface proxy
	 */
	private Map<Class<?>, Object> proxyCache;

	/**
	 * Construct data access client, which shall be done by calling
	 * DataAccessClientBuilder.build()
	 * 
	 * @param baseUrl	baseUrl for data access services
	 */
	public DataAccessClient(String baseUrl) {
		this.proxyFactory = new ProxyFactory(baseUrl);
		this.proxyCache = new HashMap<Class<?>, Object>();
	}

	
	/**
	 * Construct data access client, which shall be done by calling
	 * DataAccessClientBuilder.build()
	 * 
	 * @param serviceHandler	the ServiceHandler for data access services
	 */
	public DataAccessClient(ServiceHandler serviceHandler) {
		this.proxyFactory = new ProxyFactory(serviceHandler);
		this.proxyCache = new HashMap<Class<?>, Object>();
	}

	/**
	 * Get an instance of DataAccessClientBuilder.
	 * @return	an instance of DataAccessClientBuilder
	 */
	public static DataAccessClientBuilder builder() {
		return new DataAccessClientBuilder();
	}

	/**
	 * Set the JWT provider for data access client. If configured, data access client uses this provider
	 * to acquire a BEARER token for service authentication.
	 * This client has an implementation of JwtProvider in DefaultJwtProvider based on DefaultJwtCredential.
	 * @param jwtProvider	the JWT provide
	 */
	public void setJwtProvider(JwtProvider jwtProvider) {
		this.proxyFactory.setJwtProvider(jwtProvider);
	}

	/**
	 * Set the logRequest property for data access client for enabling or disabling request logging.
	 * @param logRequest	log request and response if true. Default false.
	 */
	public void logRequest(boolean logRequest) {
		this.proxyFactory.logRequest(logRequest);
	}

	/**
	 * Get a proxy object for a query interface type
	 *
	 * @param queryType the interface type
	 * @return	a proxy for given interface type. Throws DataAccessException if not annotated properly.
	 */
	public <T> T getQuery(Class<T> queryType) {
		@SuppressWarnings("unchecked")
		T proxy = (T) proxyCache.get(queryType);
		if (proxy == null) {
			proxy = proxyFactory.createQuery(queryType);
			this.proxyCache.put(queryType, proxy);
		}
		return proxy;
	}

	/**
	 * Get a proxy object for a command interface type
	 *
	 * @param commandType the interface type
	 * @return	a proxy for given interface type. Throws DataAccessException if not annotated properly.
	 */
	public <T> T getCommand(Class<T> commandType) {
		@SuppressWarnings("unchecked")
		T proxy = (T) proxyCache.get(commandType);
		if (proxy == null) {
			proxy = proxyFactory.createCommand(commandType);
			this.proxyCache.put(commandType, proxy);
		}
		return proxy;
	}

	/**
	 * Get a proxy object for a repository interface type
	 *
	 * @param repositoryType the interface type
	 * @return	proxy for given interface type. Throws DataAccessException if not annotated properly.
	 */
	public <T> T getRepository(Class<T> repositoryType) {
		@SuppressWarnings("unchecked")
		T proxy = (T) proxyCache.get(repositoryType);
		if (proxy == null) {
			proxy = proxyFactory.createRepository(repositoryType);
			this.proxyCache.put(repositoryType, proxy);
		}
		return proxy;
	}

	/**
	 * Get a proxy object for a batch interface
	 * @param batchType the interface type
	 * @return	proxy for given interface type. Throws DataAccessException if not annotated properly.
	 * @param <T>
	 */
	public <T> T getBatch(Class<T> batchType) {
		// create proxy
		T proxy = proxyFactory.createBatch(batchType);
		return proxy;
	}

}
