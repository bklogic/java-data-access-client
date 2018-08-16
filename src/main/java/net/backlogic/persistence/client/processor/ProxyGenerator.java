package net.backlogic.persistence.client.processor;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Repository;
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
	 * Constructor
	 */
	public ProxyGenerator(String baseUrl) {
		this.serviceHandler = new ServiceHandlerImpl(baseUrl);
		this.jsonHandler = new JsonHandlerImpl();
	}
	
	/*
	 * Change service handler. For testing purpose, to inject a mock service handler.
	 */
	public void setServiceHandler(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
	}

	
	/*
	 * Service Methods
	 */
	
	public Map<Class<?>, Object> generate(Map< String, List< Class<?>> > interfaceLists, Class<?> factoryBeanClass) {
		//interface map
		Map<Class<?>, Object> interfaceMap = new HashMap<Class<?>, Object>();
		
		//Dao
		for (Class<?> interfaceType : interfaceLists.get("dao")  ) {
			interfaceMap.put( interfaceType, createFactoryBean(interfaceType, "dao", factoryBeanClass) );
		}
		
		//Repository
		for (Class<?> interfaceType : interfaceLists.get("repository")  ) {
			interfaceMap.put( interfaceType, createFactoryBean(interfaceType, "repository", factoryBeanClass) );
		}
		
		//Query
		for (Class<?> interfaceType : interfaceLists.get("query")  ) {
			interfaceMap.put( interfaceType, createFactoryBean(interfaceType, "query", factoryBeanClass) );
		}
		
		//Command
		for (Class<?> interfaceType : interfaceLists.get("command")  ) {
			interfaceMap.put( interfaceType, createFactoryBean(interfaceType, "command", factoryBeanClass) );
		}
		
		return interfaceMap;
	}
	
	
	
	public Object createRepository(Class<?> repositoryType) {
		//validate repository interface
		Repository repositoryAnnotation = repositoryType.getAnnotation(Repository.class);
		if ( repositoryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", repositoryType.getName() + " is not repository interface");
		}
		
		//instantiate repository interface proxy
		Object proxy = Proxy.newProxyInstance(
					repositoryType.getClassLoader(), new Class[] {repositoryType}, new RepositoryProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
	
	public Object createDao(Class<?> daoType) {
		//validate repository interface
		Query queryAnnotation = daoType.getAnnotation(Query.class);
		if ( queryAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", daoType.getName() + " is not dao interface");
		}
		
		//instantiate query interface proxy
		Object proxy = Proxy.newProxyInstance(
					daoType.getClassLoader(), new Class[] {daoType}, new QueryProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
	
	public Object createQuery(Class<?> queryType) {
		//validate repository interface
		Query queryAnnotation = queryType.getAnnotation(Query.class);
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
		Command persistAnnotation = commandType.getAnnotation(Command.class);		
		if ( persistAnnotation == null ) {
			throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", commandType.getName() + " is not command interface");
		}
		
		//instantiate query interface proxy
		Object proxy = Proxy.newProxyInstance(
				commandType.getClassLoader(), new Class[] {commandType}, new CommandProxy(serviceHandler, jsonHandler)
				);
		
		return proxy;
	}
	
	
	private Object createFactoryBean(Class<?> interfaceType, String intrefaceCategory, Class<?> facoryBeanClass) {
		//instantiate factory bean
		Object bean = Proxy.newProxyInstance(
			facoryBeanClass.getClassLoader(), 
			new Class[] {facoryBeanClass}, 
			(object, method, args) -> {
				switch (method.getName()) {
					case "isSingleton":
						return false;
					case "getObjectType":
						return interfaceType;
					case "getObject":
						switch (intrefaceCategory) {
							case "dao": 
								return createDao(interfaceType);
							case "repository": 
								return createRepository(interfaceType);
							case "query": 
								return createQuery(interfaceType);
							case "command": 
								return createCommand(interfaceType);
						}
					default:
						throw new PersistenceException(PersistenceException.SystemException, "UnexpectedFactoryMethod", "Method Name: " + method.getName());
				}
			}
		);
		
		return bean;
		
	}

}
