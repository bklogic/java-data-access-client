package net.backlogic.persistence.client.proxy;

public class BatchedInvocation {
	private String name;
	private String service;
	private Object input;
	
	public BatchedInvocation(String name, String service, Object input) {
		super();
		this.name = name;
		this.service = service;
		this.input = input;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}
	
}
