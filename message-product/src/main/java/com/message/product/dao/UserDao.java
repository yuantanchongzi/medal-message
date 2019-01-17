package com.message.product.dao;

import com.message.product.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Francis
 * @Description:
 * @TIME: Created on 2018/11/3
 * @Modified by:
 */
@Mapper
public interface UserDao {

    @Select("select max(id) as id from testuser")

    int getId();
}
