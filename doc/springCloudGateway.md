# Spring Cloud Gateway

Z：Zuul是基于Servlet的，不支持任何长连接（如WebSockets）。而Spring官方推出的Spring Cloud Gateway使用非阻塞API，支持WebSocket。   

## 路由跳转

M：Spring Cloud Gateway怎么配置路由跳转，从而实现统一对外输出接口？

Z：有两种方式：yml配置和代码控制，如下所示

### 1.yml配置

Z：yml配置如下：

1. pom依赖，提升springCloud版本

   Spring Cloud需要使用Finchley版本，Finchley 版本依赖于Spring Boot 2.0.6.RELEASE。

   ```xml
   <parent>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-parent</artifactId>
   	<version>2.0.6.RELEASE</version>
   	<relativePath/> <!-- lookup parent from repository -->
   </parent>
   
   <dependencyManagement>
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-dependencies</artifactId>
   			<version>Finchley.SR2</version>
   			<type>pom</type>
   			<scope>import</scope>
   		</dependency>
   	</dependencies>
   </dependencyManagement>
   ```

   Finchley.RELEASE版本存在bug

2. 添加依赖

   ```xml
   <dependency>
   	<groupId>org.springframework.cloud</groupId>
   	<artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```

3. 配置文件编写转发信息

   ```properties
   server:
     port: 8080
   spring:
     cloud:
       gateway:
         routes:
         - id: neo_route
           uri: http://www.baidu.com
           predicates:
             - Path=/spring-cloud
   ```

   - id：指定路由id  
   - uri：跳转地址
   - Path：拦截地址

   当访问``http://localhost:8080/spring-cloud``时，会自动替换url为``http://www.baidu.com/spring-cloud``进行转发   

### 2.代码控制

Z：代码转发则是在Application启动类中添加``customRouteLocator()``来定制转发规则

```java
@SpringBootApplication
public class GateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("path_route", r -> r.path("/about")
						.uri("http://ityouknow.com"))
				.build();
	}

}
```

同样可以实现转发效果  

## Predicate

Z：Predicate来源于Java8，Predicate是一套匹配规则，对对应的路由进行处理。常见Predicate如下

![](../imgs/z02.png)  

 匹配方式为详情查看页面：http://www.ityouknow.com/springcloud/2018/12/12/spring-cloud-gateway-start.html   