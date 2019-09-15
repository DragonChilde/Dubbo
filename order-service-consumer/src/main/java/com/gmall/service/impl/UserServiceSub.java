package com.gmall.service.impl;

import com.gmall.bean.UserAddress;
import com.gmall.service.UserService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Lee
 * @create 2019-09-15 20:30
 */
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
        return null;
    }
}
