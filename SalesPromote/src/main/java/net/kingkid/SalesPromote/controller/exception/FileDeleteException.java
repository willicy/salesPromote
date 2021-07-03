package net.kingkid.SalesPromote.controller.exception;

/**
 * 文件删除异常
 */
public class FileDeleteException 
	extends RequestException {

	private static final long serialVersionUID = -9094756497477494598L;

	public FileDeleteException() {
		super();
	}

	public FileDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileDeleteException(String message) {
		super(message);
	}

	public FileDeleteException(Throwable cause) {
		super(cause);
	}

}
