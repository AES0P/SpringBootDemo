# dockerfile   

#### 1.RUN

M：RUN指令有什么用呢？

Z：镜像**构建过程中**运行的命令，有两种模式

- shell模式

  是以``/bin/sh -c command``执行指令,每个RUN指令会在镜像的上层创建新的镜像，运行指定的命令。

- exec模式

  指定其他形式的shell运行指令RUN["/bin/bash","-c","echo hello"]  

M：怎么将两条RUN指令合并成一条呢？

Z：用&& ``RUN apt-get update && apt-get install -y nginx``  

#### 2.EXPOSE  

M：EXPOSE指令有什么用？

Z：指定运行该镜像的容器所使用的端口。可以指定多个端口``EXPOSE 80``

M：指定了就会打开端口吗？

Z：并不会自动打开，还需要在运行的时候映射端口。``docker run -p 80 -d ...``  

#### 3.CMD  

M：CMD指令有什么用？

Z：用来提供容器**运行时运行**的命令，主要用来指定容器启动时的启动软件的命令（容器运行时前台启动nginx）。docker run指定运行命令时，CMD里面的命令会被覆盖，不会执行。

M：覆盖是怎么一种情况？

Z：例如dockerfile里面写了CMD,那么``docker run -p 80 --name cmd_test -d dormancypress/df_test /bin/bash``中的``/bin/bash``就会覆盖CMD的内容。

M：CMD指令有什么运行模式？

Z：三种

- shell模式
- exec模式
- 纯参数模式，和ENTRYPOINT搭配使用，提供ENTRYPOINT的默认参数

#### 4.ENTRYPOINT   

Z：ENTRYPOINT与CMD命令相似，并且不会被docker run启动命令所覆盖。

M：如果命令不想被覆盖，只想覆盖参数，怎么做呢？

Z：可以将CMD和ENTRYPOINT配合使用。CMD指定参数，ENTRYPOINT指定命令。   

```properties
ENTRYPOINT ["/usr/sbin/nginx"]
CMD ["-h"]
```

如果运行容器的时候不想用默认参数，可以将参数替换掉``... df_test5 -g "daemon off;"``   

#### 5.ADD & COPY   

M：ADD和COPY指令有什么区别呢？

Z：ADD自带解压功能，而COPY是单纯复制文件   

#### 6.VOLUME

Z：VOLUME["/data"]可以基于容器添加卷，一个卷可以存在一个或多个容器的特定目录，实现数据共享和数据持久化   

#### 7.WORKDIR  &  ENV

Z：WORKDIR可以设定工作路径，构建镜像的时候可能使用到。ENV设定的是环境变量，构建和运行过程都有效。   

例如：``ENV PATH /usr/local/swftools/bin:$PATH ``  

#### 8.USER

Z：USER指令用来指定镜像为什么用户运行，例如``USER nginx``下，就会以ngixn身份运行。如果不指定用户，默认使用root用户。

#### 9.ONBUILD

Z：ONBUILD[指令]触发器，当一个镜像被作为基础镜像运行时，会在构建过程中插入触发器中的指令。

M：ONBUILD实际使用场景？

Z：镜像A的dockerfile

```properties
...
ONBUILD COPY index.html /usr/share/nginx/html/
```

在构建镜像时是不执行COPY指令的

在镜像B的dockerfile中

```properties
FORM 镜像A
...
```

在构建镜像B时，COPY命令才会执行  

### docker构建原理  

M：docker构建过程发生了什么呢？

Z：每个步骤就是创建一个中间层镜像，并进行提交。利用docker run中间层镜像，可以对dockerfile的构建过程进行调试。

M：什么是构建缓存呢？

Z：构建时，中间生成的镜像将会作为构建缓存，在下次构建的过程中构建会变得高效（表示using cache）。

M：如果不想使用构建缓存怎么办？

Z：使用命令``docker build   --no-cache``。也可以在dockerfile中添加环境变量``ENV REFRESH_DATE 2018-09-28``,当修改时间的时候，缓存就会进行刷新。

M：怎么查看构建过程呢？

Z：使用命令``docker history AAA/df_test1``   
