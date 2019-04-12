/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.BacklogicRepository;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;

/**
 * @author Ken
 * Created on 10/2/2017
 * 
 * Repository Proxy class. Responsible for handling Repository requests.
 */
public class RepositoryProxy extends PersistenceProxy {
	
	  public RepositoryProxy(ServiceHandler serviceHandler, JsonHandler jsonHandler){ 
		  super(serviceHandler, jsonHandler);
	  }
	  

	  @Override
	  protected String getServiceUrl(Method method) {
		  String interfaceUrl, methodUrl;
		  
		  //interface url
		  BacklogicRepository interfaceAnnotation = method.getDeclaringClass().getAnnotation(BacklogicRepository.class);
		  interfaceUrl = interfaceAnnotation.value();
		  if (interfaceUrl == null || interfaceUrl == "") {
			  interfaceUrl = interfaceAnnotation.url();
		  }
		  
		  //method url
		  if ( method.getAnnotation(Read.class) != null ) {
			  methodUrl = "/read";
		  }
		  else if ( method.getAnnotation(Create.class) != null ) {
			  methodUrl = "/create";
		  }
		  else if ( method.getAnnotation(Update.class) != null ) {
			  methodUrl = "/update";
		  }
		  else if ( method.getAnnotation(Delete.class) != null ) {
			  methodUrl = "/delete";
		  }
		  else if ( method.getAnnotation(Save.class) != null ) {
			  methodUrl = "/save";
		  }
		  else {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Repository Interface method is not properly annotated");						  
		  }
		  
		  return UrlUtil.getUrl(interfaceUrl, methodUrl);
	  }	  
	  	  
}

