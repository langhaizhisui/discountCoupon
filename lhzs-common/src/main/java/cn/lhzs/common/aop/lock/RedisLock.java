package cn.lhzs.common.aop.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IBA-EDV on 2017/9/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RedisLock {
    FormActionEnum type() default FormActionEnum.REQ_COMMIT;
}
