package com.gdz.annotation;

import java.lang.annotation.*;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 10:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface GdzQuery {

    String value() default "";
}
