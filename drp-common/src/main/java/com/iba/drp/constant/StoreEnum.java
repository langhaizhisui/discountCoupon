package com.iba.drp.constant;

import java.util.Arrays;

/**
 * @author sonic.liu on 2017/8/9.
 * @descp 仓库枚举，目前只有代理仓和公司o2o仓库
 */
public enum StoreEnum {

    O20(0, "总仓"),

    /**  代理仓*/
    AGENT(1, "代理仓"),

    /**  o2o总仓*/
    NONE(2, "不限");

    StoreEnum(Integer code, String descp){
        this.code = code;
        this.descp = descp;
    }

    private Integer code;
    private String descp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public static StoreEnum getStore(Integer type){
      return   Arrays.stream(values()).filter(storeEnum -> storeEnum.getCode().equals(type)).findAny().orElse(NONE);
    }
}
