package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.handler.ReturnType;

public class ServiceMethod {
	private String serviceUrl;
	private ReturnType returnType;
	private Class<?> elementType;
	
	
	public ServiceMethod(String serviceUrl, ReturnType returnType, Class<?> elementType) {
		this.serviceUrl = serviceUrl;
		this.returnType = returnType;
		this.elementType = elementType;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public ReturnType getReturnType() {
		return returnType;
	}
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}
	public Class<?> getElementType() {
		return elementType;
	}
	public void setElementType(Class<?> elementType) {
		this.elementType = elementType;
	}
}
