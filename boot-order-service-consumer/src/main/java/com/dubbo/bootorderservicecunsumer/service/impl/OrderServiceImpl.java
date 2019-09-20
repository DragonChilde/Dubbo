package com.dubbo.bootorderservicecunsumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.gmall.bean.UserAddress;
import com.gmall.service.OrderService;
import com.gmall.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lee
 * @create 2019-09-14 21:03
 */
@Service
public class OrderServiceImpl implements OrderService {
    /*@Reference(url="localhost:20882")*/
    @Reference(loadbalance="random")//默认是random模式
    UserService userService;

    @HystrixCommand(fallbackMethod = "errorOrder")
    @Override
    public List<UserAddress> initOrder(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        for (UserAddress userAddress : addressList) {
            System.out.println(userAddress.getUserAddress());
        }
        return addressList;
    }

    public List<UserAddress> errorOrder(String userId) {

        return Arrays.asList(new UserAddress(10, "测试", "10", "测试", "测试", "Y"));
    }
}
