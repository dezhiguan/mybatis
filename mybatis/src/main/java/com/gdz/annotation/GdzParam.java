package com.gdz.annotation;

import java.lang.annotation.*;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 11:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface GdzParam {

    String value() default "";
}
