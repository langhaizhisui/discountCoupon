package com.iba.drp.constant;

/**
 * 供采购、配货列表搜索使用的枚举类型-采购单（0-不限，1-我的采购单，2-下级的采购单）
 * Created by Liguoxing on 2017/8/26.
 */
public enum PurchaseOrDistributionEnum {

    SHOP_PURCHASE(0,"门店采购"),

    AGENCY_PURCHASE(1,"代理采购"),

    SELF_PURCHASE(2,"我的采购");

    PurchaseOrDistributionEnum(int code ,String descp){
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
}
