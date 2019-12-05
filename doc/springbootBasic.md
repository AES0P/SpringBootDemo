# SpringBoot入门    

## 新建SpringBoot项目

Z：使用IntelliJ IDEA  ， 其破解地址为：``http://idea.lanyus.com/``    或者  ``https://jetlicense.nss.im/``   

M：怎么创建springBoot项目呢？

Z： 如下（也可以通过``https://start.spring.io/``页面进行创建）

1. New Project -- Spring Initializr -- 选择web

   ![](../imgs/boot01.png)  

   确定文件路径	![](../imgs/boot02.png)	  

2. 选择版本，组件

   ![](../imgs/boot03.png)  

3. 选择路径进行保存。删除没用的文件

   ![](../imgs/boot04.png)  

M：如果我需要pom文件依赖其他的父pom，而不是依赖``spring-boot-starter-parent``，该怎么做呢？

Z：添加以下内容，同样可以实现依赖继承：

```xml
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-dependencies</artifactId>
				<version>2.1.1.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<!--修改打包方式 -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<version>2.1.1.RELEASE</version>
				<executions>
					<execution>
						<goals>
							<goal>repackage</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```





## 启动SpringBoot项目

Z：运行自动生成的XXApplication类，其必须带有``@SpringBootApplication``注解，右键Run XX即可启动项目。   

```java
@SpringBootApplication
public class HellospringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(HellospringbootApplication.class, args);
	}
}
```

M：为什么我没有Run XX按钮？

Z：idea在初次启动的时候需要加载许多东西，建议maven使用阿里云的仓库，加载完之后才会出现Run XX按钮。

Z：当出现此页面的时候，说明springBoot启动成功  

![](../imgs/boot05.png)   

M：怎么编写一个Controller文件呢？

Z：添加类似Spring的注解，启动即可访问。类文件的必须属于Application.java的兄弟结点或者兄弟子节点。

```java
@RestController
public class HelloController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String say(){
        return "Hello spring Boot!";
    }
}
```

![](../imgs/boot08.png)  

## 添加配置文件

Z：新建的项目中，application.properties就是新建项目默认的配置文件。这里可以对访问端口和访问路径进行配置。

```properties
server.port=8081
server.context-path=/girl
```

application.yml也是默认配置文件，其使用分组的格式，``:之后必须加 空格 ，子内容前面为 tab键   ``

```properties
server:
	port: 8081
    tomcat:
    #    字符编码
    	uri-encoding: UTF-8
	context-path: /girl

```

M：yml可以配置java代码中注入的值吗？

Z：也行，直接写  ``键:值``，用``@Value("${键}")``的方式即可注入。  

M：那可以用@Value把配置文件内容注入到java中，那要怎么注入到xml中呢?

Z：直接用${}就可以引用了

```properties
server:
	port: 8081
age: 18
size: B
content: "size: ${size}，age: ${age}"
```

M：当配置文件需要频繁变换，怎么灵活切换呢？

Z：将其写成两个配置文件，而主配置文件只要选好要哪一个配置文件即可。   

1. 新建两个配置文件 application-dev.yml   &  application-prod.yml

2. 在application.yml中指定调用哪一个配置文件：

   ```properties
   spring:
   	profiles:
   		active:dev
   ```

   调用dev后缀的配置文件。

## 遇到的问题

M：为什么程序启动失败？

Z：单元测试的失败会影响到服务的启动。

M：为什么扫描引用不到？

上Z：Pojo等应该放在Application的同级或同级子目录下。   











## 注解

### 1.@Component  & @ConfigurationProperties  

M：一个个属性注入太麻烦了，有没有注入对象的方法？

Z：修改配置文件为组的形式，编写pojo对象映射，再将pojo对象注入

```properties
server:
	port: 8081
girl:
	age: 18
	size: B
```

pojo对象，需要``@Component ``定义Spring管理Bean，``@ConfigurationProperties``指定前缀内容。

``@Component``注解相当于:@Service,@Controller,@Repository，并下面类纳入进spring容器中管理。这样才能被下一层@Autowired注入该对象。 

```java
@Component
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {

    private String size;

    private Integer age;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    ...
}
```

运行``@SpringBootApplication``,即可访问Controller的内容   

```java
@RestController
public class HelloController {

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String say(){
        return girlProperties.getSize();
    }
}
```

### 2.@RestController   

Z：@RestController  =  @ResponseBody + @Controller  

### 3.@RequestMapping

1. @RequestMapping可以指定多个value： ``@RequestMapping(value={"/say","/hi"})`` 。   

2. @RequestMapping的Get请求获取参数的方式：

   1. 方式一PathVariable：访问地址中间参数传输：

      ```java
          @RequestMapping(value="/{id}/say",method = RequestMethod.GET)
          public String say(@PathVariable("id") Integer id){
              return "Hello Spring Boot:"+id;
          }
      ```

      url访问地址可以将id中间位置：``http://localhost:8080/hello/233333/say``   

   2. 方式二RequestParam：访问地址后面传值：  

      ```java  
          @RequestMapping(value="/say",method = RequestMethod.GET)
          public String say(@RequestParam("id") Integer id){
              return "Hello Spring Boot:"+id;
          }
      ```

      - url访问方式：``http://localhost:8080/hello/say?id=110``     
      - 添加默认值：``(@RequestParam(value = "id", required = false, defaultValue = "0") Integer id)``  ,如何不传id，它就会默认为0。  

   3. ``@RequestMapping(value="/say",method = RequestMethod.GET)``也可以写成``GetMapping(value="/say")``的方式。   

### 4.@Transactional  

M：当我一个Service的方法里有两条sql插入操作，怎么保证其同时执行成功或者同时执行失败？

Z：在方法上面添加``@Transactional``注解，即说明其为同个事务。

```java
    @Transactional
    public void insertTwo(){
        Girl girlA = new Girl();
        girlA.setSize("A");
        girlA.setAge(10);
        girlRepository.save(girlA);

        Girl girlB = new Girl();
        girlB.setSize("BBBBB");
        girlB.setAge(20);
        girlRepository.save(girlB);
    }
```

M：为什么我添加之后，还是有一个成功，一个失败呢？

Z：只有在innodb引擎下事务才能工作。所以需要在数据库中执行``ALTER TABLE girl ENGINE=innodb``命令。
