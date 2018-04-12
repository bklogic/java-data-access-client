/**
 * 
 */
package net.backlogic.persistence.client;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;

/**
 * @author Ken
 * Created on 10/2/2017
 * 
 * Repository Proxy class. Responsible for handling Repository requests.
 */
public class RepositoryProxy extends PersistenceProxy {
	
	  public RepositoryProxy(String serviceUrl, ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
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
	  
	  	  
	  public Object invokeService(Object proxy, Method m, Object[] args) throws Throwable
	    {
	      try {
	        String operation = getOperation(m);
	        Object input = getInput(m, args);
	        String inputJson = jsonHandler.toJson(input);
	        String outputJson = serviceHandler.invoke(serviceUrl + "/" + operation, inputJson, groupId);
	        Object output = jsonHandler.toObject(outputJson, m.getReturnType());
		    return output;
	      } catch (Exception e) {
		         throw e;
	      }
	    }	
	  
	  
	  protected String getOperation(Method method) {
		  //operation
		  String operation;
		  
		  //get operation based on annotation
		  if ( method.getAnnotation(Read.class) != null ) {
			  operation = "read";
		  }
		  else if ( method.getAnnotation(Create.class) != null ) {
			  operation = "create";
		  }
		  else if ( method.getAnnotation(Update.class) != null ) {
			  operation = "update";
		  }
		  else if ( method.getAnnotation(Delete.class) != null ) {
			  operation = "delete";
		  }
		  else if ( method.getAnnotation(Save.class) != null ) {
			  operation = "save";
		  }
		  else {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Interface method is not properly annotated");						  
		  }
		  
		  //return
		  return operation;
	  }
	  	  
}

