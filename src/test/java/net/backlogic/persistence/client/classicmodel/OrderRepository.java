package net.backlogic.persistence.client.classicmodel;

import net.backlogic.persistence.client.annotation.*;

import java.util.List;

@RepositoryService("/repositories/order")
public interface OrderRepository {
	
	@Create
	Order create(Order order);
	
	@Update
	Order update(Order order);
	
	@Delete
	void delete(Order order);

	@Delete
	void delete(int orderNumber);

	@Save
	List<Order> save(List<Order> orders);

	@Save
	Order save(Order order);

	@Merge
	Order merge(Order order);

	@Read
	public Order getOrderById(Integer orderNumber);
	
	@Read
	public List<Order> getOrdersByCustomer(Integer customerNumber);
	
}
