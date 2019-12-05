package com.hzero.demo.springboot.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 这个controller用来示范请求获取参数的方式示例，没有别的用途
 */
@RestController//@RestController = @ResponseBody + @Controller
public class HelloController {

    //    @RequestMapping的Get请求获取参数的方式示例：

    //    1、PathVariable：访问地址中传输参数：
    //访问方式： http://localhost/dev/123/say1 or http://localhost/dev/123/hello1
    @RequestMapping(value = {"/{id}/say1", "/{id}/hello1"}) //@RequestMapping可以指定多个value
    public String say1(@PathVariable("id") Integer id) {
        return "Hello Spring Boot:" + id;
    }

    //    2、RequestParam：访问地址后面传值,还可指定默认值：
    //访问方式： http://localhost/dev/say2?id=110 or http://localhost/dev/hello2
    @RequestMapping(value = {"/say2", "/hello2"})//@RequestMapping可以指定多个value
    public String say2(@RequestParam(value = "id", required = false, defaultValue = "120") Integer id) {
        return "Hello Spring Boot:" + id;
    }

}
