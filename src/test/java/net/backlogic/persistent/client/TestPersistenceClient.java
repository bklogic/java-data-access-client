/**
 * 
 */
package net.backlogic.persistent.client;


import java.util.List;

import net.backlogic.persistence.client.PersistenceClient;
import net.backlogic.persistence.client.ServiceGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ken
 *
 */
public class TestPersistenceClient {

	  /*
	   * For testing purpose
	   */
	  private String getOutput(String methodName) {
		  //map
		  HashMap<String, String> map = new HashMap<String, String>();
		  map.put("get", "{\"customerId\":1, \"customerName\":\"wang\"}");
		  map.put("add", "{\"customerId\":1, \"customerName\":\"wang\"}");
		  map.put("remove", "{}");
		  map.put("getCustomersByNameOrId", "[{\"customerId\":1, \"customerName\":\"wang\"}]");
		  map.put("getCustomers", "[{\"customerId\":1, \"customerName\":\"wang\"}, {\"customerId\":2, \"customerName\":\"eric\"}]");
		  
		  //return
		  return map.get(methodName);
	  }
	  
	
	
	private void testRepository() {
		//client
		PersistenceClient client = new PersistenceClient( (url, input, groupId) -> {
			System.out.println("url: " + url);
			System.out.println("input: " + input);
			System.out.println("groupId:" + groupId);
			return "[{\"customerId\":1, \"customerName\":\"wang\"}]";
		});
		
		//repository object
		CustomerRepository repository = (CustomerRepository) client.getRepository(CustomerRepository.class);
		
		//data
		Customer customer = new Customer(1, "Ken");
		List<Customer> cusList = new ArrayList<Customer>();
		cusList.add(customer);
		
		//repository call
//		customer = repository.add(customer);
		cusList = repository.add(cusList);
		
//		repository.remove(10);
//		repository.remove(cusList);
		
//		customer = repository.get(1);
		
		cusList = repository.getCustomersByNameOrId(1, "ken");
		
		cusList = repository.getCustomers();
		
	}
	
	/*
	 * Test Query Proxy
	 */
	private void testQuery() {
		//client
		PersistenceClient client = new PersistenceClient( (url, input, groupId) -> {
			System.out.println("url: " + url);
			System.out.println("input: " + input);
			System.out.println("groupId:" + groupId);
			return "[{\"customerId\":1, \"customerName\":\"wang\"}, {\"customerId\":2, \"customerName\":\"eric\"}]";
		});
		
		
		//query interface
		CustomerQuery query = (CustomerQuery) client.getQuery(CustomerQuery.class);
		
		//query call
		List<Customer>cusList;
		cusList = query.getCustomersByName("ken");
		System.out.println("cusList: " + cusList.toString());
		cusList = query.getCustomers();
		System.out.println("cusList: " + cusList.toString());
	}
	
	private void testCommand() {
		//client
		PersistenceClient client = new PersistenceClient( (url, input, groupId) -> {
			System.out.println("url: " + url);
			System.out.println("input: " + input);
			System.out.println("groupId:" + groupId);
			return "{}";
		});
		
		//command interface
		CustomerCommand command = (CustomerCommand) client.getCommand(CustomerCommand.class);
		
		//command call
		command.setGroupId("prst8787473847");
		command.removeAllCustomers();
	}
	
	private void testServiceGroup() {
		//client
		PersistenceClient client = new PersistenceClient( (url, input, groupId) -> {
			System.out.println("url: " + url);
			System.out.println("input: " + input);
			System.out.println("groupId:" + groupId);
			return "{\"groupId\": \"prst000000\"}";
		});
		
		//ServiceGroup interface
		ServiceGroup group = (ServiceGroup)client.getServiceGroup();
		
		//ServiceGroup call
		String groupId = group.create();
		System.out.println("groupId: " + groupId);
		
		group.commit(groupId);
		group.cancel(groupId);
		
	}
	
	private void test5(String groupId) {
		PersistenceClient client = new PersistenceClient("http://localhost/prst/app/");
		CustomerRepository repository = (CustomerRepository) client.getRepository(CustomerRepository.class);
		repository.setGroupId(groupId);
		
		
		Customer customer = new Customer(1, "Ken");
		customer = repository.add(customer);
	}
	
	
	public static void main(String[] args) {
		TestPersistenceClient test = new TestPersistenceClient();
//		test.testRepository();		
//		test.testQuery();		
//		test.testCommand();		
		test.testServiceGroup();		
//		test.test5("prst0000001");
//		test.test5("prst0000002");			
	}
	
}
