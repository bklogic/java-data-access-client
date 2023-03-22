package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Parameter;

import net.backlogic.persistence.client.handler.InputType;
import net.backlogic.persistence.client.handler.ReturnType;

public class ServiceMethod {
	private String serviceUrl;
	private ReturnType returnType;
	private Class<?> elementType;
	private InputType inputType;
	private Parameter[] inputParams;
	
	public ServiceMethod(String serviceUrl, ReturnType returnType, Class<?> elementType, 
			InputType inputType, Parameter[] inputParams) {
		this.serviceUrl = serviceUrl;
		this.returnType = returnType;
		this.elementType = elementType;
		this.setInputType(inputType);
		this.setInputParams(inputParams);
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
	public InputType getInputType() {
		return inputType;
	}
	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}
	public Parameter[] getInputParams() {
		return inputParams;
	}
	public void setInputParams(Parameter[] inputParams) {
		this.inputParams = inputParams;
	}
}
