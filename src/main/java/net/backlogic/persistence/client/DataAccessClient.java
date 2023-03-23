package net.backlogic.persistence.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.proxy.ProxyFactory;


public class DataAccessClient {
    /*
     * proxy proxyFactory
     */
    private final ProxyFactory proxyFactory;
    /*
     * Proxy cache. Key=interface class, value = interface proxy
     */
    Map<Class<?>, Object> proxyCache;

    /*
     * Construct persistence client
     */
    public DataAccessClient(String baseUrl) {
        this.proxyFactory = new ProxyFactory(baseUrl);
        this.proxyCache = new HashMap<Class<?>, Object>();
    }

    /*
     * Construct persistence client
     */
    public DataAccessClient(ServiceHandler serviceHandler) {
        this.proxyFactory = new ProxyFactory(serviceHandler);
        this.proxyCache = new HashMap<Class<?>, Object>();
    }
    
    public static DataAccessClientBuilder builder() {
    	return new DataAccessClientBuilder();
    }
    
    public void setJwtProvider(Supplier<String> jwtProvider) {
    	this.proxyFactory.setJwtProvider(jwtProvider);
    }    

    public void logRequest(boolean logRequest) {
    	this.proxyFactory.logRequest(logRequest);
    }

    /**
     * Get a proxy object for a query interface type
     *
     * @param queryType the interface type
     * @return return a proxy for given interface type. Return null if not exists.
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
     * @return return a proxy for given interface type. Return null if not exists.
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
     * @return return a proxy for given interface type. Return null if not exists.
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


    public <T> T getBatch(Class<T> batchType) {
		// create proxy
		T proxy = proxyFactory.createBatch(batchType);
        return proxy;    	
    }

}
