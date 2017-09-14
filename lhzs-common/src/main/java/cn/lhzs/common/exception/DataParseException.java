package cn.lhzs.common.exception;

import cn.lhzs.common.result.HttpCode;

/**
 * @author sonic.liu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DataParseException extends BaseException {

	public DataParseException() {
	}

	public DataParseException(Throwable ex) {
		super(ex);
	}

	public DataParseException(String message) {
		super(message);
	}

	public DataParseException(String message, Throwable ex) {
		super(message, ex);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.INTERNAL_SERVER_ERROR;
	}

}
