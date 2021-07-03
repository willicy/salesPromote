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
	}

	public ConcurentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConcurentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConcurentException(String message) {
		super(message);
	}

	public ConcurentException(Throwable cause) {
		super(cause);
	}

}
