package com.iba.drp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sonic.liu
 * @version 2016年09月21日 13时23分
 */
public class PatternUtil {

    private static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 检查邮箱是否合法
     * @param email
     */
    public static boolean checkEmail(String email){
        Pattern regex = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = regex.matcher(email);
        // 获取邮箱然后匹对是否正确
        return matcher.matches();
    }
}
