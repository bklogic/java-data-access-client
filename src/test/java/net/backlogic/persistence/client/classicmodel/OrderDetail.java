package net.backlogic.persistence.client.classicmodel;

public class OrderDetail {
	Long orderNumber;
	String productCode;
	String productName;
	Integer quantityOrdered;
	Float priceEach;
	Integer orderLineNumber;
	
	public Long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantityOrdered() {
		return quantityOrdered;
	}
	public void setQuantityOrdered(Integer quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}
	public Float getPriceEach() {
		return priceEach;
	}
	public void setPriceEach(Float priceEach) {
		this.priceEach = priceEach;
	}
	public Integer getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(Integer orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}	
}
