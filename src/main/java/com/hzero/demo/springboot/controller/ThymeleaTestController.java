package com.hzero.demo.springboot.controller;


import com.hzero.demo.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//要想返回的数据不是json，只需要写@Controller而不需要@ResponseBody
@Controller
@RequestMapping("/thymelea")
public class ThymeleaTestController {

    //关于跳转：取决于application.yml中的 动态视图解析器（thymelea） 是否配置
    //1、没有配置：默认跳转会自动去static 目录下查找，找不到就报错404，不会去templates 下查找
    //2、配置了：默认跳转会去templates目录下查找，找不到会报错404
    //3、配置了动态视图后，还想跳转static 目录的话可以用 return "redirect:/**.html"


    private List<User> users = new ArrayList<User>();


    //返回String类型代表页面跳转
    //通过 static/index.html 的按钮访问，或者 URL： http://localhost/dev/thymelea/ref
    @RequestMapping(value = "/ref", method = RequestMethod.GET)
    public String refHello() {

        System.out.println("ThymeleaTestController...refHello...");

        //配置了动态视图后，还想跳转static 目录的话可以用 return "redirect:/**.html";
        return "redirect:/hello.html";
    }

    @PostMapping("/showData")
    public String show(@RequestParam("word") String word, ModelMap modelMap) {

        System.out.println("ThymeleaTestController...show...");

        if (users.isEmpty()) {
            users.add(new User(1, "aesop1", "123", 1, 11, new Date(), "admin", word));
            users.add(new User(2, "aesop2", "321", 0, 22, new Date(), "admin", word));
            users.add(new User(3, "aesop3", "1234567", 1, 33, new Date(), "admin", word));
            users.add(new User(4, "aesop4", "7654321", 0, 44, new Date(), "admin", word));
        }

        modelMap.addAttribute("userList", users);
        modelMap.addAttribute("title", "列表页");

        return "show";//访问路径名与return要跳转的网页名最好不要一样，否则可能报错：会循环访问当前路径
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable int id, ModelMap modelMap) {

        System.out.println("ThymeleaTestController...detail...");

        for (User user : users) {

            if (user.getId() == id) {
                modelMap.addAttribute("user",
                        new User(user.getId(),
                                user.getUsername(),
                                user.getPassword(),
                                user.getSexy(),
                                user.getAge(),
                                user.getBirthday(),
                                user.getRole(),
                                user.getComments()));
            }

        }


        modelMap.addAttribute("title", "明细页");
        return "detail";
    }

}
