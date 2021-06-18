package net.kingkid.SalesPromote.service.exception;

/**
 * 收货地址数据不存在
 *
 */
public class AddressNotFoundException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6384346570554219230L;

	public AddressNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
