package net.kingkid.SalesPromote.service.exception;

/**
 * 数量超出限制
 * @author willicy
 *
 */
public class QuantityOutOfLimitException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2789666808349129147L;

	public QuantityOutOfLimitException() {
		super();
	}

	public QuantityOutOfLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QuantityOutOfLimitException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuantityOutOfLimitException(String message) {
		super(message);
	}

	public QuantityOutOfLimitException(Throwable cause) {
		super(cause);
	}

}
