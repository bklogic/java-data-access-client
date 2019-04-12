/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.BacklogicQuery;
import net.backlogic.persistence.client.annotation.Name;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;


/**
 * 
 * Proxy for Query interface
 * 
 */
public class QueryProxy extends PersistenceProxy {
	
	  public QueryProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  super(serviceHandler, jsonHandler);
	  }
	  
	  /*
	   * get service url
	   */
	  @Override
	  protected String getServiceUrl(Method method) {
		  String interfaceUrl, methodUrl;
		  
		  //interface url
		  BacklogicQuery interfaceAnnotation = method.getDeclaringClass().getAnnotation(BacklogicQuery.class);
		  interfaceUrl = interfaceAnnotation.value();
		  
		  //method url
		  Name urlAnnotation = method.getAnnotation(Name.class);
		  if ( urlAnnotation == null ) {
			  throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Url annotation is missing");
		  }
		  else {
			  methodUrl = urlAnnotation.value();
		  }
		  
		  return UrlUtil.getUrl(interfaceUrl, methodUrl);
	  }
	  	  
}
