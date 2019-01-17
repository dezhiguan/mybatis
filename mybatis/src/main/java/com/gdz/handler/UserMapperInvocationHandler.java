package com.gdz.handler;


import com.gdz.annotation.GdzInsert;
import com.gdz.annotation.GdzParam;
import com.gdz.annotation.GdzQuery;
import com.gdz.utils.JDBCUtils;
import com.gdz.utils.SQLUtils;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 10:49
 */
public class UserMapperInvocationHandler implements InvocationHandler {

    private Class userMapperClazz;

    public UserMapperInvocationHandler(Class userMapperClazz) {
        this.userMapperClazz = userMapperClazz;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        GdzQuery gdzQuery =  method.getDeclaredAnnotation(GdzQuery.class);
        //查询
        if (null != gdzQuery) {
            //获取结果
            return getResult(method, gdzQuery, args);
        }
        //插入
        GdzInsert gdzInsert = method.getDeclaredAnnotation(GdzInsert.class);
        if (null != gdzInsert) {
            String insertSql = gdzInsert.value();
            //插入参数
            String[] insertParam = SQLUtils.getInsertParams(insertSql);
            //参数绑定
            ConcurrentHashMap<String, Object> paramMap = getMethodParam(method, args);
            //将参数值加入list
            List<Object> paramValueList = addParamToList(insertParam, paramMap);

            insertSql = SQLUtils.replaceParam(insertSql, insertParam);
            return JDBCUtils.insert(insertSql, false, paramValueList);
        }
        return null;
    }


    private Object getResult(Method method, GdzQuery gdzQuery, Object[] args) throws SQLException, IllegalAccessException, InstantiationException {
        String querySql = gdzQuery.value();
        //获取sql参数
        List<Object> paramList = SQLUtils.getSelectParams(querySql);
        //替换sql参数
        querySql = SQLUtils.replaceParam(querySql, paramList);
        //获取方法参数，绑定值。
        ConcurrentHashMap<String, Object> paramMap = getMethodParam(method, args);

        List<Object> paramValueList = new ArrayList<>();
        //将参数值装入list集合
        for (Object param : paramList) {
            Object paramValue = paramMap.get(param);
            paramValueList.add(paramValue);
        }

        System.out.println("paramValueList:" + paramValueList);

        ResultSet rs = JDBCUtils.query(querySql, paramValueList);
        if (!rs.next()) {return null;}

        Class<?> returnTypeClazz = method.getReturnType();
        Object obj = returnTypeClazz.newInstance();
        //光标往前移动一位
        rs.previous();
        while (rs.next()) {
            Field[] fields = returnTypeClazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldValue = rs.getString(fieldName);
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
        }
        return obj;
    }

    private ConcurrentHashMap getMethodParam(Method method, Object[] args) {
        ConcurrentHashMap paramMap = new ConcurrentHashMap();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            GdzParam gdzParam = parameters[i].getAnnotation(GdzParam.class);
            if (null == gdzParam) {continue;}
            String paramName = gdzParam.value();
            Object paramValue = args[i];
            paramMap.put(paramName, paramValue);
        }
        return paramMap;
    }


    private List<Object> addParamToList(String[] insertParam, ConcurrentHashMap<String, Object> paramMap) {
        List<Object> paramValueList = new ArrayList<>();
        for (String param : insertParam) {
            Object paramValue = paramMap.get(param.trim());
            paramValueList.add(paramValue);
        }
        return paramValueList;
    }

}
