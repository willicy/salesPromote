package net.kingkid.SalesPromote.service.exception;

/**
 * 购物车数据不存在异常
 *
 */
public class CartNotFoundException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1445408879848594743L;

	public CartNotFoundException() {
		super();
	}

	public CartNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CartNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CartNotFoundException(String message) {
		super(message);
	}

	public CartNotFoundException(Throwable cause) {
		super(cause);
	}

}
