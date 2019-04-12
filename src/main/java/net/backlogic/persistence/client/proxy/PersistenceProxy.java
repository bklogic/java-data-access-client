/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;

/**
 * @author Ken
 *
 * Proxy Strategy:
 *   One proxy per persistence interface. Thus proxy is shared across threads, and should contained private property.
 * Group Service
 *   GroupId needs also be passed in at invocation, possible as a formal parameter of the persistence methods.
 *   Will decide later.  At the moment, no support for group service.
 */
public abstract class PersistenceProxy implements InvocationHandler {
	  //http handler
	  ServiceHandler serviceHandler;
	  //json handler
	  JsonHandler jsonHandler;
	  
	  /*
	   * Constructor
	   */
	  public PersistenceProxy (ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  this.serviceHandler = serviceHandler;
		  this.jsonHandler = jsonHandler;
	  }

	  @Override
	  public Object invoke (Object proxy, Method m, Object[] args) throws Throwable {
	      try {
	    	  	//input and groupId
		        Object input = getInput(m, args);
		        
		        //input json
		        String inputJson = jsonHandler.toJson(input);
		        
		        //service url
		        String serviceUrl = getServiceUrl(m);
		        
		        //invoke service
		        String outputJson = serviceHandler.invoke(serviceUrl, inputJson);
		        
		        //output  TODO: validate supported return types  getGenericReturnType(), getReturnType()
		        Object output; 
		        Type elementType;
		        Class<?> returnType = m.getReturnType();
		        Type genericReturnType = m.getGenericReturnType();
		        if (returnType.getName() == "void") {
		        	output = null;
		        }
		        else if (returnType == List.class && genericReturnType instanceof ParameterizedType) {
	        		elementType = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
	        		output = jsonHandler.toList(outputJson, (Class<?>) elementType);
		        }
		        else if (returnType == Set.class && genericReturnType instanceof ParameterizedType) {
	        		elementType = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
	        		output = jsonHandler.toSet(outputJson, (Class<?>) elementType);
		        }
		        else {
			        output = jsonHandler.toObject(outputJson, m.getReturnType());		        	
		        }
		        
		        //return
			    return output;
				 
		      } catch (Exception e) {
			         throw e;
		      }	      		  
	  }
	  	  	  
	  
	  abstract protected String getServiceUrl(Method m);
	  
	  
	  protected Object getInput(Method method, Object[] args) {
		  //get method parameters
		  Parameter param;
		  Parameter[] params = method.getParameters();
		  
		  if ( params.length == 0 ) {
			  return null;
		  }
		  else if ( params.length == 1 && !isPrimitiveType(params[0].getType()) ) {
			  return args[0];
		  }
		  else {
			  //instantiate input map
			  HashMap<String, Object> input = new HashMap<String, Object>();
			  //add args to input
			  for (int i=0; i<params.length; i++) {
				  param = params[i];
				  input.put(param.getName(), args[i]);				  
			  }
			  return input;
		  }
	  }
	 	  
	  /*
	   * To check whether a parameter is a primitive type
	   */
	  private static HashSet<Class<?>> primitiveTypes;
	  static {
		  primitiveTypes = new HashSet<Class<?>>();
	        primitiveTypes.add(Boolean.class);
	        primitiveTypes.add(Character.class);
	        primitiveTypes.add(Byte.class);
	        primitiveTypes.add(Short.class);
	        primitiveTypes.add(Integer.class);
	        primitiveTypes.add(Long.class);
	        primitiveTypes.add(Float.class);
	        primitiveTypes.add(Double.class);
	        primitiveTypes.add(java.sql.Date.class);
	        primitiveTypes.add(java.util.Date.class);
	        primitiveTypes.add(java.util.Calendar.class);
	  }
	  protected boolean isPrimitiveType(Class<?> type) {
		  return type.isPrimitive() || primitiveTypes.contains(type);
	  }

}
