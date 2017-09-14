package com.iba.drp.result;

import com.iba.drp.config.Resources;

/**
 * Ajax 请求时的自定义查询状态码，主要参考Http状态码，但并不完全对应
 */
public enum HttpCode {
	/** 200请求成功 */
	OK(200),
	/** 202请求失败 */
	FAIL(202),
	/** 207频繁操作 */
	MULTI_STATUS(207),
	/** 303登录失败 */
	LOGIN_FAIL(303),
	/** 400请求参数出错 */
	BAD_REQUEST(400),
	/** 401没有登录 */
	UNAUTHORIZED(401),
	/** 403没有权限 */
	FORBIDDEN(403),
	/** 404找不到页面 */
	NOT_FOUND(404),
	/** 库存不足 */
	UNDERSTOCK(405),
	/** 408请求超时 */
	REQUEST_TIMEOUT(408),
	/** 409发生冲突 */
	CONFLICT(409),
	/** 410已被删除 */
	GONE(410),
	/** 423已被锁定 */
	LOCKED(423),

	/** 义务处理异常 */
	BUSINESS_ERROR(431),

	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR(500),

	/**账号不能为空*/
	ACCOUNT_IS_NULL_ERROR(600),
	/**密码不能为空*/
	PASSWORD_IS_NULL_ERROR(601),
	/**验证码失效*/
	CODE_TIMEOUT_ERROR(602),
	/**手机不能为空*/
	PHONE_IS_NULL_ERROR(603),
	/**验证码错误*/
	CODE_INVALID_ERROR(604),
	/**
	 * 销售订单导单错误
	 */
	SALE_ORDER_DOWNLOAD_ERROR(605),
	/**
	 * 手机号码格式错误
	 */
	PHONE_ERROR(606);

	private final Integer value;


	HttpCode(Integer value) {
		this.value = value;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public Integer value() {
		return this.value;
	}


	public String msg() {
		return Resources.getMessage("HTTPCODE_" + this.value);
	}


	public String toString() {
		return this.value.toString();
	}
}
