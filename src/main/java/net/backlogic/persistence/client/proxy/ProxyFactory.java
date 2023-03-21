package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.annotation.BatchService;
import net.backlogic.persistence.client.annotation.CommandService;
import net.backlogic.persistence.client.annotation.QueryService;
import net.backlogic.persistence.client.annotation.RepositoryService;
import net.backlogic.persistence.client.handler.DefaultServiceHandler;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ReturnType;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.handler.TypeUtil;

/**
 * Responsible for generating persistence proxy from interface
 */
public class ProxyFactory {
	private static final String BATCH_URL = "/batch/batch";
	
    // service handler
    private final ServiceHandler serviceHandler;
    private final JsonHandler jsonHandler;
    private Map<Class<?>, ServiceMethodFinder> finderMap;

    /*
     * Construct proxy generator
     */
    public ProxyFactory(String baseUrl) {
        this.jsonHandler = new JsonHandler();
        this.serviceHandler = new DefaultServiceHandler(baseUrl, this.jsonHandler);
        init();
    }

    /*
     * Construct proxy generator with mock service handler
     */
    public ProxyFactory(ServiceHandler serviceHandler) {
        this.jsonHandler = new JsonHandler();
        this.serviceHandler = serviceHandler;
        init();
    }

    private void init() {
        this.finderMap = new HashMap<>();
        this.finderMap.put(QueryService.class, new QueryServiceMethodFinder());
        this.finderMap.put(CommandService.class, new CommandServiceMethodFinder());
        this.finderMap.put(RepositoryService.class, new RepositoryServiceMethodFinder());    	
        this.finderMap.put(BatchService.class, new BatchServiceMethodFinder());    	
    }
    
    public Object createRepository(Class<?> repositoryType) {
        //validate repository interface
        RepositoryService repositoryAnnotation = repositoryType.getAnnotation(RepositoryService.class);
        if (repositoryAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, repositoryType.getName() + " is not repository interface");
        }

        // build service map
        String interfaceUrl = repositoryAnnotation.value();
        ServiceMethodFinder finder = this.finderMap.get(RepositoryService.class);
    	Class<?> T = this.getGenericType(repositoryType);
        Map<String, ServiceMethod> serviceMap = buildServiceMap(interfaceUrl, repositoryType.getMethods(), finder, T);
        
        //instantiate repository interface proxy
        Object proxy = Proxy.newProxyInstance(
                repositoryType.getClassLoader(), new Class[]{repositoryType}, new PersistenceProxy(serviceHandler, serviceMap)
        );

        return proxy;
    }


    public Object createQuery(Class<?> queryType) {
        //validate repository interface
        QueryService queryAnnotation = queryType.getAnnotation(QueryService.class);
        if (queryAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, queryType.getName() + " is not query interface");
        }

        // build service map
        String interfaceUrl = queryAnnotation.value();
        ServiceMethodFinder finder = this.finderMap.get(QueryService.class);
        Map<String, ServiceMethod> serviceMap = buildServiceMap(interfaceUrl, queryType.getMethods(), finder);
        
        // instantiate query interface proxy
        Object proxy = Proxy.newProxyInstance(
                queryType.getClassLoader(), new Class[]{queryType}, new PersistenceProxy(serviceHandler, serviceMap)
        );

        return proxy;
    }

    public Object createCommand(Class<?> commandType) {
        // validate repository interface
        CommandService persistAnnotation = commandType.getAnnotation(CommandService.class);
        if (persistAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, commandType.getName() + " is not command interface");
        }

        // build service map
        String interfaceUrl = persistAnnotation.value();
        ServiceMethodFinder finder = this.finderMap.get(CommandService.class);
        Map<String, ServiceMethod> serviceMap = buildServiceMap(interfaceUrl, commandType.getMethods(), finder);
        
        //instantiate command interface proxy
        Object proxy = Proxy.newProxyInstance(
                commandType.getClassLoader(), new Class[]{commandType}, new PersistenceProxy(serviceHandler, serviceMap)
        );

        return proxy;
    }

    
    public Object createBatch(Class<?> batchType) {
        // validate repository interface
        BatchService persistAnnotation = batchType.getAnnotation(BatchService.class);
        if (persistAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, batchType.getName() + " is not batch interface");
        }

        // build service map
        String interfaceUrl = persistAnnotation.value();
        String batchServiceUrl = UrlUtil.getUrl(interfaceUrl, BATCH_URL);
        ServiceMethodFinder finder = this.finderMap.get(BatchService.class);
        Map<String, ServiceMethod> serviceMap = buildServiceMap(null, batchType.getMethods(), finder);
        
        //instantiate command interface proxy
        Object proxy = Proxy.newProxyInstance(
        		batchType.getClassLoader(), new Class[]{batchType}, new BatchProxy(serviceHandler, jsonHandler, serviceMap, batchServiceUrl)
        );

        return proxy;
    }
    
    
    private Map<String, ServiceMethod> buildServiceMap(String interfaceUrl, Method[] methods, ServiceMethodFinder finder) {
    	return buildServiceMap(interfaceUrl, methods, finder, null);
    }
    	
    private Map<String, ServiceMethod> buildServiceMap(String interfaceUrl, Method[] methods, 
    				ServiceMethodFinder finder, Class<?> T) {
    	Map<String, ServiceMethod> map = new HashMap<>();
    	
    	for (Method method : methods) {
    		// find method url
    		String methodUrl = finder.find(method);
    		
    		// service url
    		String serviceUrl;
    		if (methodUrl != null) {
    			serviceUrl = UrlUtil.getUrl(interfaceUrl, methodUrl);
    			serviceUrl = serviceUrl.substring(1); // discard initial "/".
    		} else {
        		// skip, not service method, if url is null 
    			continue; 
    		}
    		
    	    // return type 
    	    ReturnType outputType;
    	    Class<?> elementType;
    	    Class<?> returnType = method.getReturnType();
    	    Type genericReturnType = method.getGenericReturnType();
    	    if (returnType.getName() == "void") {
    	        outputType = ReturnType.VOID;
    	        elementType = Void.class;
    	    } else if (returnType == List.class && genericReturnType instanceof ParameterizedType) {
    	        outputType = ReturnType.LIST;
    	        if (T == null) {
        	        elementType = (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];    	        	    	        	
    	        } else {
    	        	elementType = T;
    	        }
    	    } else if (TypeUtil.isPrimitive(returnType)) {
    	        outputType = ReturnType.VALUE;
    	        elementType = returnType;
    	    } else {
    	        outputType = ReturnType.OBJECT;
    	        if (T == null) {
        	        elementType = returnType;
    	        } else {
    	        	elementType = T;
    	        }
    	    }
    	    
    		// service method
    	    ServiceMethod serviceMethod = new ServiceMethod(serviceUrl, outputType, elementType);
    	    map.put(method.getName(), serviceMethod);
    	}
    	
    	return map;
    }
    

    /*
     * Used to support GenericRepository interface
     */
    private Class<?> getGenericType(Class<?> interfaceType) {
    	Class<?> T = null;
    	Type[] genericInterfaces = interfaceType.getGenericInterfaces();
    	List<Class<?>> types = new ArrayList<>();
    	for (Type genericInterface : genericInterfaces) {
    	    if (genericInterface instanceof ParameterizedType) {
    	        Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
    	        for (Type genericType : genericTypes) {
    	            types.add((Class)genericType);
    	        }
    	    }
    	}
    	if (types.size() > 0) {
    		T = types.get(0);
    	}
    	return T;
    }
    
}
