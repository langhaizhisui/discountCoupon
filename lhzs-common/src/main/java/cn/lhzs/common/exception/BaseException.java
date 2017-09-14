package cn.lhzs.common.exception;

import cn.lhzs.common.result.HttpCode;
import cn.lhzs.common.result.Result;
import cn.lhzs.common.util.StringUtil;
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
