package com.iba.drp.result;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public Result setHttpCode(HttpCode resultCode) {
        this.code = resultCode.value();
        this.msg = resultCode.msg();
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
