/**
 * 
 */
package net.backlogic.persistent.client;

import java.util.List;

import net.backlogic.persistence.client.PersistenceInterface;
import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Repository;

/**
 * @author Ken
 *
 */
@Repository("mod/Customer")
public interface CustomerRepository extends PersistenceInterface{
	@Read
	public Customer get(int customerId);
	
	@Create
	public Customer add(Customer customer);	
	
	@Create
	public List<Customer> add(List<Customer> customers);

	@Delete
	public void remove(List<Customer> customers);

	@Delete
	public void remove(int customerId);

	@Read
	public List<Customer> getCustomersByNameOrId(int id, String name);
	
	@Read
	public List<Customer> getCustomers();
}
