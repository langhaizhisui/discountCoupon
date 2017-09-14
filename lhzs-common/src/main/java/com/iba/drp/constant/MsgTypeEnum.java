package com.iba.drp.constant;

/**
 * @author sonic.liu on 2017/8/10.
 * @descp 短信、验证码、邮件等信息类型枚举
 */
public enum MsgTypeEnum {
    // 手机短信验证码类型 --------------------------------begin-------------

    /** 配货/采购订单发货 */
    MSG_ORDER_SEND("orderSend", "尊敬的店主/代理，您的【%s订单（XXX）】已出库发货，正在配送中，物流：%s，单号：%s，如有疑问请致电:400-883-1959"),

    /** 采购订单通过审核短信 */
    MSG_ORDER_AUDIT_PASS("orderAuditPass", "尊敬的店主/代理，您的采购单(%s)申请已通过审核，将会在1-3个工作日为您发货，请及时查收发货短信，如有疑问请致电:400-883-1959"),

    /** 采购订单没有通过审核短信 */
    MSG_ORDER_AUDIT_NOPASS("orderAuditNoPass", "尊敬的店主/代理，您的采购单(%s)申请审核不通过，我们深感抱歉，如有疑问请致电:400-883-1959"),

    /** 用户修改密码成功短信 */
    MSG_USER_EDIT_PWD("userEditPwd", "尊敬的%s，您的帐号于%s修改密码成功，如有疑问请致电:400-883-1959"),

    /** 代理商/门店 用户创建短信 */
    MSG_USER_ADD("userAdd", "尊敬的%s，您的帐号已创建成功，登录账户：%s，原始密码：%s，请尽快登录并修改密码"),

    /** 短信验证码 */
    MSG_USER_SEND_VCODE("userVcode", "您好，您的短信验证码为:%s"),

    /** 用户更新 */
    MSG_USER_PHONE_UPDATE("userPhoneUpdate", "尊敬的%s，您的帐号于%s变更帐号成功，原帐号将无法登录，新的登录帐号为%s，原始密码：%s，请尽快登录并修改密码，如有疑问请致电：400-883-1959"),

    // 手机短信验证码类型 --------------------------------end-------------

    /** 手机短信验证码redis存储相关 */
    PHONE_RESET("phoneReset_", "密码重置验证码"),

    PHONE_VCODE("phoneVcode_", "手机验证码");

    MsgTypeEnum(String key, String text) {
        this.key = key;
        this.text = text;
    }

    private String key;
    private String text;

    public String key() {
        return key;
    }

    public String text() {
        return text;
    }
}
