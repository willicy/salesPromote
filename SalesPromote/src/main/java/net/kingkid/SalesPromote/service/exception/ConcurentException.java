package net.kingkid.SalesPromote.service.exception;

/**
 * 并发异常
 * @author willicy
 *
 */
public class ConcurentException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2789666808349129147L;

	public ConcurentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConcurentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ConcurentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ConcurentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConcurentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
