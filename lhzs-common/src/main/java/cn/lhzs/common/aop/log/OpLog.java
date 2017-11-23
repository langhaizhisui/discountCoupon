package cn.lhzs.common.aop.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhx on 2017/7/12.
 * @descp 后台操作日志注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface OpLog {
    String descp() default "";
    OpEnum type() default  OpEnum.SELECT;
}
