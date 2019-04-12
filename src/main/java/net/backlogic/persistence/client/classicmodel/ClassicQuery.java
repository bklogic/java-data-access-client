package net.backlogic.persistence.client.classicmodel;

import java.util.List;

import net.backlogic.persistence.client.annotation.BacklogicQuery;
import net.backlogic.persistence.client.annotation.Name;

@BacklogicQuery("/queries")
public interface ClassicQuery {
	/*
	 * Get product lines along with products.
	 */
	@Name("getProductLines")
	public List<ProductLine> getProductLines();
	
	/*
	 * Get customer along with orders and payments info.
	 */
	@Name("getCustomer")
	public Customer getCustomer(int customerNumber);
		
	/*
	 * get all employees, along with their office, manager, and customers they are responsible for.
	 */
	@Name("getEmployee")
	public List<Employee> getEmployees();
}
