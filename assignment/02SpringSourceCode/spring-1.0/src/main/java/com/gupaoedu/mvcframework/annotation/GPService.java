package com.gupaoedu.mvcframework.annotation;
import java.lang.annotation.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/27.
 * @Description: ${DESCRIPTION}
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
