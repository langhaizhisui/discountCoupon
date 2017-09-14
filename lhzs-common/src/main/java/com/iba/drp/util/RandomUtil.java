package com.iba.drp.util;

import java.util.Random;

/**
 * 随机数工具类
 */
public class RandomUtil {
	
	/** 0 ~ 9 数字数组 */
    private static final char[] NUMERAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /** a~z 字母和 0 ~ 9 数字数组 */
    private static final char[] STR_ARRAYS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    /** 随机类*/
    private static Random RANDOM = new Random(System.nanoTime());
    
    /**
     * 产生随机的六位数(纯属数字)
     *
     * @return
     */
    public static String getCode() {
//        Random rad = new Random();
//        String result = rad.nextInt(1000000) + "";
//        if (result.length() != 4) {
//            return getCode();
//        }
//        return result;
    	return createMobileNumber(6);
    }

	/**
	 * 生成制定数量的随机数
	 * 
	 * @param len 生成数字长度
	 * @return String
	 */
	public static String createMobileNumber(int len){
		int maxIndex = NUMERAL.length;
		StringBuffer buf = new StringBuffer(len);
		for(int i = 0; i < len; i++){
			int index = (int)(RANDOM.nextDouble() * maxIndex);
			buf.append(NUMERAL[index]);
		}
		return buf.toString();
	}

    /**
     * 随机生成密码
     * @param len 长度
     * @return 密码字符串
     */
    public static String generatorPwd(int len){
        StringBuffer buf = new StringBuffer(len);
        for(int i = 0; i < len; i++){
            buf.append(NUMERAL[(int)(RANDOM.nextDouble() * NUMERAL.length)]);
        }
        return buf.toString();
    }

    /**
     * 随机生成6位的密码
     * @return
     */
    public static String getPassword(){
        final int maxNum = 36;
        int i;
        int count = 0;
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < 6){
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < STR_ARRAYS.length) {
                pwd.append(STR_ARRAYS[i]);
                count ++;
            }
        }
        return pwd.toString();
    }
    
}
