/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.handler.ReturnType;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.handler.TypeUtil;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy Strategy:
 * One proxy per persistence interface. Thus proxy is shared across threads, and should not contain private property.
 */
public abstract class PersistenceProxy implements InvocationHandler {
    //http handler
    ServiceHandler serviceHandler;

    /*
     * Constructor
     */
    public PersistenceProxy(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        try {
            // input
            Object input = getInput(m, args);

            //service url
            String serviceUrl = getServiceUrl(m);

            // output type  TODO: validate supported return types  getGenericReturnType(), getReturnType()
            ReturnType outputType;
            Class<?> elementType;
            Class<?> returnType = m.getReturnType();
            Type genericReturnType = m.getGenericReturnType();
            if (returnType.getName() == "void") {
                outputType = ReturnType.VOID;
                elementType = Void.class;
            } else if (returnType == List.class && genericReturnType instanceof ParameterizedType) {
                outputType = ReturnType.LIST;
                elementType = (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
            } else if (TypeUtil.isPrimitive(returnType)) {
                outputType = ReturnType.VALUE;
                elementType = returnType;
            } else {
                outputType = ReturnType.OBJECT;
                elementType = returnType;
            }

            // invoke service
            Object output = this.serviceHandler.invoke(serviceUrl, input, outputType, elementType);

            //return
            return output;
        } catch (Exception e) {
            throw e;
        }
    }

    abstract protected String getServiceUrl(Method m);

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
