package com.hzero.demo.springboot.controller;

import com.hzero.demo.springboot.dao.UserRepository;
import com.hzero.demo.springboot.pojo.User;
import com.hzero.demo.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@RestController//使用这个注解，无法跳转页面,页面中的数据会显示成json格式
public class UserController {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 添加 X
     * 访问链接： POST方式不支持传参访问
     *
     * @param age
     */
    @PostMapping(value = "/addUser")
    public User addUser(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        User user = new User();
        user.setUsername(name);
        user.setAge(age);
        return userRepository.save(user);
    }

    /**
     * 删除 X
     * 访问链接： POST方式不支持传参访问
     */
    @DeleteMapping(value = "/delUser/{id}")
    public void userDelete(@PathVariable("id") Integer id) {
        User user = new User();
        user.setId(id);
        userRepository.delete(user);
    }

    /**
     * 更新
     * 访问链接： POST方式不支持传参访问
     */
    @PutMapping(value = "/updateUserById/{id}")
    public User userUpdate(@PathVariable("id") Integer id, @RequestParam("age") Integer age) {
        User user = new User();
        user.setId(id);
        user.setAge(age);
        return userRepository.save(user);
    }

    /**
     * 查询所有
     * 访问链接： http://localhost/dev/findAll
     *
     * @return
     */
    @GetMapping(value = "/findAll")
    public List<User> userList() {
        return userRepository.findAll();
    }

    /**
     * 根据id查询
     * 访问链接： http://localhost/dev/findUserById/1  （确保ID存在，否则会跳到springboot的报错界面）
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/findUserById/{id}")
    public User userFindOne(@PathVariable("id") Integer id) {
        Optional<User> temp = userRepository.findById(id);
        //从返回值中获取值
        return temp.get();
    }


    /**
     * 通过年龄查询
     * 访问链接： http://localhost/dev/findUserByAge/2
     */
    @GetMapping(value = "/findUserByAge/{age}")
    public List<User> getListByAge(@PathVariable("age") Integer age) {
        return userRepository.findByAge(age);
    }

    /**
     * 通过用户名查询
     * 访问链接： http://localhost/dev/findUserByUsername/aesop
     */
    @GetMapping(value = "/findUserByUsername/{username}")
    public User findUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 测试jpa兼容其它ORM框架
     * 访问链接： http://localhost/dev/findUserByIdNoJpa/1
     */
    @GetMapping(value = "/findUserByIdNoJpa/{id}")
    public String findUserByIdNoJpa(@PathVariable("id") int id) {

        User user = null;
        if ((user = userRepository.findUserById(id)) != null) {

            return userRepository.findUserById(id).toString();

        } else {
            return "null";
        }

    }

    /**
     * 测试事务：插入两条数据
     * 访问链接：
     */
    @PostMapping(value = "/user/two")
    public void userTwo() {
        userService.insertTwo();
    }

}



