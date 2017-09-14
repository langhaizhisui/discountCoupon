package cn.lhzs.common.aop.push;

/**
 * @author sonic.liu on 2017/8/30.
 * @descp
 */
public enum PushOrderEnum {

    ORDER_CREATED(1, "下单"),

    DELIVERED(2, "已发货"),

    ORDER_FINISHED(3, "订单已完成");

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

    PushOrderEnum(int code, String descp){
        this.code = code;
        this.descp = descp;
    }

    public static  String getCnDescp(int code){
        for ( PushOrderEnum op: values()) {
            if(op.getCode() == code)
                return op.getDescp();
        }
        return "未知";
    }
}
