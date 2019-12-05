# 注册中心consul

## Consul工作原理

Z：SpringCloud除了支持Erueka，还支持consul。Consul工作原理如下：

![](../imgs/c01.png)  

1.  producer启动时，向consul发送post请求：ip和port

2. consul收到请求，每隔10s（默认）向producer发起健康请求

3. consumer发起get请求时，先从consul中拿出带ip和port的临时表，通过临时表向producer发送get请求

   该表10s会更新



consul的更多使用查看http://www.ityouknow.com/springcloud/2018/07/20/spring-cloud-consul.html





