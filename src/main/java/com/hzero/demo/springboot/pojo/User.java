package com.hzero.demo.springboot.pojo;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 在项目中使用 Lombok 可以减少很多重复代码的书写。比如说getter/setter/toString等方法的编写。
 * 先安装 Lombok 插件，再引入lombok.jar包
 * <p>
 * 该注解使用在类或者属性上，该注解可以使用在类上也可以使用在属性上。生成的getter遵循布尔属性的约定。
 * 例如：boolean类型的sex,getter方法为isSex而不是getSex
 */
//@Data 该注解使用在类上，该注解会提供getter、setter、equals、canEqual、hashCode、toString方法。它没有与其他注解相同的控制粒度
@Getter//在使用该注解时，会默认生成一个无参构造。
@Setter//在使用该注解时，会默认生成一个无参构造。
//通过exclude参数中包含字段名称，可以从生成的方法中排除特定字段,includeFieldNames 来控制输出中是否包含的属性名称
@ToString(includeFieldNames = false, exclude = "password")
@AllArgsConstructor//该注解使用在类上，该注解提供一个全参数的构造方法，默认不提供无参构造。
@NoArgsConstructor//该注解使用在类上，该注解提供一个无参构造
@Entity//JPA提供自动生成数据库表的功能，使用注解@Entity，标注id@Id，自增长@GeneratedValue即可
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//设置为自动生成，生成策略为自增长
    private int id;

    @Column(nullable = false, unique = true)//设置列的非空和唯一性
    private String username;

    private String password;

    private int sexy;

    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//日期类型进行格式化
    private Date birthday;

    private String role;

    private String comments;

}





