package net.backlogic.persistence.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import net.backlogic.persistence.client.sample.Customer;
import net.backlogic.persistence.client.sample.CustomerCommand;
import net.backlogic.persistence.client.sample.CustomerDao;
import net.backlogic.persistence.client.sample.CustomerQuery;
import net.backlogic.persistence.client.sample.CustomerRepository;

public class PersistenceClientTest {
	
	private static PersistenceClient client;
	private static MockServiceHandler handler;
	private static Map<Class<?>, Object> factoryMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client = new PersistenceClient("http://loclahost/acct/app", "net.backlogic.persistence.client");
		handler = new MockServiceHandler();
		client.setServiceHandler(handler);
		factoryMap = client.getFactoryBeans(MockFactoryBean.class);
		System.out.println(factoryMap.size());
	}

	@Test
	public void testFactoryBeans() {
		MockFactoryBean factory;
		factory = (MockFactoryBean)factoryMap.get(CustomerRepository.class);
		System.out.println( factory.getObjectType().getName() );
		assertEquals("Factory should be for CustomerReposiroty", CustomerRepository.class, factory.getObjectType());
		
		factory = (MockFactoryBean)factoryMap.get(CustomerQuery.class);
		System.out.println( factory.getObjectType().getName() );
		assertEquals("Factory should be for CustomerQuery", CustomerQuery.class, factory.getObjectType());
		
		factory = (MockFactoryBean)factoryMap.get(CustomerCommand.class);
		System.out.println( factory.getObjectType().getName() );
		assertEquals("Factory should be for CustomerCommand", CustomerCommand.class, factory.getObjectType());

		factory = (MockFactoryBean)factoryMap.get(CustomerDao.class);
		System.out.println( factory.getObjectType().getName() );
		assertEquals("Factory should be for CustomerCommand", CustomerDao.class, factory.getObjectType());
		
		//display all factories
	    for (Entry<Class<?>, Object> pair : factoryMap.entrySet()){
	        System.out.println(pair.getKey().getName()+" Singleton: "+ ((MockFactoryBean)pair.getValue()).isSingleton());
	    }
	}
	
	
	@Test
	public void testCustomerRepository() {
		Customer customer = new Customer(0, "ken"); 
		List<Customer> customers = new ArrayList<Customer>(); customers.add(customer);
		Map<String, String> map;

		//preset output
		map = new HashMap<String, String>();
	 	map.put("/mod/customer/read", "[{\"customerId\":1, \"customerName\":\"wang\"}]");
	 	map.put("/mod/customer/create", "{\"customerId\":1, \"customerName\":\"wang\"}");
	 	map.put("/mod/customer/delete", "{}");
	 	handler.setOutputMap(map);
		
		//get repository
		MockFactoryBean factory = (MockFactoryBean)factoryMap.get(CustomerRepository.class);
		CustomerRepository respository = (CustomerRepository)factory.getObject();
		
		//output
		Customer output; 
		List<Customer> outputs;
		
		//create customer
		output = respository.add(customer);
		assertEquals("Url should be /mod/Customer/create", "/mod/Customer/create", handler.getServiceUrl());
		assertEquals("Input should be {\"customerId\":0, \"customerName\":\"ken\"}", "{\"customerId\":0,\"customerName\":\"ken\"}", handler.getServiceInput());
		assertEquals("Output CustomerId should be 1", 1, output.getCustomerId());
		assertEquals("Output CustomerName should be wang", "wang", output.getCustomerName());
		
		outputs = respository.getCustomers();
		assertEquals("Url should be /mod/Customer/read", "/mod/Customer/read", handler.getServiceUrl());
		assertEquals("Input should be {}", "{}", handler.getServiceInput());
		System.out.println(outputs.size());
		Map o = (Map)outputs.get(0);
//		assertEquals("Output CustomerId should be 1", 1, outputs.get(0).getCustomerId());
		assertEquals("Output CustomerId should be 1", 1, o.get("customerId") );
//		assertEquals("Output CustomerName should be wang", "wang", outputs.get(0).getCustomerName());
		
		
	}

}
