package net.backlogic.persistence.client;

import java.util.HashMap;
import java.util.Map;

import net.backlogic.persistence.client.annotation.CommandService;
import net.backlogic.persistence.client.annotation.QueryService;
import net.backlogic.persistence.client.annotation.RepositoryService;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.proxy.PersistenceProxy;
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

    /**
     * Get a proxy object for a query interface type
     *
     * @param queryType the interface type
     * @return return a proxy for given interface type. Return null if not exists.
     */
    public Object getQuery(Class<?> queryType) {
        Object proxy = proxyCache.get(queryType);
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
    public Object getCommand(Class<?> commandType) {
        Object proxy = proxyCache.get(commandType);
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
    public Object getRepository(Class<?> repositoryType) {
        Object proxy = proxyCache.get(repositoryType);
        if (proxy == null) {
            proxy = proxyFactory.createRepository(repositoryType);
            this.proxyCache.put(repositoryType, proxy);
        }
        return proxy;
    }


    public Object getBatch(Class<?> batchType) {
		// create proxy
		Object proxy = proxyFactory.createBatch(batchType);
        return proxy;    	
    }

    
    private Object createProxy(Class<?> interfaceType) {
    	Object proxy;
        if ( interfaceType.getAnnotation(QueryService.class) != null ) {
        	proxy =  proxyFactory.createQuery(interfaceType);
        } else if (interfaceType.getAnnotation(CommandService.class) != null) {
        	proxy =  proxyFactory.createCommand(interfaceType);        	
	    } else if (interfaceType.getAnnotation(RepositoryService.class) != null) {
	    	proxy =  proxyFactory.createRepository(interfaceType);        	
	    } else {
	    	throw new DataAccessException("InvalidAccessInterface", "Invalid data access interface: " + interfaceType.getName());
	    }
        return proxy;
    }

}
