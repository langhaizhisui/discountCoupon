package cn.lhzs.common.aop.report;

/**
 * @author sonic.liu on 2017/8/28.
 * @descp 方便导出枚举
 */
public enum ReportTypeEnum {

    SALE(1, "销售统计"),

    STORE(2, "库存统计"),

    PURCHASE_REQ(3, "采购申请"),

    PURCHASE_ORDER(4, "采购单"),

    ALERT(5, "告警统计");


    private int code;
    private String descp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    ReportTypeEnum(int code, String descp){
        this.code = code;
        this.descp = descp;
    }

    public static  String getCnDescp(int code){
        for ( ReportTypeEnum op: values()) {
            if(op.getCode() == code)
                return op.getDescp();
        }
        return "未知";
    }

}
