package cn.lhzs.common.constant;

/**
 * @author sonic.liu on 2017/8/14.
 * @descp 采购请求状态枚举
 */
public enum PurchaseReqTypeEnum {
    DISTRIBUTION(1, "配货"),

    APPLICATION(2, "采购申请");

    private Integer code;
    private String descp;

    PurchaseReqTypeEnum(Integer code, String descp){
        this.code = code;
        this.descp = descp;
    }

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
