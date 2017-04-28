package cn.lhzs.web.result;

/**
 * Created by ZHX on 2017/1/5.
 */
public class RequestResult {

    /**
     * code : 200
     * msg : 获取设备信息成功
     * data : {}
     */

    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
