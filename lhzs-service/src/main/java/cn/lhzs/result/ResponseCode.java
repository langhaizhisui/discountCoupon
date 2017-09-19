package cn.lhzs.result;

/**
 * Created by ZHX on 2017/9/15.
 */
public enum ResponseCode {

    OK(200, "请求成功"),
    FAIL(500,"请求失败");

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

    ResponseCode(Integer code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public static ResponseCode get(Integer code){
        ResponseCode[] responseCodes = values();
        for (int i = 0; i < responseCodes.length; i++) {
            ResponseCode responseCode = responseCodes[i];
            if(responseCode.getCode().equals(code)){
                return responseCode;
            }
        }
        return null;
    }
}
