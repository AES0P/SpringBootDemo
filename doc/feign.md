# Feign

## Feign的引入

Z：Feign引入，步骤如下

1. pom.xml添加``spring-cloud-starter-openfeign``依赖
2. 在Application启动上添加``@EnableFeignClients ``注解

M：之前我使用``@EnableFeignClients ``，使用idea推荐的``spring-cloud-openfeign-core``,启动报了错。

Z：可能是父pom位置的问题，不能单独引入该坐标。

## Demo   

M：怎么使用Feign进行接口调用呢？

Z：SpringCloud对Feign进行了封装，当我们搭建好注册中心Eureka之后，将服务注册到Eureka上，就可以通过Feign进行调用。

Z：提供者client1服务像往常Controller的写法一样

```java
@RequestMapping("/hello")
public String index(@RequestParam String name){
    return "hello" + name + ",this is first message";
}
```

消费者client2服务添加Feign接口,name指向配置文件的``spring.application.name``

```java
@FeignClient(name= "client1")
public interface IHelloRemote {
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam String name);
}
```

Z：编写完Feign之后，相当于在当前服务构建了一个Service，通过注入的方式就可以使用远程client1的Controller方法

```java
@RestController
public class ConsumerController {

    @Autowired
    IHelloRemote helloRemote;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name);
    }

}
```

## 熔断  

Z：如果服务存在多个层级调用，一个服务故障时，可能会导致级联故障，从而整个系统奔溃。

为了应对服务雪崩，可以使用熔断器。Fallback是一个降级操作，请求出现异常时，返回缓存值或者默认值。

M：熔断器要怎么使用呢？

Z：如下操作：

1. application.properties添加

2. 实现Feign接口，声明熔断时返回的内容（缓存值或者默认值）

   ```java
   /**
    * 处理熔断返回值
    */
   @Service
   public class HelloRemoteImpl implements IHelloRemote {
   
       @Override
       public String hello(String name) {
           return "发生熔断了"; //熔断返回false
       }
   }
   ```

3. 在Feign接口的方法上添加``fallback = HelloRemoteImpl.class``属性   

   ```java
   @FeignClient(name= "client1", fallback = HelloRemoteImpl.class)
   public interface IHelloRemote {
       @RequestMapping(value = "/hello")
       public String hello(@RequestParam String name);
   }
   ```

   完成以上操作之后，一旦服务奔溃，就会执行熔断器返回的内容