package com.gdz.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 14:10
 */
public class SQLUtils {


    public static String replaceParam(String sql, List<Object> selectParams) {
        for (int i = 0; i < selectParams.size(); i++) {
            Object paramName = selectParams.get(i);
            sql = sql.replace("#{" + paramName + "}", "?");
        }
        return sql;
    }

    public static String replaceParam(String sql, String[] parameterName) {
        for (int i = 0; i < parameterName.length; i++) {
            String string = parameterName[i].trim();
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }

    public static List<Object> getSelectParams(String sql) {
        int startIndex = sql.indexOf("where") + 5;
        String whereClause = sql.substring(startIndex);
        String[] paramsStrs = whereClause.split("and");
        List<Object> paramList = new ArrayList<>();
        for (String paramsStr : paramsStrs) {
            String sp = paramsStr.split("=")[1];
            String param = sp.replace("#{", "").replace("}", "");
            paramList.add(param.trim());
        }
        return paramList;
    }

    public static String[] getInsertParams(String sql) {
        int startIndex = sql.indexOf("values");
        String values = sql.substring(startIndex + 6).replaceAll("#\\{", "").replaceAll("}", "").
                replace("(", "").replace(")", "");
        return values.split(",");
    }
}
