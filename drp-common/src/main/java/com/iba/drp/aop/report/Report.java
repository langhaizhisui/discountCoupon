package com.iba.drp.aop.report;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sonic.liu on 2017/8/28.
 * @descp 进行数据统计的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Report {
    ReportTypeEnum type() default  ReportTypeEnum.SALE;
}
