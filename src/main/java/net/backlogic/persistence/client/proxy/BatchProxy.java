package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ReturnType;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static net.backlogic.persistence.client.proxy.BatchBuiltInCommand.*;

/**
 * Proxy for BatchService interface. It is stateful because of the list of invocation.
 * The state is cleaned after each run.
 */
public class BatchProxy extends PersistenceProxy implements Cloneable {
	
    private JsonHandler jsonHandler;
    private ServiceHandler serviceHandler;
    private Map<String, ServiceMethod> serviceMap;
	private List<BatchedInvocation> invocations;
	private String batchServiceUrl;

	private Class<?> returnType;

	private Map<String, Method> returnMap;

	/**
	 * Constructor for ProxyFactory
	 * @param serviceHandler
	 * @param jsonHandler
	 * @param serviceMap
	 * @param batchServiceUrl
	 * @param returnType
	 */
    public BatchProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler, 
    			Map<String, ServiceMethod> serviceMap, String batchServiceUrl, Class<?> returnType) {
    	super(serviceHandler, serviceMap);
        this.serviceHandler = serviceHandler;
        this.jsonHandler = jsonHandler;
        this.serviceMap = serviceMap;
        this.invocations = new ArrayList<>();
        this.batchServiceUrl = batchServiceUrl;
		this.returnType = returnType;
		this.returnMap = createReturnMap(returnType, serviceMap);
    }

	/**
	 * Constructor for cloning batch proxy.
	 * @param serviceHandler
	 * @param jsonHandler
	 * @param serviceMap
	 * @param batchServiceUrl
	 * @param returnType
	 * @param returnMap
	 */
	public BatchProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler,
					  Map<String, ServiceMethod> serviceMap, String batchServiceUrl,
					  Class<?> returnType, Map<String, Method> returnMap) {
		super(serviceHandler, serviceMap);
		this.serviceHandler = serviceHandler;
		this.jsonHandler = jsonHandler;
		this.serviceMap = serviceMap;
		this.invocations = new ArrayList<>();
		this.batchServiceUrl = batchServiceUrl;
		this.returnType = returnType;
		this.returnMap = returnMap;
	}

	/**
	 * To clone a clean BatchProxy from this instance.
	 * @return the cloned proxy instance
	 */
	public BatchProxy clone() {
		return new BatchProxy(serviceHandler, jsonHandler, serviceMap, batchServiceUrl, returnType, returnMap);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// build in commands
		if (method.getName().equals(CLEAN)) {
			this.invocations = new ArrayList<>();
			return null;
		}
		if (Arrays.asList(RUN, GET, SAVE).contains(method.getName())) {
			Object output = run();
			this.invocations = new ArrayList<>(); // cleaning state
			return output;
		} 
		
		// add invocation
	    String methodKey = MethodUtil.createMethodKey(method);
		ServiceMethod serviceMethod = this.serviceMap.get(methodKey);
		if (serviceMethod == null) {
			throw new DataAccessException("InvalidMethod", "Invalid data access method: " + method.getName());
		}
        Object serviceInput = getInput(serviceMethod, args);
        BatchedInvocation invocation = new BatchedInvocation(methodKey, serviceMethod.getServiceUrl(), serviceInput);
        this.invocations.add(invocation);
		
		return null;
	}

	/**
	 * Create a return map for the batch service, keyed by field name of return type,
	 * with value being the setter method for the field.
	 * @param returnType	return type of the batch service
	 * @param serviceMap service map for the batch service; value is ServiceMethod
	 * @return return map created
	 */
	private Map<String, Method> createReturnMap(Class returnType, Map<String, ServiceMethod> serviceMap) {
		// if return type is array
		if (returnType.isArray() || returnType == Void.class) {
			return null;
		}

		// or else
		Map<String, Method> returnMap = new HashMap<>();
		for (Map.Entry<String, ServiceMethod> entry: serviceMap.entrySet()) {
			// field name
			String fieldName = entry.getValue().getReturnMapping();
			if (fieldName == null) {
				continue;
			}
			// setter
			String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method setter;
			try {
				Field field = returnType.getDeclaredField(fieldName);
				setter = returnType.getMethod(setterName, field.getType());
			} catch (NoSuchFieldException e) {
				throw new DataAccessException("BatchProxyError", "Field not found for: "
						+ fieldName + " of " + returnType.getName());
			} catch (NoSuchMethodException e) {
				throw new DataAccessException("BatchProxyError", "Setter method not found for: "
						+ setterName + " of " + returnType.getName());
			}
			// add map entry
			returnMap.put(fieldName, setter);
		}
		// return
		return returnMap;
	}

	private Object run() {
		// output array
		Object[] outputs = new Object[this.invocations.size()];
		Object batchReturn = null;
		if (returnMap != null) {
			try {
				batchReturn = returnType.getConstructors()[0].newInstance();
			} catch (Exception e) {
				throw new DataAccessException("BatchExecutionError", "Problem to instantiate batch return object", e);
			}
		}

		// invoke services
		@SuppressWarnings("unchecked")
		Map<String, Object> outputMap = (Map<String, Object>) serviceHandler.invoke(
				batchServiceUrl, invocations, ReturnType.MAP, String.class, true
		);
		
		// process outputs
		for ( int i = 0; i<invocations.size(); i++) {
			Object output;
			// get output
			BatchedInvocation invocation = invocations.get(i);
			ServiceMethod sm = this.serviceMap.get(invocation.getName());
			
			output = outputMap.get(invocation.getName());			
			if (output != null) {
				// to json string
				String jsonString = this.jsonHandler.toJson(output);
				
				// to return type
				output = this.jsonHandler.toReturnType(jsonString, sm.getReturnType(), sm.getElementType());
			} else if (sm.getReturnType() == ReturnType.LIST) {
				output = new ArrayList<>();
			}
			// add to output array
			outputs[i] = output;
			// add to return object
			if (returnMap != null && sm.getReturnMapping() != null) {
				String fieldName = sm.getReturnMapping();
				Method setter = returnMap.get(fieldName);
				try {
					setter.invoke(batchReturn, output);
				} catch (Exception e) {
					throw new DataAccessException("BatchExecutionError", "Failed to set return field: "
							+ fieldName + " of " + returnType.getName(), e);
				}
			}
		}
		// return
		if (returnType.isArray()) {
			return outputs;
		} else if (returnType == Void.class) {
			return null;
		} else if (batchReturn != null) {
			return batchReturn;
		} else {
			return null;
		}
	}

}
