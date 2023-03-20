/**
 *
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.handler.TypeUtil;

/**
 * Proxy Strategy:
 * One proxy per persistence interface. Thus proxy is shared across threads, and should not contain private property.
 */
public class PersistenceProxy implements InvocationHandler {
    private ServiceHandler serviceHandler;
    private Map<String, ServiceMethod> serviceMap;

    /*
     * Constructor
     */
    public PersistenceProxy(ServiceHandler serviceHandler, Map<String, ServiceMethod> serviceMap) {
        this.serviceHandler = serviceHandler;
        this.serviceMap = serviceMap;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        try {
        	// service method
        	ServiceMethod sm = this.serviceMap.get(m.getName());
        	
            // input
            Object input = getInput(m, args);

            // invoke service
            Object output = this.serviceHandler.invoke(sm.getServiceUrl(), input, sm.getReturnType(), sm.getElementType());

            //return
            return output;
        } catch (Exception e) {
            throw e;
        }
    }

    protected Object getInput(Method method, Object[] args) {
        //get method parameters
        Parameter[] params = method.getParameters();

        // construct input
        Object input;
        if (params.length == 0) { 
        	// no parameter
            input = null;
        } else if (params.length == 1) { 
        	Class<?> paramType = method.getParameterTypes()[0];
        	if ( TypeUtil.isPrimitive(paramType) ) {
                input = this.createInputMap(params, args);        		
        	} else if (Collection.class.isAssignableFrom(paramType)) {
        		// collection 
        		Type type = method.getGenericParameterTypes()[0];
        		if (type instanceof ParameterizedType) {
                    Class<?> elementType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                    if (TypeUtil.isPrimitive(elementType)) {
                    	// collection of primitive
                    	input = this.createInputMap(params, args);
                    } else {
                    	// collection of objects
                    	input = args[0];
                    }
        		} else {
        			// not parameterized.  maybe should throw exception here?	
                	input = args[0]; 		
        		}
        	} else {
	        	// single object
	            input = args[0];
	        }
        } else {
            return this.createInputMap(params, args);
        }
        
        return input;
    }
    
    
    private Map<String, Object> createInputMap(Parameter[] params, Object[] args) {
        //instantiate input map
        Map<String, Object> map = new HashMap<String, Object>();
        
        //add args to input
        for (int i = 0; i < params.length; i++) {
            Parameter param = params[i];
            map.put(param.getName(), args[i]);
        }
        return map;
    }

}
