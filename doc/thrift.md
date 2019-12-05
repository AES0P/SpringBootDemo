# Thrift   

M：Thrift有什么作用？

Z：当系统之间的语言不通，Thrift可以用来做跨平台的高效服务。

M：window怎么安装Thrift呢？

Z：步骤如下：

1. 下载thrift的exe文件，[下载地址](http://thrift.apache.org/download)   
2. 将thrift文件更名为``thrift.exe``,存放在C盘的Thrift文件夹下``C:\Thrift``
3. 添加文件夹目录到Path系统环境变量中

通过``thrift -version``可以查看是否成功安装

也可以将thrift语法转化为指定语言的语法，例如demo.thrift

```
namespace java com.imooc.thrift.demo
namespace py thrift.demo

service DemoService{
	void sayHello(1:string name);
}
```

使用命令``thrift --gen java demo.thrift``转化为java，使用``thrift --gen py demo.thrift``转化为python




