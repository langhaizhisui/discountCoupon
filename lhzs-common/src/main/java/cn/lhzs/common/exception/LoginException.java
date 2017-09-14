package cn.lhzs.common.exception;

import cn.lhzs.common.result.HttpCode;

/**
 * 登录异常
 * @author sonic.liu
 */
@SuppressWarnings("serial")
public class LoginException extends BaseException {
	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, Exception e) {
		super(message, e);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.LOGIN_FAIL;
	}
}
