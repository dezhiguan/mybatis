package com.gdz.mapper;

import com.gdz.bean.User;
import com.gdz.annotation.GdzInsert;
import com.gdz.annotation.GdzParam;
import com.gdz.annotation.GdzQuery;

/**
 * @Author: guandezhi
 * @Date: 2019/1/17 10:37
 */
public interface UserMapper {

    @GdzQuery("select username, city from user  where username = #{username} and city = #{city}")
    User selectUser(@GdzParam("username") String name, @GdzParam("city") String city);


    @GdzInsert("insert into user(username, city) values(#{username}, #{city})")
    int insertUser(@GdzParam("username") String name, @GdzParam("city") String city);


}
