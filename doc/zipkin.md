# zipkin链路跟踪

Z：一个请求可能要通过多个服务才能完成，如果请求变慢或不可用，很难得知是哪个服务出现问题。Spring Cloud Sleuth可以查看数据请求服务的顺序，还有请求的时间。结合zipkin，将信息发送到zipkin，可以用来展示数据。

## zipkin服务搭建

Z：zipkin搭建方式如下：

1. 添加pom依赖

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-eureka</artifactId>
       </dependency>
       <dependency>
           <groupId>io.zipkin.java</groupId>
           <artifactId>zipkin-server</artifactId>
       </dependency>
       <dependency>
           <groupId>io.zipkin.java</groupId>
           <artifactId>zipkin-autoconfigure-ui</artifactId>
       </dependency>
   </dependencies>
   ```

2. 添加``@EnableZipkinServer``注解  

   ```java
   @SpringBootApplication
   @EnableEurekaClient
   @EnableZipkinServer
   public class ZipkinApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ZipkinApplication.class, args);
       }
   }
   ```

   启动访问``http://localhost:9000/zipkin/``即可查看Zipkin后台页面   

## 添加zipkin支持

Z：添加zipkin支持需要添加依赖 和 添加配置信息:

1. 在要被监测的服务上添加zipkin依赖

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-zipkin</artifactId>
   </dependency>
   ```

   Spring应用监测到Java依赖包中有sleuth和zipkin后，会在请求中注入追踪信息，并且向Zipkin Server发送这些信息。

2. 添加配置信息：

   ```properties
   server:
     port: 9001
   spring:
     application:
       name: producer
     zipkin:
       base-url: http://localhost:9000
     sleuth:
       sampler:
         percentage: 1.0
   eureka:
     client:
       serviceUrl:
         defaultZone: http://localhost:8761/eureka/
   ```

   base-url指向zipkin地址，percentage设定采样比例（1.0表示100%采样）

访问服务之后，查看``http://localhost:9000/zipkin/``即可了解服务间的调用情况与时间顺序。   