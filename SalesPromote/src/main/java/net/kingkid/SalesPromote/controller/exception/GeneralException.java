package net.kingkid.SalesPromote.controller.exception;

/**
 * 上传的文件为空的异常
 */
public class GeneralException 
	extends Exception {

	private static final long serialVersionUID = 3888506555536311435L;

	public GeneralException() {
		super();
	}

	public GeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GeneralException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralException(String message) {
		super(message);
	}

	public GeneralException(Throwable cause) {
		super(cause);
	}

}
