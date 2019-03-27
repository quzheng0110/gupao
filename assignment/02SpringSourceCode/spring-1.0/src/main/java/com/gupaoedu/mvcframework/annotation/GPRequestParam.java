package com.gupaoedu.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/27.
 * @Description: ${DESCRIPTION}
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";
}
