package cn.lhzs.web.exception;

/**
 * 登录异常
 * @author zhx
 */
public class LoginException extends BaseException {
	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, Exception e) {
		super(message, e);
	}

}
