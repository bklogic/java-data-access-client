package net.backlogic.persistence.client.handler;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import net.backlogic.persistence.client.classicmodel.Customer;
import net.backlogic.persistence.client.classicmodel.Order;
import net.backlogic.persistence.client.classicmodel.OrderDetail;
import net.backlogic.persistence.client.classicmodel.Payment;
import net.backlogic.persistence.client.classicmodel.Product;
import net.backlogic.persistence.client.classicmodel.ProductLine;

public class JsonHandlerImplTest {
	
	JsonHandler jsonHandler = new  JsonHandlerImpl();
	Object object; 
	String json;
	String expectedJson;
	
	@Test
	public void testNullToJson() {
		object = null;
		json = jsonHandler.toJson(object);
		System.out.println(json);
		Assert.assertEquals("{}", json);
		
		object = jsonHandler.toObject(json, Void.class);
		Assert.assertNull(object);
	}
	
	
	@Test
	public void testMapToJson() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", 1234);
		map.put("customerName", "ABC");
		map.put("startDate", getDate("2019-19-05T10:19:05.895Z"));
		map.put("icon", "ABCDEFG".getBytes());
		map.put("order", getOrder());
		json = jsonHandler.toJson(map);
		expectedJson = "{\"customerId\":1234,\"icon\":\"QUJDREVGRw==\",\"customerName\":\"ABC\",\"startDate\":\"2019-19-05T10:19:05.895Z\",\"order\":{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}}";
		System.out.println(json);
		Assert.assertEquals(expectedJson, json);		
	}
	private Date getDate(String s) {
		try {
			return new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSS'Z'").parse(s);
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	@Test
	public void testOrderToJson() {
		object = getOrder();
		json = jsonHandler.toJson(object);
		expectedJson = "{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}";
		System.out.println(json);
		Assert.assertEquals(expectedJson, json);
		
		object = jsonHandler.toObject(json, Order.class);
		Assert.assertNotNull(((Order)object).getOrderDetails());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOrdersToJson() {
		List<Order> orders = getOrderList();
		json = jsonHandler.toJson(orders);
		expectedJson = "[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}]";
		System.out.println(json);
		Assert.assertEquals(expectedJson, json);
		
		List<Order>orderList = (List<Order>) jsonHandler.toList(json, Order.class);
		Assert.assertEquals(true, orderList.get(0) instanceof Order);
		Assert.assertEquals(2, orders.size() );
		
		Set<Order>orderSet = (Set<Order>) jsonHandler.toSet(json, Order.class);
		Assert.assertEquals(true, orderSet.iterator().next() instanceof Order);
		Assert.assertEquals(2, orders.size() );
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProductLinesToJson() {
		List<ProductLine> productLines = getProductLines();
		json = jsonHandler.toJson(productLines);
		expectedJson = "[{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]},{\"productLine\":null,\"textDescription\":null,\"htmlDescription\":null,\"image\":null,\"products\":[{\"productCode\":null,\"productName\":null,\"productLine\":null,\"productScale\":null,\"productVendor\":null,\"productDescription\":null,\"quantityInStock\":null,\"buyPrice\":null,\"msrp\":null}]}]";
		System.out.println(json);
		Assert.assertEquals(expectedJson, json);
		
		productLines = (List<ProductLine>) jsonHandler.toList(json, ProductLine.class);
		Assert.assertEquals(true, productLines.size()>0 );
		Assert.assertEquals(true, productLines.get(0) instanceof ProductLine);
	}
	
	private ProductLine getProductLine() {
		ProductLine productLine = new ProductLine();
		List<Product> products = new ArrayList<Product>();
		products.add(new Product());
		productLine.setProducts(products);
		return productLine;
	}
	private List<ProductLine> getProductLines(){
		List<ProductLine> productLines = new ArrayList<ProductLine>();
		productLines.add(getProductLine());
		productLines.add(getProductLine());		
		return productLines;
	}

	@Test
	public void testCustomerToJson() {
		Customer customer = getCustomer();
		json = jsonHandler.toJson(customer);
		expectedJson = "{\"customerNumber\":null,\"customerName\":null,\"contactLastName\":null,\"contactFirstName\":null,\"phone\":null,\"addressLine1\":null,\"addressLine2\":null,\"city\":null,\"state\":null,\"postalCode\":null,\"country\":null,\"creditLimit\":null,\"orders\":[{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]},{\"orderNumber\":null,\"orderDate\":null,\"requiredDate\":null,\"shippedDate\":null,\"status\":null,\"comments\":null,\"customerNumber\":null,\"customerName\":null,\"orderDetails\":[{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null},{\"orderNumber\":null,\"productCode\":null,\"productName\":null,\"quantityOrdered\":null,\"priceEach\":null,\"orderLineNumber\":null}]}],\"payments\":[{\"customerNumber\":null,\"customerName\":null,\"checkNumber\":null,\"paymentDate\":null,\"amount\":null}]}";
		System.out.println(json);
		Assert.assertEquals(expectedJson, json);
		
		customer = (Customer) jsonHandler.toObject(json, customer.getClass());
		Assert.assertEquals(true, customer.getOrders().size()>0 );
		Assert.assertEquals(true, customer.getOrders().get(0) instanceof Order);
		Assert.assertEquals(true, customer.getPayments().size()>0 );
		Assert.assertEquals(true, customer.getPayments().get(0) instanceof Payment);
	}	
	
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setOrders(getOrderList());
		customer.setPayments(getPayments());
		return customer;
	}
    private List<Payment> getPayments() {
    	List<Payment> payments = new ArrayList<Payment>();
    	payments.add(new Payment());
    	return payments;
    }
	
}
