package com.iba.drp.exception;

import com.iba.drp.result.HttpCode;
import com.iba.drp.result.Result;
import com.iba.drp.util.StringUtil;
import org.springframework.ui.ModelMap;

/**
 *
 */
@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}

	public void handler(Result result) {
		result.setCode(getHttpCode().value());
		if (StringUtil.isNotBlank(getMessage())) {
			result.setMsg(getMessage());
		} else {
			result.setMsg(getHttpCode().msg());
		}
	}

	protected abstract HttpCode getHttpCode();
}
