# Docker   

## 安装后配置

安装docker之后，建议在Settings中将Advanced挪到非系统盘的位置，适当修改内存等资源。

并且在Daemon中添加国内的Registry mirrors，方便拉取线上的镜像。

```
https://docker.mirrors.ustc.edu.cn
https://hub-mirror.c.163.com
http://f1361db2.m.daocloud.io
```

## Docker常用命令

通过``docker pull XX``可以直接从仓库中拉取镜像。（国内网易镜像中心的 https://c.163yun.com/hub#/m/home/）

常用命令还有，``--``表示参数全称，``-``表示参数简称：

- 查看镜像列表：``docker images``   

- 查看帮助：``docker {命令} --help``

  在帮助列表中，``--hostname string``表示参数未string的变量，``--init``表示无参变量

- 运行镜像：``docker run hello-world``   

- 删除容器：``docker rm {container的id}``

- 删除镜像：``docker rmi {image的id}``   

- 查看进程：``docker ps``，查看所有的容器，包括未启动的就加``-a``参数   

- 进入容器内部：``docker exec -it {进程的id} bash``   

- 停止容器：``docker stop {进程的id}``

- 启动容器：镜像在运行后会保存为容器，下次只要直接``docker start {容器的id}``，重启用``docker restart``

## 容器运行案例

启动zookeeper命令

```
docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env 
```

启动kafka命令：

```
KAFKA_ADVERTISED_HOST_NAME=192.168.31.51 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest
```

参数解析：



### 自定义镜像   

M：首先要做什么？

Z：编写Dockerfile，然后build构建镜像   

1. 使用tomcat镜像作为基础镜像，去把tomcat镜像下载下来。

2. 编写Dockerfile  ``vi Dockerfile``  

   ```properties
   # 基础镜像
   from hub.c.163.com/library/tomcat
   
   MAINTAINER leekoko xxx@qq.com
   
   COPY jpress.war /usr/local/tomcat/webapps
   ```

3. 到Dockerfile所在目录下运行 ``docker build -t dockName:latest .``  构建项目，使用 ``docker images`` 即可看到已创建的镜像。

M：win10下的Dockerfile怎么创建？

Z：直接右键新建txt文件，去掉txt后缀即可

M：docker的run没有留下进程？

Z：可能是命令没有卡住，构建dockerfile，添加``CMD tail -f /dev/null ``,即可以将容器启动卡住。

## 上传镜像

Z：docker怎么上传镜像的步骤

1. 添加dockerfile

   ```
   FROM 192.168.0.115:5000/runimage-java:jre-8
   COPY . home
   WORKDIR home
   CMD java -jar -Dloader.path=. eda-service-center-1.0.0-SNAPSHOT.jar
   ```

2. tag修改镜像名

   ```
   docker tag eda-service-center 10.197.70.13:5000/microservice/eda-service-center
   ```

M：为什么推送不到远程仓库上？

Z：需要在docker的setting中添加Daemon地址