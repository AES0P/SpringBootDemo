package com.hzero.demo.springboot.mapper;

import com.hzero.demo.springboot.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository(value = "userMapper")
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") int id);

}
