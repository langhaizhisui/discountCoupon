package cn.lhzs.common.aop.log;

/**
 * @author sonic.liu on 2017/7/12.
 * @descp 操作成功或失败枚举
 */
public enum OpResultEnum {
    NO(0), YES(1);

    private  int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    OpResultEnum(int code){
        this.code = code;
    }
}
