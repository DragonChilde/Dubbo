**boot-user-service-provider服务端**

**整合方式一**

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

	/**这里是dubbo @Service注解避免与spring的注解冲突，所以spring用了@Component指明***/
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

启动开启注解@EnableDubbo

	@EnableDubbo
	@SpringBootApplication
	public class BootUserServiceProviderApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(BootUserServiceProviderApplication.class, args);
	    }
	
	}

**整合方式二**

保留dubbo xml配置文件(provider.xml)

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd	http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd
			http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
	
	    <!-- 1、指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名） -->
	    <dubbo:application name="boot-user-service-provider" />
	
	    <!-- 2、指定注册中心的位置 (两种写法一样)-->
	    <!--<dubbo:registry address="zookeeper://120.77.237.175:9181" />-->
	    <dubbo:registry protocol="zookeeper" address="120.77.237.175:9181" />
	
	    <!-- 3、指定通信规则（通信协议/通信端口） -->
	    <dubbo:protocol name="dubbo" port="20882"/>
	
	    <!-- 4、暴露服务   ref：指向服务的真正的实现对象 -->
	    <dubbo:service interface="com.gmall.service.UserService" ref="userServiceImpl">
	        <!--<dubbo:method name="getUserAddressList" timeout="5000"/>-->
	    </dubbo:service>
	    <!-- 5 和本地bean一样实现服务 -->
	   <!-- <bean id="userServiceImpl" class="com.dubbo.bootuserserviceprovider.service.impl.UserServiceImpl"/>-->
	
	
	    <!-- 连接监控中心 -->
	    <dubbo:monitor protocol="registry"/>
	</beans>

服务层

	/**已在provider.xml配了service就不需要用@Service注解了**/
	@Component
	public class UserServiceImpl implements UserService {
	    @Override
	    public List<UserAddress> getUserAddressList(String userId) {
	        UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
	        UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");
	        return Arrays.asList(address1,address2);
	    }
	}

启动
	
	/**把dubbo的配置文件导入进来**/
	@ImportResource(locations = "classpath:provider.xml")
	@SpringBootApplication
	public class BootUserServiceProviderApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(BootUserServiceProviderApplication.class, args);
	    }
	
	}

**整合方式三**