/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Url;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;

/**
 * @author Ken
 *
 */
public class CommandProxy extends PersistenceProxy{

	  public CommandProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  super(serviceHandler, jsonHandler);
	  }
	  
	  
	  /*
	   * get service url
	   */
	  @Override
	  protected String getServiceUrl(Method method) {
		  String interfaceUrl, methodUrl;
		  
		  //interface url
		  Command interfaceAnnotation = method.getDeclaringClass().getAnnotation(Command.class);
		  interfaceUrl = interfaceAnnotation.value();
		  
		  //method url
		  Url urlAnnotation = method.getAnnotation(Url.class);
		  if ( urlAnnotation == null ) {
			  throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Url annotation is missing");
		  }
		  else {
			  methodUrl = urlAnnotation.value();
		  }
		  
		  return UrlUtil.getUrl(interfaceUrl, methodUrl);
	  }	  	  
	  	  
}
