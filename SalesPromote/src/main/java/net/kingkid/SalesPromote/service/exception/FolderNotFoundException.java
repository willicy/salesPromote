package net.kingkid.SalesPromote.service.exception;

/**
 * 用户数据不存在
 */
public class FolderNotFoundException 
	extends ServiceException {

	private static final long serialVersionUID = -2470715991347231448L;

	public FolderNotFoundException() {
		super();
	}

	public FolderNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FolderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FolderNotFoundException(String message) {
		super(message);
	}

	public FolderNotFoundException(Throwable cause) {
		super(cause);
	}

}
