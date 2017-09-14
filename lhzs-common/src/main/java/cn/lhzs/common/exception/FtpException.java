package cn.lhzs.common.exception;

/**
 * FTP异常
 *
 * @author sonic.liu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FtpException extends RuntimeException {
	public FtpException() {
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
