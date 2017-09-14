package cn.lhzs.common.constant;

/**
 * @author sonic.liu on 2017/8/14.
 * @descp 采购请求状态枚举
 */
public enum PurchaseReqStatusEnum {

    DRAFT(0, "已保存"),

    AUDITING(1, "待审核"),

    AUDIT_PASS(2, "审核通过"),

    AUDIT_NO_PASS(3, "审核不通过"),

    UNPAID(4,"待付款"),

    PAYMENT(5, "已付款"),

    RECEIVED_PAY(6, "已收款"),

    CANCELED(7, "已取消");


    private Integer code;
    private String descp;

    PurchaseReqStatusEnum(Integer code, String descp){
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
