package com.gdz.annotation;

import java.lang.annotation.*;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 14:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface GdzInsert {

    String value() default "";
}
