/**
 *
 */
package net.backlogic.persistence.client;

/**
 * A runtime exception for data access client.
 */
@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {
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
	 * exception name
	 */
	String name;

	public DataAccessException() {
	}

	/**
	 * @param name    exception name
	 * @param message exception message
	 */
	public DataAccessException(String name, String message) {
		super(message);
		this.name = name;
	}

	/**
	 * @param name  exception name
	 * @param cause exception cause
	 */
	public DataAccessException(String name, String message, Throwable cause) {
		super(message + " >> " + cause.getMessage(), cause);
		this.name = name;
	}

}
