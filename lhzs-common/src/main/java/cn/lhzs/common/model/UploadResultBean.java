package cn.lhzs.common.model;

import java.io.Serializable;

/**
 * @author sonic.liu
 */
@SuppressWarnings("serial")
public class UploadResultBean implements Serializable {
    private int httpCode;
    private String msg;
    private int timestamp;
    private String imgName;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
