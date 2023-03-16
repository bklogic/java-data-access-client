package net.backlogic.persistence.client;

import net.backlogic.persistence.client.classicmodel.*;
import net.backlogic.persistence.client.handler.MockServiceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersistenceClientTest {
	
	private static PersistenceClient client;
	private static MockServiceHandler handler;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		handler = new MockServiceHandler("http://localhost/acct/app");
		client = new PersistenceClient(handler);
	}
	

	private Order getOrder() {
		Order order = new Order();
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		details.add(new OrderDetail());
		details.add(new OrderDetail());
		order.setOrderDetails(details);
		return order;
	}
	private List<Order> getOrderList(){
		List<Order> orders = new ArrayList<Order>();
		orders.add(getOrder());
		orders.add(getOrder());
		return orders;
	}		
	@Test
	public void testOrderRepository() {
		Order order; List<Order> orders; String input;
		OrderRepository repository = (OrderRepository) client.getRepository(OrderRepository.class);
		
		//read
		handler.setOutput("/repositories/order/read", "[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}]");
		orders = repository.getOrdersByCustomer(123);
		assertEquals("/repositories/order/read", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"customerNumber\":123}", input);
		assertEquals(true, orders.size()>0);
		assertEquals(true, orders.get(0) instanceof Order);
		
		//save
		handler.setOutput("/repositories/order/save", "[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}]");
		orders = getOrderList();
		orders = repository.save(orders);
		assertEquals("/repositories/order/save", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}]", input);
		assertEquals(true, orders.size()>0);
		assertEquals(true, orders.get(0) instanceof Order);
				
		//create
		handler.setOutput("/repositories/order/create", "{\"orderNumber\":1234,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}");
		order = getOrder();
		order = repository.create(order);
		assertEquals("/repositories/order/create", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}", input);
		assertNotNull(order);
		assertEquals((Long)1234L, order.getOrderNumber());
		
		//update
		handler.setOutput("/repositories/order/update", "{\"orderNumber\":1234,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}");
		order = getOrder();
		order = repository.update(order);
		assertEquals("/repositories/order/update", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}", input);
		assertNotNull(order);
		assertEquals((Long)1234L, order.getOrderNumber());	
		
		//delete
		handler.setOutput("/repositories/order/delete", "{}");
		order = getOrder();
		repository.delete(order);
		assertEquals("/repositories/order/delete", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}", input);

	}
		
	
	@Test
	public void testClassicQuery() {
		List<ProductLine> productLines; String input;
		ClassicQuery query = (ClassicQuery)client.getQuery(ClassicQuery.class);
		
		//get product lines
		handler.setOutput("/queries/getProductLines", "[{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]},{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]}]");
		productLines = query.getProductLines();
		assertEquals("/queries/getProductLines", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{}", input);
		assertEquals(true, productLines.size()>0 );
		assertEquals(true, productLines.get(0) instanceof ProductLine);		
		assertEquals(true, productLines.get(0).getProducts().get(0) instanceof Product);		
	}

	@Test
	public void testClassicQuery2() {
		Customer customer; String input;
		ClassicQuery query = (ClassicQuery)client.getQuery(ClassicQuery.class);
		
		//get product lines
		handler.setOutput("/queries/getCustomer", "{\"customerNumber\":null,\"customerName\":null,\"contactLastName\":null,\"contactFirstName\":null,\"phone\":null,\"addressLine1\":null,\"addressLine2\":null,\"city\":null,\"state\":null,\"postalCode\":null,\"country\":null,\"creditLimit\":null,\"orders\":[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}],\"payments\":[{\"customerNumber\":null,\"customerName\":null,\"checkNumber\":null,\"paymentDate\":null,\"amount\":null}]}");
		customer = query.getCustomer(1234);
		assertEquals("/queries/getCustomer", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"customerNumber\":1234}", input);
		assertEquals(true, customer.getOrders().size()>0 );
		assertEquals(true, customer.getOrders().get(0) instanceof Order);
		assertEquals(true, customer.getPayments().size()>0 );
		assertEquals(true, customer.getPayments().get(0) instanceof Payment);
	}

	@Test
	public void testClassicCommand() {
		String input;
		ClassicCommand command = (ClassicCommand)client.getCommand(ClassicCommand.class);
		
		//get product lines
		handler.setOutput("/commands/duplicateProductLine", "{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]},{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]}");
		command.duplicateProductLine("Car", "Boat");
		assertEquals("/commands/duplicateProductLine", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"sourceProductLine\":\"Car\",\"targetProductLine\":\"Boat\"}", input);
	}	
	
	@Test
	public void testClassicCommand2() {
		String input;
		ClassicCommand command = (ClassicCommand)client.getCommand(ClassicCommand.class);
		
		//get product lines
		handler.setOutput("/commands/removeCustomer", "{}");
		command.removeCustomer(1234);
		assertEquals("/commands/removeCustomer", handler.getServiceUrl());
//		input = handler.getServiceInput();
//		assertEquals("{\"customerNumber\":1234}", input);
	}	
}
