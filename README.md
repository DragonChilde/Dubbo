# Dubbo #

**分布式基础理论**

分布式系统原理与范型定义：“分布式系统是若干独立计算机的集合，这些计算机对于用户来说就像单个相关系统”

分布式系统（distributed system）是建立在网络之上的软件系统。

随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需一个治理系统确保架构有条不紊的演进。

![](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568181254364&di=0e7f265f2354787f8a943856d3c0ee1e&imgtype=0&src=http%3A%2F%2Fwww.pc-fly.com%2Fuploads%2Fallimg%2F20170502%2F1493658203638_2.jpg)

*单一应用架构*

适用于小型网站，小型管理系统，将所有功能都部署到一个功能里，简单易用。

缺点： 1、性能扩展比较难 2、协同开发问题 3、不利于升级维护

*垂直应用架构*

通过切分业务来实现各个模块独立部署，降低了维护和部署的难度，团队各司其职更易管理，性能扩展也更方便，更有针对性
。
缺点： 公用模块无法重复利用，开发性的浪费

**什么叫RPC**

RPC【Remote Procedure Call】是指远程过程调用，是一种进程间通信方式，他是一种技术的思想，而不是规范。它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数，而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地的还是远程的函数，本质上编写的调用代码基本相同。

**RPC基本原理**

![](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568181774260&di=5d366ea9c7249fbc8071dfeb907268b2&imgtype=0&src=http%3A%2F%2Fpic2.zhimg.com%2Fv2-7f0d1d89c641d582c6873af571eb95b5_b.jpg)

![](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568181846920&di=1c05f889edbde13ee33e2dd1f8c0677a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180528%2F8482d98b927e4ee799df9400c91399f8.png)

**RPC两个核心模块：通讯，序列化。**

# dubbo核心概念 #

Apache Dubbo (incubating) |ˈdʌbəʊ| 是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。

