package cn.lhzs.common.util;

/**
 * @descption 过滤词工具类
 * @author sonic.liu
 * @date 2017年2月7日
 */
public class FilterWordUtil {

	/** 搜索供应商2个单词时过滤词 */
	private static final String[] FILTER2_LIST = { "有限", "公司" };

	/** 搜索供应商4个单词时过滤词 */
	private static final String[] FILTER4_LIST = { "有限公司" };

	public static String filterWord(String keyword) {
		if (StringUtil.isBlank(keyword))
			return null;
		if (keyword.length() == 2) {
			return filter2Word(keyword);
		} else {
			return filter4Word(keyword);
		}
	}

	public static String filter2Word(String keyword) {
		if (StringUtil.isBlank(keyword))
			return null;
		String newWord = keyword;
		for (String word : FILTER2_LIST) {
			if (keyword.contains(word)) {
				newWord = keyword.replaceAll(word, "");
			}
		}
		return newWord;
	}

	public static final String filter4Word(String keyword) {
		if (StringUtil.isBlank(keyword))
			return null;
		String newWord = keyword;
		for (String word : FILTER4_LIST) {
			if (keyword.contains(word)) {
				newWord = keyword.replaceAll(word, "");
			}
		}
		return newWord;
	}

	public static void main(String[] args) {
		String keyword = "公司";
		System.out.println(filterWord(keyword));
	}
}
