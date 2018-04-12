package net.backlogic.persistent.client;

import java.util.List;

import net.backlogic.persistence.client.PersistenceInterface;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Service;

@Query
public interface CustomerQuery extends PersistenceInterface {
	@Service("mod/getCustomersByName")
	public List<Customer> getCustomersByName(String name);
	
	@Service("mod/getCustomers")
	public List<Customer> getCustomers();
}

