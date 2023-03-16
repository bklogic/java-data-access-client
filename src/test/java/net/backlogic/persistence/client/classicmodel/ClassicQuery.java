package net.backlogic.persistence.client.classicmodel;

import java.util.List;

import net.backlogic.persistence.client.annotation.QueryService;
import net.backlogic.persistence.client.annotation.Query;

@QueryService("/queries")
public interface ClassicQuery {
	/*
	 * Get product lines along with products.
	 */
	@Query("getProductLines")
	public List<ProductLine> getProductLines();
	
	/*
	 * Get customer along with orders and payments info.
	 */
	@Query("getCustomer")
	public Customer getCustomer(int customerNumber);
		
	/*
	 * get all employees, along with their office, manager, and customers they are responsible for.
	 */
	@Query("getEmployee")
	public List<Employee> getEmployees();
}
