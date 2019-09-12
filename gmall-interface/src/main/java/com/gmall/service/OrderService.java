package com.gmall.service;

import com.gmall.bean.UserAddress;

import java.util.List;

/**
 * @author Lee
 * @create 2019/9/10 16:46
 */
public interface OrderService {
    /**
     * 初始化订单
     * @param userId
     * @return List<UserAddress>
     */
    public List<UserAddress> initOrder(String userId);
}
