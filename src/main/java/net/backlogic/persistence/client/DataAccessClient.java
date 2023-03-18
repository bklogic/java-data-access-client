package net.backlogic.persistence.client;

import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.proxy.ProxyFactory;

import java.util.HashMap;
import java.util.Map;


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

}
