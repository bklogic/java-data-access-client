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
import java.util.List;
import java.util.Map;

import net.backlogic.persistence.client.handler.ReturnType;
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
    	    String methodKey = MethodUtil.createMethodKey(m);
        	ServiceMethod sm = this.serviceMap.get(methodKey);
        	
            // input
            Object input = this.getInput(sm, args);

            // invoke service
            Object output = this.serviceHandler.invoke(sm.getServiceUrl(), input, sm.getReturnType(), sm.getElementType());

            // convert list to instance, for ReadById
            if (output instanceof List && sm.getReturnType() == ReturnType.OBJECT) {
            	@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) output;
            	if ( list.size() > 0 ) {
            		output = list.get(0);
            	} else {
            		output = null;
            	}
            }
            
            //return
            return output;
        } catch (Exception e) {
            throw e;
        }
    }


    protected Object getInput(ServiceMethod sm, Object[] args) {
        Object input;
        switch (sm.getInputType()) {
        	case MAP:
        		input = this.createInputMap(sm.getInputParams(), args);
        		break;
        	case SINGLE:
        		input = args[0];
        		break;
        	default:
        		input = null;
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
