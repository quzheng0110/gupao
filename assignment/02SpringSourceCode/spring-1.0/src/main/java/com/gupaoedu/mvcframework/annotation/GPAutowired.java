package com.gupaoedu.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/27.
 * @Description: ${DESCRIPTION}
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
