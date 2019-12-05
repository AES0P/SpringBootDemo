# Kubernetes

M：Kubernetes是什么？

Z： k8s是一个服务编排容器的工具。在k8s进行管理应用的时候，基本步骤是：创建集群，部署应用，发布应用，扩展应用，更新应用，还可以提供容器的健康状态管理以及复制功能。

M：什么是**编排**呢？

Z：简单来说就是配置，协作，管理docker容器。

**应用编排服务：**使容器之间能够通信、彼此可以传递运行期,同时管理多个容器的行为。当容器集群共同构建应用架构时，需要考虑集群环境中的容器，哪些端口需要暴露、哪些卷需要挂载等信息。   

![](D:/github_place/microservice/imgs/m01.png)    

```
Pod
Container（容器）
Label(label)（标签）
Replication Controller（复制控制器）
Service（enter image description here）（服务）
Node（节点）
Kubernetes Master（Kubernetes主节点）
```

