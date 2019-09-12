package com.gmall.service;

import com.gmall.bean.UserAddress;

import java.util.List;

/**
 * @author Lee
 * @create 2019/9/10 16:47
 */
public interface UserService {
    /**
     * 按照用户id返回所有的收货地址
     * @param userId
     * @return List<UserAddress>
     */
    public List<UserAddress> getUserAddressList(String userId);
}
