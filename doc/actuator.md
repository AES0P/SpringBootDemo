# actuator  

Z：spring-boot-starter-actuator主要作用是用于监控与管理

## 环境搭建

M：怎么查看项目中有哪些bean？

Z：操作方式如下：

1. 添加actuator的pom依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```

2. application.properties中添加以下配置，暴露所有端点

   ```properties
   management.endpoints.web.exposure.include=*
   ```

   因为2.0的actuator将所有端点都屏蔽了（health和info除外）

3. 访问actuator的端点

   - 查看bean：``http://localhost:8080/actuator/beans``（安装jsonview可以格式化json数据）

   - 服务状态health：``http://localhost:8080/actuator/health``   

## 端点介绍

### 1.conditions端点

Z：conditions原名autoconfig，该端点展示了自动化配置是否成功的报告

- `positiveMatches`中返回的是条件匹配成功的自动化配置
- `negativeMatches`中返回的是条件匹配不成功的自动化配置

### 2.beans端点

Z：beans查看端点，如下：

```json
jdbcTemplate: {
    aliases: [ ],
    scope: "singleton",
    type: "org.springframework.jdbc.core.JdbcTemplate",
    resource: "class path resource [org/springframework/boot/autoconfigure/jdbc/JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration.class]",
    dependencies: [ ]
}
```

- scope：Bean的作用域
- type：Bean的Java类型
- reource：class文件的具体路径
- dependencies：依赖的Bean名称

更多端点使用可查看：http://blog.didispace.com/spring-boot-actuator-1/

《Spring Boot实战》109页
