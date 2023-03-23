package net.backlogic.persistence.client.classicmodel;

import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.QueryService;

import java.util.List;

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


	/**
		Random methods for testing input composition
	 */

	/**
		multiple simple fields
	 */
	@Query("getCustomer")
	public Customer getCustomer(String state, String city, String postalCode);

	/**
	 * Single Object
	 */
	@Query("getCustomer")
	public Customer getCustomer(Order order);

	/**
	 * Multiple Object
	 */
	@Query("getCustomer")
	public Customer getCustomer(Order order, Payment payment);

	/**
	 * Mix of Objects and primitive
	 */
	@Query("getCustomer")
	public Customer getCustomer(int customerNumber, Order order, Payment payment);

}
