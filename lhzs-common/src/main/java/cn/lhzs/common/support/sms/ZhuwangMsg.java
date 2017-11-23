//package cn.lhzs.common.support.sms;
//
//import cn.lhzs.common.config.Resources;
//import cn.lhzs.common.config.SpringContentRegister;
//import cn.lhzs.common.support.thread.PushMessagePool;
//import cn.lhzs.common.util.HttpClientUtil;
//import cn.lhzs.common.util.StringUtil;
//import cn.lhzs.common.util.XmlUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static cn.lhzs.common.config.Resources.PHONE;
//
///**
// * @author sonic.liu on 2017/8/9.
// * @descp 筑望短信平台
// */
//public final class ZhuwangMsg {
//
//    /**
//     * 筑望短信反馈格式为xml方式，格式如下：
//     * <returnsms>
//     * <returnstatus>Success</returnstatus>
//     * <message>OK</message>
//     * <remainpoint>0</remainpoint>
//     * <taskID>0</taskID>
//     * <successCounts>0</successCounts>
//     * </returnsms>
//     */
//    private static final String MSG_INFO = "message";
//    private static final String MSG_STATUS = "returnstatus";
//    private static final String MSG_STATUS_OK = "Success";
//    private Logger log = LoggerFactory.getLogger(getClass());
//    /**
//     * 筑望短信提供多个API接口地址，如果某个接口地址发送失败，可以使用其他接口地址
//     */
//    private List<String> urlList;
//    /**
//     * 请求参数MAP
//     */
//    private Map<String, Object> urlParmMap;
//    private PushMessagePool pushMessagePool;
//
//    private ZhuwangMsg() {
//        pushMessagePool = (PushMessagePool) SpringContentRegister.getApplicationContext().getBean("pushMessagePool");
//
//        urlParmMap = new HashMap<>(6);
//        urlParmMap.put("userid", PHONE.getString("zhuwang.userid"));
//        urlParmMap.put("account", PHONE.getString("zhuwang.account"));
//        urlParmMap.put("password", PHONE.getString("zhuwang.password"));
//        urlParmMap.put("action", "send");
//
//        String urls = PHONE.getString("zhuwang.url");
//        String[] urlArry = urls.split(",");
//        this.urlList = Arrays.asList(urlArry);
//    }
//
//    public static ZhuwangMsg getInstanse() {
//        return SingletonHolder.INSTANCE;
//    }
//
//    /**
//     * TODO：增加返回值
//     * 发送短信接口 ， 使用异步线程处理
//     *
//     * @param phone   手机号
//     * @param content 发送短信内容，需要调用 MessageConstant.exCode(MsgTypeEnum msgType, String ... code)
//     * @see MessageConstant.exCode
//     */
//    public void sendMsg(String phone, String content) {
//        if (!"true".equals(Resources.DRP_CONFIG.getString("send_msg_switch")))
//            return;
//
//        urlParmMap.put("mobile", phone);
//        urlParmMap.put("content", content);
//
//        pushMessagePool.addTask(() -> {
//            for (String url : urlList) {
//                if (StringUtil.isBlank(url)) continue;
//                try {
//                    String result = HttpClientUtil.post(url, urlParmMap);
//                    Map<String, String> map = XmlUtil.getMapFromXML(result);
//                    if (MSG_STATUS_OK.equals(map.get(MSG_STATUS))) {
//                        log.info("[send msg] Success, phone:{}, content:{}", phone, content);
//                        return;
//                    } else {
//                        log.error("[send msg] error, phone:{}, content:{}, erroMsg:{}", phone, content, map.get(MSG_INFO));
//                    }
//                } catch (Exception e) {
//                    log.error("[send msg] error, phone:{}, content:{}, erroMsg:{}", phone, content, e.getMessage());
//                }
//            }
//        });
//    }
//
//    //使用静态内部类加载对象，使用classloder的机制来保证初始化instance时只有一个实例
//    private static class SingletonHolder {
//        private static final ZhuwangMsg INSTANCE = new ZhuwangMsg();
//    }
//
//}