[http://dubbo.apache.org/zh-cn/](http://dubbo.apache.org/zh-cn/)

![](http://dubbo.apache.org/img/architecture.png)


**服务提供者（Provider）**：暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。

**服务消费者（Consumer）**: 调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。

**注册中心（Registry）**：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者

**监控中心（Monitor）**：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心

调用关系说明:

- 服务容器负责启动，加载，运行服务提供者。
- 服务提供者在启动时，向注册中心注册自己提供的服务。
- 服务消费者在启动时，向注册中心订阅自己所需的服务。
- 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
- 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
- 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。


**整合Spring工程架构**

[**gmall-interface(公共接口层)**](https://github.com/DragonChilde/Dubbo/tree/master/gmall-interface)

[**order-service-consumer(订单服务模块)(消费接口)**](https://github.com/DragonChilde/Dubbo/tree/master/order-service-consumer)

[**user-service-provider(用户服务模块)(服务接口)**](https://github.com/DragonChilde/Dubbo/tree/master/user-service-provider)

[**整合SpringBoot**](https://github.com/apache/dubbo-spring-boot-project/blob/0.2.x/README_CN.md)

 	<!-- Dubbo Spring Boot Starter -->
        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>0.2.1</version>
        </dependency>

注意:SpringBoot2.1.x以上要用0.2.1版本,以下用0.1.1版本

**dubbo注解@Service、@Reference**

**如果没有在配置中写dubbo.scan.base-package,还需要使用@EnableDubbo注解**


**boot-order-service-consumer消费端**



**boot-user-service-provider服务端**

application.properties

	#端口号
	server.port=9002

	#是服务名，不能跟别的dubbo提供端重复
	dubbo.application.name=boot-user-service-provider
	#指定注册中心协议
	dubbo.registry.protocol=zookeeper
	#注册中心的地址加端口号
	dubbo.registry.address=120.77.237.175:9181
	#dubbo.protocol.port=20882
	#分布式固定是dubbo,不要改
	dubbo.protocol.name=dubbo
	#注解方式要扫描的包
	dubbo.scan.base-packages=com.dubbo.bootuserserviceprovider


实现 Dubbo 服务提供方

	package com.dubbo.bootuserserviceprovider.service.impl;

	import com.alibaba.dubbo.config.annotation.Service;
	import com.gmall.bean.UserAddress;
	import com.gmall.service.UserService;
	import org.springframework.stereotype.Component;
	
	import java.util.Arrays;
	import java.util.List;

	/**这里的dubbo @Service注解避免与spring的注解冲突，所以spring用了@Component指明***/
	/**暴露服务接口**/
	@Service
	@Component
	public class UserServiceImpl implements UserService {
	    @Override
	    public List<UserAddress> getUserAddressList(String userId) {
	        UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
	        UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");
	        return Arrays.asList(address1,address2);
	    }
	}

# Dubbo配置 #

**1. 配置原则**

![](https://img-blog.csdnimg.cn/20181031220111268.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l6MzU3ODIzNjY5,size_16,color_FFFFFF,t_70)

- JVM 启动 -D 参数优先，这样可以使用户在部署和启动时进行参数重写，比如在启动时需改变协议的端口。
- XML 次之，如果在 XML 中有配置，则 dubbo.properties 中的相应配置项无效。
- Properties 最后，相当于缺省值，只有 XML 没有配置时，dubbo.properties 的相应配置项才会生效，通常用于共享公共配置，比如应用名。

**启动时检查**

Dubbo 缺省会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成，以便上线时，能及早发现问题，默认 check="true"。

**通过 spring 配置文件**：

- 关闭某个服务的启动时检查 (没有提供者时报错)：

		<dubbo:reference interface="com.foo.BarService" check="false" />

- 关闭所有服务的启动时检查 (没有提供者时报错)：

		<dubbo:consumer check="false" />
- 关闭注册中心启动时检查 (注册订阅失败时报错)：

		<dubbo:registry check="false" />
	
**2. 重试次数**

失败自动切换，当出现失败，重试其它服务器，但重试会带来更长延迟。可通过 retries="2" 来设置重试次数(不含第一次，一共连接三次)

	    <!-- retries="":重试次数，不包含第一次调用，0代表不重试-->
   		 <!-- 幂等（设置重试次数）【查询、删除、修改】、非幂等（不能设置重试次数）【新增】 -->
	    <dubbo:reference id="userService" interface="com.gmall.service.UserService" timeout="1000" retries="3">
        	<dubbo:method name="getUserAddressList" timeout="1000" retries="3"/>
	    </dubbo:reference>
	    <!--全局超时配置-->
	    <dubbo:consumer timeout="1000" retries="3"/>

**假如同时有多个服务方(不同的端口),这时重试不会只连同一个服务端，会多个服务端都尝试连接**

**3. 超时时间**

由于网络或服务端不可靠，会导致调用出现一种不确定的中间状态（超时）。为了避免超时导致客户端资源（线程）挂起耗尽，必须设置超时时间。

**order-service-consumer(消费端)**

    <!-- timeout默认是1000ms-->
	<!--指定接口或者特定方法超时配置-->
    <dubbo:reference id="userService" interface="com.gmall.service.UserService" timeout="1000">
        <dubbo:method name="getUserAddressList" timeout="5000"/>
    </dubbo:reference>
	<!--全局超时配置-->
    <dubbo:consumer timeout="1000"/>

**user-service-provider(服务端)**

	<!--指定接口或者特定方法超时配置-->
	<dubbo:service interface="com.gmall.service.UserService" ref="userServiceImpl" timeout="1000">
        <dubbo:method name="getUserAddressList" timeout="5000"/>
    </dubbo:service>
	<!--全局超时配置-->
    <dubbo:provider timeout="1000"/>

**配置原则**

**dubbo推荐在Provider上尽量多配置Consumer端属性：**

1. 作为服务的提供者，比服务使用方更清楚服务性能参数，如调用的超时时间，合理的重试次数，等等
2. 在Provider配置后，Consumer不配置则会使用Provider的配置值，即Provider配置可以作为Consumer的缺省值。否则，Consumer会使用Consumer端的全局设置，这对于Provider不可控的，并且往往是不合理的

**配置的覆盖规则:**

**1）、精确优先 (方法级优先，接口级次之，全局配置再次之)**

**2）、消费者设置优先(如果级别一样，则消费方优先，服务提供方次之)**

备住:这里以timeout属性为例

![](https://img-blog.csdnimg.cn/20190726161213171.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNjExNjc2,size_16,color_FFFFFF,t_70)

**4. 版本号**

当一个接口实现，出现不兼容升级时，可以用版本号过渡，版本号不同的服务相互间不引用。可以按照以下的步骤进行版本迁移：

- 在低压力时间段，先升级一半提供者为新版本
- 再将所有消费者升级为新版本
- 然后将剩下的一半提供者升级为新版本

		<!--服务端-->
		<!--旧版本服务添加版本号-->
	    <dubbo:service interface="com.gmall.service.UserService" ref="userServiceImpl" version="1.0.0" />
	    <bean id="userServiceImpl" class="com.gmall.service.impl.UserServiceImpl"/>
	
		<!--新版本服务号添加版本号-->
	    <dubbo:service interface="com.gmall.service.UserService" ref="userNewServiceImpl" version="2.0.0"/>
	    <bean id="userNewServiceImpl" class="com.gmall.service.impl.UserNewServiceImpl"/>

		<!--消费端-->
		<!--可通过指定version来指定调用哪个版本的服务,也可以通过*来随机调用-->
		<dubbo:reference id="userService" interface="com.gmall.service.UserService" timeout="1000" retries="3" version="2.0.0">

[**5. 本地存根**](http://dubbo.apache.org/zh-cn/docs/user/demos/local-stub.html)

远程服务后，客户端通常只剩下接口，而实现全在服务器端，但提供方有些时候想在客户端也执行部分逻辑，比如：做 ThreadLocal 缓存，提前验证参数，调用失败后伪造容错数据等等，此时就需要在 API 中带上 Stub，客户端生成 Proxy 实例，会把 Proxy 通过构造函数传给 Stub [1]，然后把 Stub 暴露给用户，Stub 可以决定要不要去调 Proxy。

![](http://dubbo.apache.org/docs/zh-cn/user/sources/images/stub.jpg)

	/**在消费端提供实现***/
	public class UserServiceSub implements UserService {
	    private final UserService userService;
	
	    // 构造函数传入真正的远程代理对象
	    public UserServiceSub(UserService userService) {
	        this.userService = userService;
	    }
	
	    @Override
	    public List<UserAddress> getUserAddressList(String userId) {
	        System.out.println("UserServiceSub");
	        // 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
	        if (!StringUtils.isEmpty(userId)){
	           return userService.getUserAddressList(userId);
	        }
			// 可以容错，可以做任何AOP拦截事项
	        return null;
	    }
	}

在消费端spring配置文件中按以下方式

	 <dubbo:reference id="userService" interface="com.gmall.service.UserService" timeout="1000" retries="3" version="2.0.0" stub="com.gmall.service.impl.UserServiceSub">

1. Stub必须有可传入 Proxy 的构造函数

2. 在interface旁边放一个Stub实现，它实现BarService接口，并有一个传入远程BarService实例的构造函数（在实际开发中Stub是放在公共的interface模块里的实现的,本地试验并没作处理）