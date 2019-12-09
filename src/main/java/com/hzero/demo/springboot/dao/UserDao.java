package com.hzero.demo.springboot.dao;

import com.hzero.demo.springboot.pojo.User;

/**
 * 仅用来测试JPA扩展其它ORM框架
 */
public interface UserDao {

    User findUserById(int id);

}
