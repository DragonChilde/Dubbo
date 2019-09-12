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