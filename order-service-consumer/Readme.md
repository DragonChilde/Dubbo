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