package com.iba.drp.aop.push;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sonic.liu on 2017/8/30.
 * @descp 推送订单状态注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface PushOrder {
    PushOrderEnum type() default PushOrderEnum.ORDER_FINISHED;
}
