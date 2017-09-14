package cn.lhzs.common.exception;

/**
 * 
 * 业务异常
 * @author sonic.liu
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
	public BusinessException() {
	}

	public BusinessException(Throwable ex) {
		super(ex);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable ex) {
		super(message, ex);
	}

}