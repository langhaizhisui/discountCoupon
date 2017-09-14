package cn.lhzs.common.aop.lock;

/**
 * Created by IBA-EDV on 2017/9/9.
 */
public enum FormActionEnum {
    REQ_COMMIT(1,"提交申请"),
    REQ_RECEIPT(2,"确认收款");
    private Integer code;
    private String descp;

    FormActionEnum(Integer code,String desp){
        this.code = code;
        this.descp = desp;
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

    public static  String getCnDescp(int code){
        for ( FormActionEnum op: values()) {
            if(op.getCode() == code)
                return op.getDescp();
        }
        return "未知";
    }
}
