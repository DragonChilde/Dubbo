package com.dubbo.bootorderservicecunsumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.gmall.bean.UserAddress;
import com.gmall.service.OrderService;
import com.gmall.service.UserService;

import java.util.List;

/**
 * @author Lee
 * @create 2019-09-14 21:03
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Reference
    UserService userService;
    @Override
    public List<UserAddress> initOrder(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        for (UserAddress userAddress : addressList) {
            System.out.println(userAddress.getUserAddress());
        }
        return addressList;
    }
}
