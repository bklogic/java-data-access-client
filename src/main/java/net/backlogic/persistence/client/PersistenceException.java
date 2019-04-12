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
	public static final String ServiceException = "ServiceException";
	/**
	 * exception type: system. for any unexpected exception
	 */
	public static final String SystemException = "SystemException";
	/**
	 * exception type: system. for any unexpected exception
	 */
	public static final String InputException = "InputException";
	/**
	 * exception type: system. for any unexpected exception
	 */
	public static final String JsonException = "JsonException";
	/**
	 * exception type: interface. for issue with persistence interface
	 */
	public static final String InterfaceException = "InterfaceException";
	/**
	 * exception type: http. for issue with http call
	 */
	public static final String HttpException = "HttpException";

	
	/**
	 * exception type
	 */
	String type;
	/**
	 * exception name
	 */
	String name;
	
	
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
	 * @param type	exception type
	 * @param name	exception name
	 * @param cause	exception cause
	 */
	public PersistenceException(String type, String name, Throwable cause) {
		super(cause);
		this.type = type;
		this.name = name;
	}

}
