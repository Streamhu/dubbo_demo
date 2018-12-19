github参考地址：https://github.com/Streamhu/dubbo_demo
### 一、环境准备

```
1. 开发环境：idea + maven + jdk1.8 + tomcat8 

2. 主要框架：spring + dubbo + zookeeper

3. zookeeper已启动
```

<hr>

### 二、项目主结构搭建
##### 1. 新建maven空项目 dubbo_demo（父项目），引入jar包如下：
```
<!-- spring -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>4.2.5.RELEASE</version >
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>4.2.5.RELEASE</version>
</dependency>

```
##### 2. 新建maven子项目 dubbo_api(jar) 
##### 3. 新建maven子项目 生产者 dubbo_provvider(jar), 引入jar包如下：
```
<!-- api项目（声明接口）-->
<dependency>
    <groupId>hh</groupId>
    <artifactId>dubbo_api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<!-- dubbo -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>dubbo</artifactId>
    <version>2.5.3</version>
</dependency>
<!-- zookeeper -->
<dependency>
    <groupId>com.101tec</groupId>
    <artifactId>zkclient</artifactId>
    <version>0.9</version>
</dependency>
```
##### 4. 新建maven子项目 消费者 dubbo_consumer(jar)，jar包引入同上 

<hr>

### 三、代码示例
##### 1. dubbo_api项目
```
1）新建接口声明    

	public interface DemoService {

	    String sayHello(String name);
	}

2）写完类记得 mvn clean package ，因为生产者和消费者都会引入
```
##### 2. dubbo_provider项目（生产者）	
```
1）实现dubbo_api声明的接口类，是具体实现类：

	public class DemoServiceImpl implements DemoService {

	    @Override
	    public String sayHello(String name) {
	        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
	        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
	    }

	}	

2）resources目录下新建 dubbo_provider.xml 文件

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	       xmlns="http://www.springframework.org/schema/beans"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
	       http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

	    <!--定义了提供方应用信息，用于计算依赖关系；在 dubbo-admin 或 dubbo-monitor 会显示这个名字，方便辨识-->
	    <dubbo:application name="demo-provider"/>

	    <!--使用 zookeeper 注册中心暴露服务，注意要先开启 zookeeper-->
	    <dubbo:registry address="zookeeper://localhost:2181"/>

	    <!-- 用dubbo协议在20880端口暴露服务 -->
	    <dubbo:protocol name="dubbo" port="20880"/>

	    <!--使用 dubbo 协议实现定义好的 api.demoService接口-->
	    <dubbo:service interface="hh.test.dubbo.demo.DemoService" ref="demoService"/>

	    <!--具体实现该接口的 bean-->
	    <bean id="demoService" class="hh.test.dubbo.DemoServiceImpl"/>
	    
	</beans>

3）新建启动类（用main方法）Provider:	

	public class Provider {

	    public static void main(String[] args) throws Exception {

	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo_provider.xml"});
	        context.start();
	        System.out.println("----------------服务已启动，按任意键结束···········--------------------");
	        System.in.read(); // press any key to exit
	    }

	}

```
##### 3. dubbo_consumer项目（消费者）
```
1）resource目录下新建 dubbo_consumer.xml文件

	<?xml version="1.0" encoding="UTF-8"?>

	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	       xmlns="http://www.springframework.org/schema/beans"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
	       http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

	    <!-- 消费者应用名称 -->
	    <dubbo:application name="demo-consumer"/>

	    <!-- zookeeper注册中心 -->
	    <dubbo:registry address="zookeeper://localhost:2181"/>

	    <!-- 需要的服务接口-->
	    <dubbo:reference id="demoService" check="false" interface="hh.test.dubbo.demo.DemoService"/>

	</beans>

2）新建启动类（main）Consumer，以及简单调用（一般可能是web的controller的是消费者）:

	public class Consumer {

	    public static void main(String[] args) {
	        System.setProperty("java.net.preferIPv4Stack", "true");
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo_consumer.xml"});
	        context.start();
	        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

	        while (true) {
	            try {
	                Thread.sleep(10000);
	                String hello = demoService.sayHello("world"); // call remote method
	                System.out.println(hello); // get result

	            } catch (Throwable throwable) {
	                throwable.printStackTrace();
	            }

	        }

	    }
	}			
					
```
##### 4. 项目启动
```
1）启动 dubbo_provider

2) 启动 dubbo_consumer	
```

<hr>

### 四、dubbo_admin（dubbo监控管理）

```
1. 去版本发布地址（https://github.com/apache/incubator-dubbo/releases ）下载 2.6.0以前的源码(2.6.0以后是新的，功能还没完善，先用以前的)		

2. idea打开 dubbo_admin项目，mvn clean package

3. 根据自己的zookeeper地址修改dubbo.properties的配置项 dubbo.registry.address=zookeeper://localhost:2181 （样例）

4. tomcat启动，localhost:8080启动 （账号root  密码:root, guest是错的。。），即可看到监控页面
```


<hr>

#### **参考网址**
[搭建简单的Dubbo实例](https://blog.csdn.net/ljlsblog/article/details/79622952)


<font color=#FF4500 size=3>注：文章是经过参考其他的文章然后自己整理出来的，有可能是小部分参考，也有可能是大部分参考，但绝对不是直接转载，觉得侵权了我会删，我只是把这个用于自己的笔记，顺便整理下知识的同时，能帮到一部分人。
ps : 有错误的还望各位大佬指正,小弟不胜感激</font>