# 部署微服务项目

M：首先通过docker下载启动eureka    

编写eureka的Dockerfile   ，使用jdk运行eureka的jar程序

M：eureka的jar程序是怎么来的？

研究Eureka，这具体是怎么用的东西？

Z：这是一个程序，我现在用的是作者发到网易云上的。

M：RANCHER是什么东西呢？www.cnrancher.com  

Z：一款管理docker的软件，需要使用Centor，保证虚拟机和主机网络互相ping通之后   

安装docker到虚拟机上，``yum install docker``  

Centor下启动Docker   ``systemctl start docker ``   

安装RANCHER，linux下运行``$sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher``     

使用镜像加速器，加速镜像下载：

修改文件：``vim /etc/docker/daemon.json``

```json
{	
"registry-mirrors":["https://fy707np5.mirror.aliyuncs.com"]
}
```

 执行命令：``systemctl daemon-reload``  , ``systemctl restart docker``  

Z：如果报错可以重启docker，上方的sudo语句   

通过访问服务器的 ip:port 可以访问rancher的界面









添加主机，使用当前站点。看添加所需的条件，符合之后。添加ip到页面中，复制脚本在服务器B上执行。



B服务器   

远程   ssh root@192.168.30.154   

安装docker： ``yum install docker``

启动docker：  ``systemctl start docker ``  



从基础架构就可以看到主机   






