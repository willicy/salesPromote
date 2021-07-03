package net.kingkid.SalesPromote.service.exception;

/**
 * 访问被拒异常
 * @author willicy
 *
 */
public class AccessDeniedException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2789666808349129147L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

}
