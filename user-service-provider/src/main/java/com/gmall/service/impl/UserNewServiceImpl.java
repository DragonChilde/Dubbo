package com.gmall.service.impl;

import com.gmall.bean.UserAddress;
import com.gmall.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 *  * 1、将服务提供者注册到注册中心（暴露服务）
 *  * 		1）、导入dubbo依赖（2.6.2）\操作zookeeper的客户端(curator)
 *  * 		2）、配置服务提供者
 *  *
 *  * 2、让服务消费者去注册中心订阅服务提供者的服务地址
 */
public class UserNewServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
       /* try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getUserAddressList .....1");*/
        System.out.println("UserNewServiceImpl");
        UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
        UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");
        return Arrays.asList(address1,address2);
    }
}
