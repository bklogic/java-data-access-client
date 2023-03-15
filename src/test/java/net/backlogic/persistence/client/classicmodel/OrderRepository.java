package net.backlogic.persistence.client.classicmodel;

import java.util.List;

import net.backlogic.persistence.client.annotation.BacklogicRepository;
import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;

@BacklogicRepository("/repositories/order")
public interface OrderRepository {
	
	@Create
	Order create(Order order);
	
	@Update
	Order update(Order order);
	
	@Delete
	void delete(Order order);
	
	@Save
	List<Order> save(List<Order> orders);
	
	@Read
	public Order getOrderById(Integer orderNumber);
	
	@Read
	public List<Order> getOrdersByCustomer(Integer customerNumber);
	
}
