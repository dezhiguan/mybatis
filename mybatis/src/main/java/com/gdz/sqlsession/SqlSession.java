package com.gdz.sqlsession;

import com.gdz.handler.UserMapperInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 16:11
 */
public class SqlSession {

    public static <T> T getUserMapper(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
                new UserMapperInvocationHandler(clazz));
    }
}
