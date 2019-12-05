package com.hzero.demo.springboot.dao;

import com.hzero.demo.springboot.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserDao {

    //自定义方法名规范： findXXBy,readXXBy,queryXXBy,countXXBy, getXXBy做为前缀，拼接属性（首字母大写）
    //可参考 jpa自定义方法命名示例.png

    //通过年龄查询
    List<User> findByAge(int age);

    //  Query  注解指定nativeQuery,这样就可以使用原生sql查询
    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    User findByUsername(String username);

}
