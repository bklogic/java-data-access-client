package net.backlogic.persistence.client.classicmodel;

import net.backlogic.persistence.client.annotation.CommandService;
import net.backlogic.persistence.client.annotation.Query;

@CommandService("/commands")
public interface ClassicCommand {
	
	/*
	 * clone a source product line
	 */
	@Query("duplicateProductLine")
	void duplicateProductLine(String sourceProductLine, String targetProductLine);
	
	/*
	 * Remove customer, along with orders and payments
	 */
	@Query("removeCustomer")
	void removeCustomer(Integer customerNumber);
}
