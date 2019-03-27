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
public @interface GPController {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}