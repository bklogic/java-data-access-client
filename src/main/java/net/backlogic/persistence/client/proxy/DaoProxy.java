/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Dao;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;


/**
 * @author Ken
 * Created on 10/22/2017
 * 
 * Providing Query object
 */
public class DaoProxy extends PersistenceProxy {
	
	  public DaoProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  super(serviceHandler, jsonHandler);
	  }
	  
	  @Override
	  protected String getServiceUrl(Method method) {
		  String interfaceUrl, methodUrl;
		  
		  //interface url
		  Dao interfaceAnnotation = method.getDeclaringClass().getAnnotation(Dao.class);
		  interfaceUrl = interfaceAnnotation.value();
		  
		  //method url
		  Query queryAnnotation = method.getAnnotation(Query.class);
		  Command commandAnnotation = method.getAnnotation(Command.class);
		  if ( queryAnnotation != null ) {
			  methodUrl = queryAnnotation.value();			  
		  }
		  else if ( commandAnnotation != null ) {
			  methodUrl = commandAnnotation.value();			  
		  }
		  else {
			  throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Query or Command annotation is missing");
		  }
		  
		  return UrlUtil.getUrl(interfaceUrl, methodUrl);
	  }	  	  
	  	  
}
