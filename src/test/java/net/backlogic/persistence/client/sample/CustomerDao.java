package net.backlogic.persistence.client.sample;

import java.util.List;

import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Dao;
import net.backlogic.persistence.client.annotation.Query;

@Dao("/mod")
public interface CustomerDao {

	@Query("/getCustomersByName")
	public List<Customer> getCustomersByName(String name);
	
	@Query("/getCustomers")
	public List<Customer> getCustomers();
	
	@Command("/RemoveAllCustomers")
	public void removeAllCustomers();

}
