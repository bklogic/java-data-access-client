/**
 * 
 */
package net.backlogic.persistence.client;

/**
 * @author Ken
 * Created on 10/20/2017
 */
@SuppressWarnings("serial")
public class PersistenceException extends RuntimeException  {
	/**
	 * exception type: service. For any invalid service condition
	 */
	static final String ServiceException = "ServiceException";
	/**
	 * exception type: system. for any unexpected exception
	 */
	static final String SystemException = "SystemException";
	/**
	 * exception type: system. for any unexpected exception
	 */
	static final String InputException = "InputException";
	/**
	 * exception type: interface. for issue with persistence interface
	 */
	static final String InterfaceException = "InterfaceException";
	/**
	 * exception type: http. for issue with http call
	 */
	static final String HttpException = "HttpException";

	
	/**
	 * exception type
	 */
	String type;
	/**
	 * exception name
	 */
	String name;
	/**
	 * exception detail
	 */
	String detail;
	
	
	/**
	 * 
	 * @param type	exception type
	 * @param name	exception name
	 * @param message	exception message
	 */
	public PersistenceException(String type, String name, String message) {
		super(message);
		this.type = type;
		this.name = name;
	}

	
	/**
	 * 
	 * @param type	exception type
	 * @param name	exception name
	 * @param message	exception message
	 * @param detail	exception detail
	 */
	public PersistenceException(String type, String name, String message, String detail) {
		super(message);
		this.type = type;
		this.name = name;
		this.detail = detail;
	}

	
	/**
	 * @param type	exception type
	 * @param name	exception name
	 * @param message	exception message
	 * @param cause	exception cause
	 */
	public PersistenceException(String type, String name, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
		this.name = name;
	}

}
