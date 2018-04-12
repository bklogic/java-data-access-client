/**
 * 
 */
package net.backlogic.persistent.client;

/**
 * @author Ken
 *
 */
public class Customer {
	private int customerId;
	private String customerName;
	
	public Customer() {
	}
	public Customer(int customerId, String customerName) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
