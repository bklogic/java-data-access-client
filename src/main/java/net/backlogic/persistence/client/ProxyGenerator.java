package net.backlogic.persistence.client;

import java.lang.reflect.Proxy;

import net.backlogic.persistence.client.annotation.BacklogicCommand;
import net.backlogic.persistence.client.annotation.BacklogicQuery;
import net.backlogic.persistence.client.annotation.BacklogicRepository;
import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.JsonHandlerImpl;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.handler.ServiceHandlerImpl;
import net.backlogic.persistence.client.proxy.CommandProxy;
import net.backlogic.persistence.client.proxy.QueryProxy;
import net.backlogic.persistence.client.proxy.RepositoryProxy;

/**
 * 
 * Responsible for generating persistence proxy from interface
 *
 */
public class ProxyGenerator {
	// service handler
	private ServiceHandler serviceHandler;
	// json Handler
	private JsonHandler jsonHandler;
	
	/*
	 * Construct proxy generator
	 */
	public ProxyGenerator(String baseUrl) {
		this.serviceHandler = new ServiceHandlerImpl(baseUrl);
		this.jsonHandler = new JsonHandlerImpl();
	}
		

	/*
	 * Construct proxy generator with mock service handler
	 */
	public ProxyGenerator(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.jsonHandler = new JsonHandlerImpl();
	}
	
	
	public Object createRepository(Class<?> repositoryType) {
		//validate repository interface
		BacklogicRepository repositoryAnnotation = repositoryType.getAnnotation(BacklogicRepository.class);
		if ( repositoryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", repositoryType.getName() + " is not repository interface");
		}
		
		//instantiate repository interface proxy
		Object proxy = Proxy.newProxyInstance(
					repositoryType.getClassLoader(), new Class[] {repositoryType}, new RepositoryProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
	
	public Object createQuery(Class<?> queryType) {
		//validate repository interface
		BacklogicQuery queryAnnotation = queryType.getAnnotation(BacklogicQuery.class);
		if ( queryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", queryType.getName() + " is not query interface");
		}
		
		//instantiate query interface proxy
		Object proxy = Proxy.newProxyInstance(
					queryType.getClassLoader(), new Class[] {queryType}, new QueryProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
	
	public Object createCommand(Class<?> commandType) {
		//validate repository interface
		BacklogicCommand persistAnnotation = commandType.getAnnotation(BacklogicCommand.class);		
		if ( persistAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", commandType.getName() + " is not command interface");
		}
		
		//instantiate query interface proxy
		Object proxy = Proxy.newProxyInstance(
				commandType.getClassLoader(), new Class[] {commandType}, new CommandProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
}
