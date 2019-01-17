package com.gdz;


import com.gdz.bean.User;
import com.gdz.mapper.UserMapper;
import com.gdz.sqlsession.SqlSession;

import java.sql.SQLException;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 10:22
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        UserMapper userMapper = SqlSession.getUserMapper(UserMapper.class);
        User user = userMapper.selectUser("guan", "sanming");
        //int row = userMapper.insertUser("guandezhi", "fujian");
        System.out.println("row:" + user);
    }



}
