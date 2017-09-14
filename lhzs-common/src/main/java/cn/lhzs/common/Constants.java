package cn.lhzs.common;

/**
 * 常量表
 */
public interface Constants {
    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
    /**
     * 客户端语言
     */
    String MANAGERLANGUAGE = "userLanguage";
    /**
     * 客户端主题
     */
    String WEBTHEME = "webTheme";
    /**
     * 当前用户
     */
    String CURRENT_USER = "CURRENT_USER";
    String CURRENT_MANAGER_TOKEN = "CURRENT_USER_TOKEN";
    /**
     * 在线管理员数量
     */
    String ALLMANAGER_NUMBER = "ALLMANAGER_NUMBER";
    /**
     * 上次请求地址
     */
    String PREREQUEST = "PREREQUEST";
    /**
     * 上次请求时间
     */
    String PREREQUEST_TIME = "PREREQUEST_TIME";
    /**
     * 非法请求次数
     */
    String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
    /**
     * 缓存命名空间
     */
    String O2O_REDIS_NAME_SPACE = "OTO:";

    /**
     * 验证码命名空间
     */
    String VALIDATE_CODE_KEY = "VALIDATE_CODE";

    /**
     * 短信验证码命名
     */
    String NOTE_VALIDATE_CODE_KEY = "NOTE";

    /**
     * 万能验证码
     */
    String PASS_CODE = "036015";

    /**
     * 排序 -升序
     */
    String ORDER_ASC = "ASC";

    /**
     * 排序 -降序
     */
    String ORDER_DESC = "DESC";

    /**
     * 代理管理员
     */
    String AGENT_MANAGER = "代理管理员";

    /**
     * 门店管理员
     */
    String SHOP_MANAGER = "门店管理员";
}
