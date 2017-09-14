package com.iba.drp.util;

import java.util.Random;

/**
 * Created by Liguoxing on 2017/8/9.
 */
public class ValidateCodeUtil {

    //验证码字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    private static final String  VALIDATE_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 使用系统默认字符源生成验证码
     * @param validateCodeSize    验证码长度
     * @return
     */
    public static String generateVerifyCode(int validateCodeSize){
        return generateVerifyCode(validateCodeSize, VALIDATE_CODES);
    }

    /**
     * 使用指定源生成验证码
     * @param validateCodeSize    验证码长度
     * @param validateCodeSources   验证码字符源
     * @return
     */
    private static String generateVerifyCode(int validateCodeSize, String validateCodeSources){
        int codesLength = validateCodeSources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder validateCode = new StringBuilder(validateCodeSize);
        for(int i = 0; i < validateCodeSize; i++){
            validateCode.append(validateCodeSources.charAt(rand.nextInt(codesLength-1)));
        }
        return validateCode.toString();
    }

}
