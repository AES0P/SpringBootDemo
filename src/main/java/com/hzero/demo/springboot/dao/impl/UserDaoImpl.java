package com.hzero.demo.springboot.dao.impl;

import com.hzero.demo.springboot.dao.UserDao;
import com.hzero.demo.springboot.mapper.UserMapper;
import com.hzero.demo.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "userDaoImpl")
public class UserDaoImpl implements UserDao {

//        System.out.println("如果有搞不定的查询方法，可以自定义接口，例如 UserDao ，和它的实现类。" +
//                "然后在这个实现类里用喜欢的ORM框架，例如：mybatis ,jdbcTemplate 等。" +
//                "最后在用UserRepository 去继承 UserDao 接口，就实现了jpa和其他orm框架的组合使用");
//    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }
}

