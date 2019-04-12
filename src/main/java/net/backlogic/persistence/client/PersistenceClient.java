/**
 * 
 */
package net.backlogic.persistence.client;

import java.util.HashMap;
import java.util.Map;

import net.backlogic.persistence.client.handler.ServiceHandler;


public class PersistenceClient {
	/*
	 * proxy generator
	 */
	private ProxyGenerator generator;
	/*
	 * Proxy cache. Key=interface class, value = interface proxy
	 */
	Map<Class<?>, Object> proxyCache = new HashMap<Class<?>, Object>(); 
	
	
	/*
	 * Construct persistence client
	 */
	public PersistenceClient(String baseUrl) {
		this.generator = new ProxyGenerator(baseUrl);
	}

	
	/*
	 * Construct persistence client
	 */
	public PersistenceClient(ServiceHandler serviceHandler) {
		this.generator = new ProxyGenerator(serviceHandler);
	}
	
	
	/**
	 * Get a proxy object for a query interface type 
	 * @param repositoryType	the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getQuery(Class<?> queryType) {
		Object proxy = proxyCache.get(queryType);
		if (proxy == null) {
			proxy = generator.createQuery(queryType);
		}
		return proxy;
	}

	
	/**
	 * Get a proxy object for a command interface type 
	 * @param repositoryType  the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getCommand(Class<?> commandType) {
		Object proxy = proxyCache.get(commandType);
		if (proxy == null) {
			proxy = generator.createCommand(commandType);
		}
		return proxy;
	}
			
	
	/**
	 * Get a proxy object for a repository interface type 
	 * @param repositoryType	the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getRepository(Class<?> repositoryType) {
		Object proxy = proxyCache.get(repositoryType);
		if (proxy == null) {
			proxy = generator.createRepository(repositoryType);
		}
		return proxy;
	}

}
