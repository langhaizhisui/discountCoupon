package cn.lhzs.common.support.sms;

import java.io.Serializable;

/**
 * 短信发送
 */
@SuppressWarnings("serial")
public class PhoneMsg implements Serializable {
    private String phone;
    private String message;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
