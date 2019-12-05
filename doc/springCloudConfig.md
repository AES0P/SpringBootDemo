# Spring Cloud Config

## 服务端   

Z：Spring Cloud Config就是将配置文件存放在git上，以接口的方式将内容提供给client端调用。

Z：Spring Cloud Config的使用步骤如下：

1. 添加maven依赖，使用的是``spring-cloud-config-server``   

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-server</artifactId>
   </dependency>
   ```

2. 添加配置文件

   ```yaml
   server:
     port: 8001
   spring:
     application:
       name: spring-cloud-config-server
     cloud:
       config:
         server:
           git:
             uri: https://github.com/leekoko/microservice/     # 配置git仓库的地址
             search-paths: config-repo   # git仓库地址下的相对地址，可以配置多个，用,分割。
             username: leekoko                                      # git仓库的账号
             password:                                              # git仓库的密码
   ```

3. 启动类添加``@EnableConfigServer``注解

   完成以上配置之后，启动项目就会将git上的配置文件获取到。通过web接口的方式返回json数据。访问地址为：``http://localhost:8001/配置文件名称.properties``     

M：配置文件名称有什么要求吗？neo-config-dev.properties

Z：格式如：/{应用名}-{profile}.properties。这里的{应用名}是neo-config，{profile}可以用来区分配置文件的使用场景：例如dev是开发环境，test是测试环境，pro是生产环境。

## 客户端   

M：服务端已经将配置文件信息通过接口暴露出来了，客户端怎么请求获取它的配置文件呢？

Z：操作步骤如下

1. 添加pom依赖，使用的是``spring-cloud-starter-config``

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   ```

2. 添加bootstrap.yml配置文件，指明获取配置文件的地址

   ```yaml
   spring:
     cloud:
       config:
         name: neo-config
         profile: dev
         uri: http://localhost:8001/
         label: master
   ```

   这个地址可以拼凑成url访问地址：``http://localhost:8001/配置文件名称.properties``，指明了获取master分支       

## 配置中心服务化   

Z：上面的配置读取方式，要指定配置的服务端。但是服务端的地址随时会变，所以是将服务端和客户端注册到Eureka中管理

修改方式如下：

1. config服务端添加配置，注册服务到Eureka

   ```yaml
   eureka:
     client:
       serviceUrl:
         defaultZone: http://localhost:8761/eureka/   # 注册中心eurka地址
   ```

2. 客户端连接Eureka，获取config服务，通过config服务再去获取git上的配置信息

   ```yaml
   spring:
     cloud:
       config:
         name: neo-config
         profile: dev
   #      uri: http://localhost:8001/
         label: master
         discovery:
           service-id: spring-cloud-config-server
           enabled: true
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/   # 注册中心eurka地址
   
   ```

   service-id指定服务端name值：spring-cloud-config-server，启动服务发现：enabled: true

## refresh   

Z：客户端从服务端获取配置文件，如果配置文件内容修改，客户端是不会感知到变化，需要使用POST方法触发``/refresh``。具体操作暂时仍未实现

进阶的还有：

用webhook检测更新

http://www.ityouknow.com/springcloud/2017/05/23/springcloud-config-svn-refresh.html

利用Spring cloud bus解决多客户端更新

http://www.ityouknow.com/springcloud/2017/05/26/springcloud-config-eureka-bus.html



