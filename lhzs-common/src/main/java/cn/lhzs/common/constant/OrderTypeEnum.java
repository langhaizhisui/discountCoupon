package cn.lhzs.common.constant;

/**
 * @author sonic.liu on 2017/8/23.
 * @descp 订单类型枚举
 */
public enum OrderTypeEnum {

    SALE_ORDER(1, "XS", "销售订单"),

    DISTRIBUTION_ORDER(2, "PH", "配货订单"),

    PURCHASE_ORDER(3, "CG", "采购订单"),

    APPLY_DISTRIBUTION_ORDER(4, "XQPH", "配货申请"),

    APPLY_PURCHASE_ORDER(5, "XQCG", "采购申请");

    private Integer code;
    private String sn;
    private String descp;


    OrderTypeEnum(Integer code, String sn, String descp){
        this.code = code;
        this.sn = sn;
        this.descp = descp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
