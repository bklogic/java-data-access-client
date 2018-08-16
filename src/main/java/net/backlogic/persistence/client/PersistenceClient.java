/**
 * 
 */
package net.backlogic.persistence.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.processor.InterfaceScanner;
import net.backlogic.persistence.client.processor.ProxyGenerator;

/**
 * The main class of persistence client, providing
 * 	 	- methods of obtaining client instance,
 *  	- methods to get persistence interface proxy, and
 * 	    - method to get interface list
 *  
 * The persistence client comprises:
 * 		- an interface scanner to scan for persistence interfaces
 * 		- a proxy generator to generate persistence proxy for persistence interface
 * 		- a service handler for invoking persistence service
 * 		- a JsonHandler for handling json-object conversion
 *  
 */
public class PersistenceClient {
	/*
	 * variable to hold a client instance
	 */
	private static PersistenceClient instance = null;
	/*
	 * interface scanner
	 */
	InterfaceScanner scanner;
	/*
	 * proxy generator
	 */
	ProxyGenerator generator;
	
	
	/*
	 * Constructor
	 */
	public PersistenceClient(String baseUrl, String basePackage) {
		this.scanner = new InterfaceScanner(basePackage);
		this.generator = new ProxyGenerator(baseUrl);
	}
	
	
	/**
	 * Create and return a PersistenceClient instance
	 * @param baseUrl	baseUrl for persistence services
	 * @param basePackage	base package for persistence interface scanning
	 * @return	PersistenceClient instance created
	 */
	public static PersistenceClient newInstance(String baseUrl, String basePackage){
		instance = new PersistenceClient(baseUrl, basePackage);
		return instance;
	}

	
	/**
	 * Get the previously created PersistenceClient instance
	 * @return  instance of previously created persistence client
	 */
	public static PersistenceClient getInstance() {
		//check instance is not null
		if ( instance == null ) {
			throw new PersistenceException(PersistenceException.ServiceException, "ClientNotAvaiable", "The persistence client is not avaiable. Call newInstance method first.");
		}
		return instance;
	}

	
	/*
	 * Change service handler. For testing purpose, to inject a mock service handler.
	 */
	public void setServiceHandler(ServiceHandler serviceHandler) {
		generator.setServiceHandler(serviceHandler);
	}	
	
	
	/**
	 * Return the list of interfaces found by scanning
	 * @return
	 */
	public List<Class<?>> getInterfaceList() {
		//scan 
		Map<String, List< Class<?>>> interfaceLists = scanner.scan();
		
		//consolidate
		List<Class<?>> interfaceList = new ArrayList<Class<?>>();
		interfaceList.addAll(interfaceLists.get("dao"));
		interfaceList.addAll(interfaceLists.get("repository"));
		interfaceList.addAll(interfaceLists.get("query"));
		interfaceList.addAll(interfaceLists.get("command"));
		
		//return
		return interfaceList;
	}
	
	
	/**
	 * Return FactoryBeans for scanned persistence interfaces. 
	 * Specifically for Spring Boot.
	 * @factoryBeanClass FactoryBean.class, passed by Spring Boot application to avoid dependence on Spring Framework.
	 * @return  a Map of FactoryBeans. Key = interface class; value: FactoryBean for the interface class.
	 */
	public Map<Class<?>, Object> getFactoryBeans(Class<?> factoryBeanClass) {
		//scan 
		Map<String, List< Class<?>>> interfaceLists = scanner.scan();
		
		//generate proxys
		Map<Class<?>, Object> facoryMap = generator.generate(interfaceLists, factoryBeanClass);
		
		return facoryMap;
	}
	
	
	/**
	 * Get a proxy object for a DAO interface type 
	 * @param daoType	the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getDao(Class<?> daoType) {
		return generator.createDao(daoType);
	}

	
	/**
	 * Get a proxy object for a repository interface type 
	 * @param repositoryType	the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getRepository(Class<?> repositoryType) {
		return generator.createRepository(repositoryType);
	}
	
	
	/**
	 * Get a proxy object for a query interface type 
	 * @param repositoryType	the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getQuery(Class<?> queryType) {
		return generator.createQuery(queryType);
	}

	
	/**
	 * Get a proxy object for a command interface type 
	 * @param repositoryType  the interface type
	 * @return  return a proxy for given interface type. Return null if not exists.
	 */
	public Object getCommand(Class<?> commandType) {
		return generator.createCommand(commandType);
	}
		
}
