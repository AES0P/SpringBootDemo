# zuul路由

## 介绍

Z：添加APi网关之后，所有的url请求就都需要通过一道墙，这道墙可以对请求进行权限控制。API Gateway是微服务轻量级网关。   

   ![img](../imgs/g01.png)  

## 路由跳转   

M：怎么实现路由跳转呢？

Z：步骤如下：

1.  引入pom依赖

   ```xml
   <dependency>
   	<groupId>org.springframework.cloud</groupId>
   	<artifactId>spring-cloud-starter-zuul</artifactId>
   </dependency>
   ```

2. 添加跳转路径配置

   ```properties
   spring.application.name=gateway-service-zuul
   server.port=8888
   
   zuul.routes.baidu.path=/baidu/** 
   zuul.routes.baidu.url=http://www.baidu.com/
   ```

3. 添加``@EnableZuulProxy``注解，启动。访问/test/**路径，将会自动跳转到百度页面

Z：一般情况下是路由到其他服务的页面上，还可以带参数跳转

1. 配置路径跳转

   ```properties
   zuul.routes.hello.path=/hello/**
   zuul.routes.hello.url=http://localhost:9000/
   ```

2. 访问路径为``http://127.0.0.1:8888/hello/hello?name=sky``

   相当于将路径进行替换``http://127.0.0.1:9000/hello?name=sky``后访问  

## 服务化   

M：路径跳转是直接配置zuul的url，而url是不固定的，怎么处理？

Z：将zuul注册到Eureka，通过serviceId的方式调用目标服务

1. 修改配置文件

   ```properties
   spring.application.name=gateway-service-zuul
   server.port=8888
   
   zuul.routes.api-a.path=/producer/**
   zuul.routes.api-a.serviceId=spring-cloud-producer
   
   eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
   ```

   将原先的url改为serviceId，指向目标服务。注册zuul到eureka

Z：serviceId对应的不一定只是一个服务，可能是多个同名的服务，从而实现负载均衡。

Z：zuul还提供了默认的跳转路径，格式为：``http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**``

,访问``http://localhost:8888/spring-cloud-producer/hello?name=sky``也可以实现路由跳转。   

## 自定义过滤器

M：如果我想用zuul做权限控制，该怎么实现呢？

Z：需要做以下修改

### 1.自定义Filter类

添加TokenFilter类，继承ZuulFilter类

```java
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() { //过滤器内容
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getParameter("token");// 获取请求的参数

        if (StringUtils.isNotBlank(token)) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
    }

}
```

- filterType方法：定义Filter的生命周期，4个周期分别是“PRE”、“ROUTING”、“POST”、“ERROR”

  ![](../imgs/z01.png)  

- filterOrder方法：指定执行顺序，Zuul默认Filter顺序如下：

  | 类型  | 顺序 | 过滤器                  | 功能                       |
  | ----- | ---- | ----------------------- | -------------------------- |
  | pre   | -3   | ServletDetectionFilter  | 标记处理Servlet的类型      |
  | pre   | -2   | Servlet30WrapperFilter  | 包装HttpServletRequest请求 |
  | pre   | -1   | FormBodyWrapperFilter   | 包装请求体                 |
  | route | 1    | DebugFilter             | 标记调试标志               |
  | route | 5    | PreDecorationFilter     | 处理请求上下文供后续使用   |
  | route | 10   | RibbonRoutingFilter     | serviceId请求转发          |
  | route | 100  | SimpleHostRoutingFilter | url请求转发                |
  | route | 500  | SendForwardFilter       | forward请求转发            |
  | post  | 0    | SendErrorFilter         | 处理有错误的请求响应       |
  | post  | 1000 | SendResponseFilter      | 处理正常的请求响应         |

该自定义过滤器就是判断是否带token参数

### 2.添加@Bean

Z：在启动类中添加以下代码

```java
@Bean
public TokenFilter tokenFilter() {
	return new TokenFilter();
}
```

实现请求拦截，请求url方式为：``http://localhost:8888/spring-cloud-producer/hello?name=neo&token=xx``  

## 路由熔断

Z：路由器可以实现熔断，拦截返回特定内容。实现方式如下：

1. 新增ProducerFallback类（该类添加@Component注解），实现FallbackProvider接口

2. 实现getRoute方法，指定熔断的服务

   ```java
   public String getRoute() {
       return "spring-cloud-producer";
   }
   ```

3. 实现fallbackResponse方法，定义返回的内容

   ```java
   @Override
   public ClientHttpResponse fallbackResponse() {
       return new ClientHttpResponse() {
           @Override
           public HttpStatus getStatusCode() throws IOException {
               return HttpStatus.OK;
           }
   
           @Override
           public int getRawStatusCode() throws IOException {
               return 200;
           }
   
           @Override
           public String getStatusText() throws IOException {
               return "OK";
           }
   
           @Override
           public void close() {
   
           }
   
           @Override
           public InputStream getBody() throws IOException {
               return new ByteArrayInputStream("The service is unavailable.".getBytes());
           }
   
           @Override
           public HttpHeaders getHeaders() {
               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(MediaType.APPLICATION_JSON);
               return headers;
           }
       };
   }
   ```

   fallbackResponse方法还有重载方法，附加了异常信息的返回

   ```java
       @Override
       public ClientHttpResponse fallbackResponse(Throwable cause) {
           if (cause != null && cause.getCause() != null) {
               String reason = cause.getCause().getMessage();
               logger.info("Excption {}",reason);
           }
           return fallbackResponse();
       }
   ```

   当服务发生异常时，就会触发``fallbackResponse(Throwable cause)``,打印异常信息并返回``The service is unavailable.``到页面。

## 路由重试

M：如果因为网络或其他原因，导致服务不可用，希望路由对服务重试，怎么实现呢？

Z：步骤如下：

1. 添加spring-retry

   ```xml
   <dependency>
   	<groupId>org.springframework.retry</groupId>
   	<artifactId>spring-retry</artifactId>
   </dependency>
   ```

2. 配置文件开启Zuul Retry

   ```properties
   #是否开启重试功能
   zuul.retryable=true
   #对当前服务的重试次数
   ribbon.MaxAutoRetries=2
   #切换相同Server的次数
   ribbon.MaxAutoRetriesNextServer=0
   ```

   在服务调用失败，会再尝试请求两次

Z：但是这种方式存在缺陷，开启了重试，熔断器只有在所有实例都无法运作的情况下才能起作用。如果当前实例存在问题，压力就会转移到其他实例上，最终导致所有的实例都被压垮。  