package net.backlogic.persistence.client;

public interface MockFactoryBean {
	public Object getObject();
	public Class<?> getObjectType();
	public boolean isSingleton();
}
