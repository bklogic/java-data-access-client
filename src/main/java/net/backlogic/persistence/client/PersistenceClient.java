/**
 * 
 */
package net.backlogic.persistence.client;

import java.lang.reflect.Proxy;

import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Group;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Repository;

/**
 * @author Ken
 * Created on 10/22/2017
 */
public class PersistenceClient {
	// service handler
	private ServiceHandler serviceHandler;
	// json Handler
	private JsonHandler jsonHandler;
	//service group
	private ServiceGroup serviceGroup;
	
	/*
	 * Constructor
	 */
	public PersistenceClient(String baseUrl) {
		this.serviceHandler = new ServiceHandlerImpl(baseUrl);
		this.jsonHandler = new JsonHandlerImpl();
		this.serviceGroup = new ServiceGroupImpl(serviceHandler, jsonHandler);
	}
	
	public PersistenceClient(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.jsonHandler = new JsonHandlerImpl();
		this.serviceGroup = new ServiceGroupImpl(serviceHandler, jsonHandler);
	}
	
	public void setHttpHandler(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
	}
	
	public void setJsonHandler(JsonHandler jsonHandler) {
		this.jsonHandler = jsonHandler;
	}

	
	/*
	 * Public Methods
	 */
	public Object getRepository(Class<?> repositoryType) {
		//service url
		String url;
		
		//validate repository interface
		Repository repositoryAnnotation = repositoryType.getAnnotation(Repository.class);
		
		if ( repositoryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Input is not repository interface");
		}
		else {
			url = repositoryAnnotation.value();
		}
		if ( url == null || url == "" ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Invalid repository service path");			
		}
		
		//instantiate repository interface proxy
		Object repositoryProxy = Proxy.newProxyInstance(
					repositoryType.getClassLoader(), new Class[] {repositoryType}, new RepositoryProxy(url, serviceHandler, jsonHandler)
				);
		
		return repositoryProxy;
	}
	
	
	public Object getQuery(Class<?> queryType) {
		//service url
		String url = "";
		
		//validate repository interface
		Query queryAnnotation = queryType.getAnnotation(Query.class);
		
		if ( queryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Input is not query interface");
		}
		
		//instantiate query interface proxy
		Object queryProxy = Proxy.newProxyInstance(
					queryType.getClassLoader(), new Class[] {queryType}, new QueryProxy(url, serviceHandler, jsonHandler)
				);
		
		return queryProxy;
	}
	
	
	public Object getCommand(Class<?> persistType) {
		//service url
		String url = "";
		
		//validate repository interface
		Command persistAnnotation = persistType.getAnnotation(Command.class);
		
		if ( persistAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Input is not persist interface");
		}
		
		//instantiate query interface proxy
		Object persistProxy = Proxy.newProxyInstance(
					persistType.getClassLoader(), new Class[] {persistType}, new CommandProxy(url, serviceHandler, jsonHandler)
				);
		
		return persistProxy;
	}

	
	public ServiceGroup getServiceGroup() {
		return serviceGroup;
	}
	
}
