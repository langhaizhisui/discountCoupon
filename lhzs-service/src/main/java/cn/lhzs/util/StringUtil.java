package cn.lhzs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static Boolean isEmptyJson(final String jsonValue) {
		return (null == jsonValue || "" == jsonValue || "[]" == jsonValue);
	}

	public static Boolean isEmptyString(final String value) {
		return (null == value || "".equals(value.trim()));
	}

	public static Boolean isNotEmptyString(final String value) {
		return !isEmptyString(value);
	}

	/**
	 * 合并字符串集合
	 *
	 * @author shenxufei
	 * @param inputs
	 *            字符串集合
	 * @return
	 */
	public static String combineString(String... inputs) {
		StringBuilder tmp = new StringBuilder();
		for (String string : inputs) {
			tmp.append(string);
		}
		return tmp.toString();
	}

	/**
	 * 生成指定长度字符串，不足部分补齐指定字符串
	 *
	 * @author shenxufei
	 * @param val
	 *            原始字符串
	 * @param pad
	 *            补齐不足部分使用字符串
	 * @param length
	 *            最终要生成的字符串长度
	 * @return
	 */
	public static String lpad(String val, String pad, Integer length) {
		if (null != val && val.length() >= length) {
			return val;
		}
		// 生成追加部分字符串
		StringBuilder sb = new StringBuilder(pad);
		int orgLength = null == val ? 0 : val.length();
		int padLength = length - orgLength;
		while (sb.length() < padLength) {
			sb.append(pad);
		}
		// 截取多余部分
		StringBuilder result = new StringBuilder(sb.substring(sb.length() - padLength));
		if (null != val) {
			result.append(val);
		}
		return result.toString();
	}

	/**
	 * 右补全
	 *
	 * @param val
	 * @param pad
	 * @param length
	 * @return
	 */
	public static String rpad(String val, String pad, Integer length) {
		if (null != val && val.length() >= length) {
			return val;
		}
		// 生成追加部分字符串
		StringBuilder sb = new StringBuilder(val);
		while (sb.length() < length) {
			sb.append(pad);
		}
		return sb.toString().substring(0, length);
	}

	public static String getNoBlankStr(String input) {
		if (isEmptyString(input)) {
			return null;
		}
		return input;
	}

	public static String getNoNullStr(String input) {
		return (input == null ? "" : input.trim());
	}

	public static String getMatch(String input, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.group();
		}
		return null;
	}

	public static String captureMatch(String input, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	/**
	 * url字符串增加参数
	 *
	 * @param url
	 * @param paramName
	 * @param paramVal
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String addParam2Url(String url, String paramName, String paramVal, String encode)
			throws UnsupportedEncodingException {
		StringBuilder tmp = new StringBuilder(url);
		if (url.contains("?") || url.contains("&")) {
			tmp.append("&");
		} else {
			tmp.append("?");
		}
		tmp.append(paramName).append("=").append(URLEncoder.encode(paramVal, encode));
		return tmp.toString();
	}

	/**
	 * url字符串增加参数
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String addParams2Url(String url, Map<String, String> params, String encode)
			throws UnsupportedEncodingException {
		Iterator<String> keyIter = params.keySet().iterator();
		StringBuilder tmp = new StringBuilder(url);
		if (keyIter.hasNext()) {
			if (url.contains("?") || url.contains("&")) {
				tmp.append("&");
			} else {
				tmp.append("?");
			}
			String key = keyIter.next();
			tmp.append(key).append("=").append(URLEncoder.encode(params.get(key), encode));
		}
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			tmp.append("&").append(key).append("=").append(URLEncoder.encode(params.get(key), encode));
		}
		return tmp.toString();
	}

	public static String addParams2UrlSorted(String url, Map<String, String> params, String encode)
			throws UnsupportedEncodingException {
		List<String> listkey = new ArrayList<String>(params.keySet());
		StringBuilder tmp = new StringBuilder(url);
		Collections.sort(listkey);
		String key = null;
		String val = null;
		for (int i = 0; i < listkey.size(); i++) {
			key = listkey.get(i);
			val = params.get(key);
			if (i == 0) {
				if (url.contains("?") || url.contains("&")) {
				} else {
					tmp.append("?");
				}
			} else {
				tmp.append("&");
			}
			tmp.append(key).append("=").append(URLEncoder.encode(val, encode));
		}
		return tmp.toString();
	}

	public static String createLinkString(Map<String, String> params) {
		if (params == null || params.size() == 0){
			return "";
		}

		List<String> keys = new ArrayList<String>(params.keySet());
		//Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(keys, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (isNotEmptyString(value)){
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
					prestr = prestr + key + "=" + value;
				} else {
					prestr = prestr + key + "=" + value + "&";
				}
			}
		}
		return prestr;
	}
}
