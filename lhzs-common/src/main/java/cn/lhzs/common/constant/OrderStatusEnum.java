package cn.lhzs.common.constant;

/**
 * @author sonic.liu on 2017/8/14.
 * @descp 订单状态枚举
 */
public enum OrderStatusEnum {

    PENDING_DELIVERY(1, "待发货"),

    DELIVERED(2, "已发货"),

    FINISHED(3, "已完成"),

    IN_STORE(4, "已入库");

    private Integer code;
    private String descp;

    OrderStatusEnum(Integer code, String descp){
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
