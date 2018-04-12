/**
 * 
 */
package net.backlogic.persistence.client;

import java.lang.reflect.Method;
import java.util.HashMap;

import net.backlogic.persistence.client.annotation.Service;


/**
 * @author Ken
 * Created on 10/22/2017
 * 
 * Providing Query object
 */
public class QueryProxy extends PersistenceProxy {
	
	  public QueryProxy(String serviceUrl, ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  super(serviceUrl, serviceHandler, jsonHandler);
	  }
	  
	  
	  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		 if (m.getName() == "setGroupId") {
			 groupId = (String)args[0];
			 return null;
		 }
		 else {
			 return invokeService (proxy, m, args);
		 }    	  
	  }	
	  
	  
	  private Object invokeService (Object proxy, Method m, Object[] args) throws Throwable {
	      try {
		        serviceUrl = getServiceUrl(m);
		        if ( groupId != null ) {
		        	serviceUrl = serviceUrl + "?" + "groupId=" + groupId;
		        }
		        Object input = getInput(m, args);
		        String inputJson = jsonHandler.toJson(input);
		        String outputJson = serviceHandler.invoke(serviceUrl, inputJson, groupId);
		        Object output = jsonHandler.toObject(outputJson, m.getReturnType());
			    return output;
				 
		      } catch (Exception e) {
			         throw e;
		      }	      		  
	  }
	  
	  
	  /*
	   * get service url
	   */
	  private String getServiceUrl(Method method) {
		  String url;
		  Service serviceAnnotation = method.getAnnotation(Service.class);
		  if ( serviceAnnotation == null ) {
			  throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Service annotation is missing");
		  }
		  else {
			  url = serviceAnnotation.value();
		  }
		  if ( url == null || url == "" ) {
			  throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Invalid servic url");			
		  }
		  
		  return url;
	  }
	  	  
}
