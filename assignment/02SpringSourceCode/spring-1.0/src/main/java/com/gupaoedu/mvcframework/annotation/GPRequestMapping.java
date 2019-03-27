package com.gupaoedu.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/27.
 * @Description: ${DESCRIPTION}
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
