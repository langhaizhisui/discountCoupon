package cn.lhzs.common.support.sms;


import cn.lhzs.common.constant.MsgTypeEnum;

/**
 * 短信发送消息常量类
 */
public final class MessageConstant {

    private MessageConstant() {
    }

    /**
     * 短信抬头
     */
    public static final String TOPIC = "【洋老板DRP】";

    // 各种验证
    public static String exCode(MsgTypeEnum msgType, String... code) {
        return TOPIC + String.format(msgType.text(), code);
    }
}
