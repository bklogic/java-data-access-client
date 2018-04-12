/**
 * 
 */
package net.backlogic.persistence.client;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ken
 *
 */
public abstract class PersistenceProxy implements InvocationHandler {
	  //service url
	  String serviceUrl;	  
	  //groupId
	  String groupId;
	  //http hanlder
	  ServiceHandler serviceHandler;
	  //json hanlder
	  JsonHandler jsonHandler;
	  
	  
	  public PersistenceProxy (String serviceUrl, ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  this.serviceUrl = serviceUrl; 
		  this.serviceHandler = serviceHandler;
		  this.jsonHandler = jsonHandler;
	  }

	  public abstract Object invoke(Object proxy, Method m, Object[] args) throws Throwable;	  
	  
	  	  
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
