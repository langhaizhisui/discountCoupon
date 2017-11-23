//package cn.lhzs.common.support.sms;
//
//import cn.lhzs.common.config.Resources;
//import cn.lhzs.common.constant.MsgTypeEnum;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * 云片短信发送平台API
// *
// */
//
//public class JavaSmsApi {
//	// 编码格式。发送编码格式统一用UTF-8
//	private static String ENCODING = "UTF-8";
//
//	public static void main(String[] args) throws IOException, URISyntaxException {
//
//		// 修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后获取
//		String apikey = "b90bbf4687cc69f04369b63d0a291f90";
//
//		// 修改为您要发送的手机号
//		String mobile = "13798543279";
//
//		/**************** 查账户信息调用示例 *****************/
//		// System.out.println(JavaSmsApi.getUserInfo(apikey));
//
//		/**************** 使用智能匹配模板接口发短信(推荐) *****************/
//		// 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
//		// String text = "【云片网】您的验证码是1234";
//		// 发短信调用示例
//		System.out.println(JavaSmsApi.sendSms(MessageConstant.exCode(MsgTypeEnum.PHONE_VCODE, "1234"), mobile));
//
//		/**************** 使用指定模板接口发短信(不推荐，建议使用智能匹配模板接口) *****************/
//		// 设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
//		// long tpl_id = 1588838;
//		// 设置对应的模板变量值
//
//		// String tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
//		// + URLEncoder.encode("1234", ENCODING);
//		// 模板发送的调用示例
//		// System.out.println(tpl_value);
//		// System.out.println(JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value,
//		// mobile));
//	}
//
//	/**
//	 * 取账户信息
//	 *
//	 * @return json格式字符串
//	 * @throws IOException
//	 */
//
//	public static String getUserInfo() throws IOException, URISyntaxException {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		return post(Resources.PHONE.getString("phone.uriGetUserInfo"), params);
//	}
//
//	/**
//	 * 智能匹配模板接口发短信
//	 *
//	 * @param text
//	 *            短信内容
//	 * @param mobile
//	 *            接受的手机号
//	 * @return json格式字符串
//	 * @throws IOException
//	 */
//
//	public static String sendSms(final String text, final String mobile) throws IOException {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		params.put("text", text);
//		params.put("mobile", mobile);
//		return post(Resources.PHONE.getString("phone.uriSendSms"), params);
//	}
//
//	/**
//	 * 通过模板发送短信(不推荐)
//	 *
//	 * @param tpl_id
//	 *            模板id
//	 * @param tpl_value
//	 *            模板变量值
//	 * @param mobile
//	 *            接受的手机号
//	 * @return json格式字符串
//	 * @throws IOException
//	 */
//
//	public static String tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		params.put("tpl_id", String.valueOf(tpl_id));
//		params.put("tpl_value", tpl_value);
//		params.put("mobile", mobile);
//		return post(Resources.PHONE.getString("phone.uriTplSendSms"), params);
//	}
//
//	/**
//	 * 通过接口发送语音验证码
//	 *
//	 * @param mobile
//	 *            接收的手机号
//	 * @param code
//	 *            验证码
//	 * @return
//	 */
//
//	public static String sendVoice(String mobile, String code) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		params.put("mobile", mobile);
//		params.put("code", code);
//		return post(Resources.PHONE.getString("phone.uriSendVoice"), params);
//	}
//
//	/**
//	 * 通过接口绑定主被叫号码
//	 *
//	 * @param from
//	 *            主叫
//	 * @param to
//	 *            被叫
//	 * @param duration
//	 *            有效时长，单位：秒
//	 * @return
//	 */
//
//	public static String bindCall(String from, String to, Integer duration) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		params.put("from", from);
//		params.put("to", to);
//		params.put("duration", String.valueOf(duration));
//		return post(Resources.PHONE.getString("phone.uriSendBind"), params);
//	}
//
//	/**
//	 * 通过接口解绑绑定主被叫号码
//	 *
//	 * @param from
//	 *            主叫
//	 * @param to
//	 *            被叫
//	 * @return
//	 */
//	public static String unbindCall(String from, String to) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("apikey", Resources.PHONE.getString("phone.apiKey"));
//		params.put("from", from);
//		params.put("to", to);
//		return post(Resources.PHONE.getString("phone.uriSendBind"), params);
//	}
//
//	/**
//	 * 基于HttpClient 4.3的通用POST方法
//	 *
//	 * @param url
//	 *            提交的URL
//	 * @param paramsMap
//	 *            提交<参数，值>Map
//	 * @return 提交响应
//	 */
//
//	public static String post(String url, Map<String, String> paramsMap) {
//		CloseableHttpClient client = HttpClients.createDefault();
//		String responseText = "";
//		CloseableHttpResponse response = null;
//		try {
//			HttpPost method = new HttpPost(url);
//			if (paramsMap != null) {
//				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
//					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
//					paramList.add(pair);
//				}
//				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
//			}
//			response = client.execute(method);
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				responseText = EntityUtils.toString(entity);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				response.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return responseText;
//	}
//}
