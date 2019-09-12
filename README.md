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


**工程架构**

**gmall-interface(公共接口层)**

所有公共接口层（model，service，exception…）

**Bean模型**

	public class UserAddress implements Serializable {
	    private Integer id;
	    private String userAddress; //用户地址
	    private String userId; //用户id
	    private String consignee; //收货人
	    private String phoneNum; //电话号码
	    private String isDefault; //是否为默认地址    Y-是     N-否
	
	    public UserAddress() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	
	    public UserAddress(Integer id, String userAddress, String userId, String consignee, String phoneNum,
	                       String isDefault) {
	        super();
	        this.id = id;
	        this.userAddress = userAddress;
	        this.userId = userId;
	        this.consignee = consignee;
	        this.phoneNum = phoneNum;
	        this.isDefault = isDefault;
	    }
	
	    public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    public String getUserAddress() {
	        return userAddress;
	    }
	    public void setUserAddress(String userAddress) {
	        this.userAddress = userAddress;
	    }
	    public String getUserId() {
	        return userId;
	    }
	    public void setUserId(String userId) {
	        this.userId = userId;
	    }
	    public String getConsignee() {
	        return consignee;
	    }
	    public void setConsignee(String consignee) {
	        this.consignee = consignee;
	    }
	    public String getPhoneNum() {
	        return phoneNum;
	    }
	    public void setPhoneNum(String phoneNum) {
	        this.phoneNum = phoneNum;
	    }
	    public String getIsDefault() {
	        return isDefault;
	    }
	    public void setIsDefault(String isDefault) {
	        this.isDefault = isDefault;
	    }
	
	}

**Server接口**
	
	<!--初始化OrderService-->
	public interface OrderService {
	    /**
	     * 初始化订单
	     * @param userId
	     * @return List<UserAddress>
	     */
	    public List<UserAddress> initOrder(String userId);
	}

	<!--初始化UserService-->
	public interface UserService {
	    /**
	     * 按照用户id返回所有的收货地址
	     * @param userId
	     * @return List<UserAddress>
	     */
	    public List<UserAddress> getUserAddressList(String userId);
	}


**order-service-consumer(订单服务模块)(消费接口)**

**pom.xml**

	 <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>dubbo</groupId>
            <artifactId>gmall-interface</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- 由于我们使用zookeeper作为注册中心，所以需要操作zookeeper
        dubbo 2.6以前的版本引入zkclient操作zookeeper
        dubbo 2.6及以后的版本引入curator操作zookeeper
        下面两个zk客户端根据dubbo版本2选1即可
        -->
        <!--
        <dependency>
			<groupId>com.101tec</groupId>
			<artifactId>zkclient</artifactId>
			<version>0.10</version>
		</dependency>

        -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.6.2</version>
        </dependency>
        <!-- curator-framework -->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>2.12.0</version>
        </dependency>
    </dependencies>

**applicationContext.xml**

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd
			http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
	
		!--自动扫包-->
	    <context:component-scan base-package="com.gmall.service.impl" />
	
	    <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
	    <dubbo:application name="order-service-consumer"/>
	
	    <!-- 使用zookeeper广播注册中心暴露发现服务地址 -->
	    <dubbo:registry protocol="zookeeper" address="120.77.237.175:9181" />
	
	    <!-- 生成远程服务代理，服务消费者引用服务配置 -->
	    <dubbo:reference id="userService" interface="com.gmall.service.UserService" />
	</beans>

**实现OrderService**

	@Service
	public class OrderServiceImpl implements OrderService {
	
	    @Autowired
	    UserService userService;
	
	    @Override
	    public List<UserAddress> initOrder(String userId) {
	        // TODO Auto-generated method stub
	        System.out.println("用户id："+userId);
	        //1、查询用户的收货地址
	        List<UserAddress> addressList = userService.getUserAddressList(userId);
	        for (UserAddress userAddress : addressList) {
	            System.out.println(userAddress.getUserAddress());
	        }
	        return addressList;
	    }
	}

**测试消费者**

	public class ConsumerApplication {
	    public static void main(String[] args) {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	        OrderService orderService = context.getBean(OrderService.class);
	        orderService.initOrder("1");
	        System.out.println("success!");
	
	    }
	}
**user-service-provider(用户服务模块)(服务接口)**

pom文件一样

**applicationContext.xml**

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd	http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd
			http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
	
	    <!-- 1、指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名） -->
	    <dubbo:application name="user-service-provider" />
	
	    <!-- 2、指定注册中心的位置(两种写法一样) -->
	    <!--<dubbo:registry address="zookeeper://120.77.237.175:9181" />-->
	    <dubbo:registry protocol="zookeeper" address="120.77.237.175:9181" />
	
	    <!-- 3、指定通信规则（通信协议/通信端口） -->
	    <dubbo:protocol name="dubbo" port="20882"/>
	
	    <!-- 4、暴露服务   ref：指向服务的真正的实现对象 -->
	    <dubbo:service interface="com.gmall.service.UserService" ref="userServiceImpl"/>
	
	    <!-- 5 和本地bean一样实现服务 -->
	    <bean id="userServiceImpl" class="com.gmall.service.impl.UserServiceImpl"/>
	</beans>

**实现UserService**

	/**
	 *  * 1、将服务提供者注册到注册中心（暴露服务）
	 *  * 		1）、导入dubbo依赖（2.6.2）\操作zookeeper的客户端(curator)
	 *  * 		2）、配置服务提供者
	 *  *
	 *  * 2、让服务消费者去注册中心订阅服务提供者的服务地址
	 */
	public class UserServiceImpl implements UserService {
	    @Override
	    public List<UserAddress> getUserAddressList(String userId) {
	        UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
	        UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");
	        return Arrays.asList(address1,address2);
	    }
	}

**开启服务提供者测试**

	public class ProviderApplication {
	    public static void main(String[] args) throws Exception {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	        context.start();
	        System.in.read();
	    }
	}
