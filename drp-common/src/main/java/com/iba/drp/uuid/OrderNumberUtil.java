package com.iba.drp.uuid;

import com.iba.drp.constant.OrderTypeEnum;
import com.iba.drp.util.DateUtil;

import java.util.Date;
import java.util.Random;

/**
 * @author sonic.liu
 * @descption 订单生成工具类
 */
public final class OrderNumberUtil {


    /**
     * 随机类
     */
    private static Random RANDOM = new Random(System.nanoTime());

    /**
     * a~z 字母和 0 ~ 9 数字数组 剔除 0，O ,1，I 字母和数字相似的
     */
    private static final char[] STR_ARRAYS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 根据生成订单号
     * 生成规则: [订单类型]+[shopid]+日期+[成交时间yyyyMMdd]+[当前毫秒数]+[3位随机数]
     * 例如 : 采购订单： CG_20170902_10000_051_K48 销售订单： XS_20170902__10000_801_K4J
     *
     * @param order  订单类型
     *               @see com.iba.drp.constant.OrderTypeEnum
     * @param shopId 店铺id
     * @return 21位订单号
     */
    public static String generateOrderNumber(OrderTypeEnum order, Long shopId) {
        String dateTimeStr = DateUtil.format(new Date(), DateUtil.DATE_PATTERN_YYYYMMDD);
        StringBuilder sb = new StringBuilder(21);
        sb.append(order.getSn()).append(dateTimeStr).append(shopId)
                .append(getHashCodeStr()).append(getRandomNumber(3));
        return sb.toString();
    }


    // 生成随机数
    public static String getRandomNumber(int size) {
        if(size < 1) {
            int i = Math.abs(RANDOM.nextInt(STR_ARRAYS.length));
            return  String.valueOf(STR_ARRAYS[i]);
        }else{
            StringBuilder sb = new StringBuilder(size);
            for(int i = 0; i< size; i++){
                int j = Math.abs(RANDOM.nextInt(STR_ARRAYS.length));
                sb.append(STR_ARRAYS[j]);
            }
            return sb.toString();
        }
    }

    /**
     * 获取当前时间毫秒数
     *
     * @return
     */
    public static  String getHashCodeStr() {
        return String.valueOf(System.nanoTime()).substring(13);
    }

//    public static void main(String[] ss) {
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println(generateOrderNumber(OrderTypeEnum.SALE_ORDER, 10001L));
////            System.out.println(getHashCodeStr());
//        }
//        System.out.println("cost=" + (System.currentTimeMillis() - startTime));
//    }
}
