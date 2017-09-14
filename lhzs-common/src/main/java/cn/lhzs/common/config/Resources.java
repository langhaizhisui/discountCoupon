package cn.lhzs.common.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 加载配置
 */
public final class Resources {
    /**
     * 邮箱服务器配置
     */
    public static final ResourceBundle EMAIL = ResourceBundle.getBundle("config/email");
    /**
     * 手机短信配置
     */
    public static final ResourceBundle PHONE = ResourceBundle.getBundle("config/phone");
    /**
     * 第三方登录配置
     */
    public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");

    /**
     * 同步洋老板配置URL
     */
    public static final ResourceBundle DRP_CONFIG = ResourceBundle.getBundle("drp");

    /**
     * 图片URL配置
     */
    public static final ResourceBundle IMAGE = ResourceBundle.getBundle("config/image");
    /**
     * 国际化信息
     */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<>();

    /**
     * 国际化信息
     */
    public static String getMessage(String key, Object... params) {
//		Locale locale = LocaleContextHolder.getLocale();
        Locale locale = new Locale("zh", "CN");
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            synchronized (MESSAGES) {
                message = MESSAGES.get(locale.getLanguage());
                if (message == null) {
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    MESSAGES.put(locale.getLanguage(), message);
                }
            }
        }
        if (params != null && params.length > 0) {
            return String.format(message.getString(key), params);
        }
        return message.getString(key);
    }

    /**
     * 清除国际化信息
     */
    public static void flushMessage() {
        MESSAGES.clear();
    }

    public static Integer getBossUser() {
        return Integer.valueOf(DRP_CONFIG.getString("drp_user_id"));
    }

}
