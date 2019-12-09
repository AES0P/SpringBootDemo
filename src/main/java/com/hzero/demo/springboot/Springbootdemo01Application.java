package com.hzero.demo.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 包含了@Configuration、@EnableAutoConfiguration、@ComponentScan 以及他们的默认属性。
 * 这个启动类就是一个AppConfig
 * <p>
 * 注意@SpringBootApplication默认扫描启动类所在包及其子包
 * 如果需要扫描其他路径，需要增加@ComponentScan或者@Import注解
 */
@SpringBootApplication
@MapperScan("com.hzero.demo.springboot.mapper")
public class Springbootdemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo01Application.class, args);
    }

}
