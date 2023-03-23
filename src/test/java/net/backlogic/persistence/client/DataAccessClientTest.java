package net.backlogic.persistence.client;

import net.backlogic.persistence.client.classicmodel.*;
import net.backlogic.persistence.client.handler.MockServiceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
Test query, command and repository interfaces, to assert that Access client can
 - formulate the right service URL
 - formulate right input
 - invoke the right service handler method
 */
public class DataAccessClientTest {
	private static String baseUrl = "http://localhost/acct/app/";
	private static DataAccessClient client;
	private static MockServiceHandler handler;

	@BeforeAll
	public static void setUp() throws Exception {
		handler = new MockServiceHandler(baseUrl);
		client = new DataAccessClient(handler);
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
	public void testQuery() {
		// get query proxy
		ClassicQuery query = (ClassicQuery) client.getQuery(ClassicQuery.class);

		// prepare service handler
		String url = baseUrl + "queries/getCustomer";
		Customer customer = new Customer(101);
		handler.addOutput(url, customer);

		// test url and invocation
		Customer returnedCustomer = query.getCustomer(1234);
		assertNotNull(returnedCustomer);
		assertEquals(customer.getCustomerNumber(), returnedCustomer.getCustomerNumber());

		// test input - single primitive
		Object returnedInput;
		query.getCustomer(1234);
		returnedInput = handler.getInput(url);
		assertNotNull(returnedInput);
		assumeTrue(returnedInput instanceof Map);
		assertEquals(1234, ((Map)returnedInput).get("customerNumber"));

		// test input - multiple primitives
		query.getCustomer("CA", "Los Angeles", "90083");
		returnedInput = handler.getInput(url);
		assertNotNull(returnedInput);
		assumeTrue(returnedInput instanceof Map);
		assertEquals("CA", ((Map)returnedInput).get("state"));
		assertEquals("Los Angeles", ((Map)returnedInput).get("city"));
		assertEquals("90083", ((Map)returnedInput).get("postalCode"));

		// test input - single object
		Order order = new Order(100L);
		query.getCustomer(order);
		returnedInput = handler.getInput(url);
		assertNotNull(returnedInput);
		assumeTrue(returnedInput instanceof Order);
		assertEquals(100L, ((Order)returnedInput).getOrderNumber());

		// test input - multiple objects
		Payment payment = new Payment(1234);
		query.getCustomer(order, payment);
		returnedInput = handler.getInput(url);
		assertNotNull(returnedInput);
		assumeTrue(returnedInput instanceof Map);
		assertEquals(order, ((Map)returnedInput).get("order"));
		assertEquals(payment, ((Map)returnedInput).get("payment"));

		// test input - mixed objects and primitive
		query.getCustomer(1234, order, payment);
		returnedInput = handler.getInput(url);
		assertNotNull(returnedInput);
		assumeTrue(returnedInput instanceof Map);
		assertEquals(1234, ((Map)returnedInput).get("customerNumber"));
		assertEquals(order, ((Map)returnedInput).get("order"));
		assertEquals(payment, ((Map)returnedInput).get("payment"));

		// test another method of the interface
		url = baseUrl + "queries/getProductLines";
		List<ProductLine> productLines = new ArrayList<>();
		productLines.add(new ProductLine("NewLine"));
		handler.addOutput(url, productLines);
		Object output = query.getProductLines();
		assertNotNull(output);
		assertTrue(output instanceof List);
		assertTrue( ((List<?>) output).get(0) instanceof ProductLine);
		assertEquals("NewLine", ((ProductLine)((List<?>) output).get(0)).getProductLine()); ;
	}

	@Test
	public void testCommand() {
		// command proxy
		ClassicCommand command = (ClassicCommand)client.getCommand(ClassicCommand.class);

		// prepare service handler
		String url = baseUrl + "commands/duplicateProductLine";
		ProductLine productLine = new ProductLine("Boat");
		handler.addOutput(url, productLine);

		// test url and invocation
		ProductLine output = command.duplicateProductLine("Car", "Boat");
		assertNotNull(output);
		assertEquals("Boat", output.getProductLine());

		// test input
		Object input = handler.getInput(url);
		assertTrue(input instanceof Map);
		assertEquals( "Car", ((Map)input).get("sourceProductLine"));

		// test another method
		url = baseUrl + "commands/removeCustomer";
		command.removeCustomer(1234);
		input = handler.getInput(url);
		assertNotNull(input);
		assertTrue(input instanceof Map);
		assertEquals( 1234, ((Map)input).get("customerNumber"));
	}

	@Test
	public void testRepository() {
		// repository proxy
		OrderRepository repository = (OrderRepository) client.getRepository(OrderRepository.class);

		// prepare
		String url = baseUrl + "repositories/order";
		Order order = new Order(100L);
		List<Order> orders = new ArrayList();
		orders.add(order);
		Object input = null;
		Object output = null;

		// test read
		handler.addOutput(url + "/read", orders);
		output = repository.getOrdersByCustomer(1234);
		assertEquals(orders, output);
		handler.addOutput(url + "/read", order);
		output = repository.getOrderById(100);
		assertEquals(order, output);

		// test create
		handler.addOutput(url + "/create", order);
		output = repository.create(order);
		assertEquals(order, output);

		// test update
		handler.addOutput(url + "/update", order);
		output = repository.update(order);
		assertEquals(order, output);

		//delete
		repository.delete(order);
		input = handler.getInput(url + "/delete");
		assertEquals(order, input);
		repository.delete(1234);
		input = handler.getInput(url + "/delete");
		assertNotNull(input);
		assertTrue(input instanceof Map);
		assertEquals(1234, ((Map)input).get("orderNumber") );

		// test save
		handler.addOutput(url + "/save", orders);
		output = repository.save(orders);
		assertEquals(orders, output);
		handler.addOutput(url + "/save", order);
		output = repository.save(order);
		assertEquals(order, output);

		// test merge
		handler.addOutput(url + "/merge", order);
		output = repository.merge(order);
		assertEquals(order, output);
	}

}
