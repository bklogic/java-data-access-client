package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Parameter;

import net.backlogic.persistence.client.handler.InputType;
import net.backlogic.persistence.client.handler.ReturnType;

public class ServiceMethod {
	/**
	 * Url of service
	 */
	private String serviceUrl;
	/**
	 * Return type of service method
	 */
	private ReturnType returnType;
	private Class<?> elementType;
	private InputType inputType;
	/**
	 * Input parameters of service method
	 */
	private Parameter[] inputParams;
	/**
	 * Return mapping of service method. For BatchService only.
	 */
	private String returnMapping;

	public ServiceMethod(String serviceUrl, ReturnType returnType, Class<?> elementType,
						 InputType inputType, Parameter[] inputParams, String returnMapping) {
		this.serviceUrl = serviceUrl;
		this.returnType = returnType;
		this.elementType = elementType;
		this.setInputType(inputType);
		this.setInputParams(inputParams);
		this.returnMapping = returnMapping;
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

	public String getReturnMapping() {
		return returnMapping;
	}

	public void setReturnMapping(String returnMapping) {
		this.returnMapping = returnMapping;
	}
}
