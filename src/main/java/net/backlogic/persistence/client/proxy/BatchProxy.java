package net.backlogic.persistence.client.proxy;

import static net.backlogic.persistence.client.proxy.BatchBuiltInCommand.CLEAN;
import static net.backlogic.persistence.client.proxy.BatchBuiltInCommand.GET;
import static net.backlogic.persistence.client.proxy.BatchBuiltInCommand.RUN;
import static net.backlogic.persistence.client.proxy.BatchBuiltInCommand.SAVE;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ReturnType;
import net.backlogic.persistence.client.handler.ServiceHandler;

public class BatchProxy extends PersistenceProxy {
	
    private JsonHandler jsonHandler;
    private ServiceHandler serviceHandler;
    private Map<String, ServiceMethod> serviceMap;
	private List<BatchedInvocation> invocations;
	private String batchServiceUrl;

    public BatchProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler, 
    			Map<String, ServiceMethod> serviceMap, String batchServiceUrl) {
    	super(serviceHandler, serviceMap);
        this.serviceHandler = serviceHandler;
        this.jsonHandler = jsonHandler;
        this.serviceMap = serviceMap;
        this.invocations = new ArrayList<>();
        this.batchServiceUrl = batchServiceUrl;
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// build in commands
		if (method.getName().equals(CLEAN)) {
			this.invocations = new ArrayList<>();
			return null;
		}
		if (Arrays.asList(RUN, GET, SAVE).contains(method.getName())) {
			return run();
		} 
		
		// add invocation
		ServiceMethod serviceMethod = this.serviceMap.get(method.getName());
		if (serviceMethod == null) {
			throw new DataAccessException("InvalidMethod", "Invalid data access method: " + method.getName());
		}
        Object serviceInput = getInput(method, args);
        BatchedInvocation invocation = new BatchedInvocation(method.getName(), serviceMethod.getServiceUrl(), serviceInput);
        this.invocations.add(invocation);
		
		return null;
	}

	
	private Object[] run() {
		// output array
		Object[] outputs = new Object[this.invocations.size()];
		
		// invoke services
		@SuppressWarnings("unchecked")
		Map<String, Object> outputMap = (Map<String, Object>) serviceHandler.invoke(batchServiceUrl, invocations, ReturnType.MAP, String.class);
		
		// process outputs
		for ( int i = 0; i<invocations.size(); i++) {
			Object output;
			// get output
			BatchedInvocation invocation = invocations.get(i);
			output = outputMap.get(invocation.getName());
			
			// to json string
			String jsonString = this.jsonHandler.toJson(output);
			
			// to return type
			ServiceMethod sm = this.serviceMap.get(invocation.getName());
			output = this.jsonHandler.toReturnType(jsonString, sm.getReturnType(), sm.getElementType());
			outputs[i] = output;		
		}
		
		return outputs;
	}
	
}
