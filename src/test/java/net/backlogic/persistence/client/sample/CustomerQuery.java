package net.backlogic.persistence.client.sample;

import java.util.List;

import net.backlogic.persistence.client.PersistenceInterface;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Url;

@Query("/mod")
public interface CustomerQuery extends PersistenceInterface {
	@Url("/getCustomersByName")
	public List<Customer> getCustomersByName(String name);
	
	@Url("/getCustomers")
	public List<Customer> getCustomers();
	
}

