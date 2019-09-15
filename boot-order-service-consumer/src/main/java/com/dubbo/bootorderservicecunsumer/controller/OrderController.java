package com.dubbo.bootorderservicecunsumer.controller;

import com.gmall.bean.UserAddress;
import com.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Lee
 * @create 2019-09-14 21:08
 */
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping("initOrder")
    public List<UserAddress> initOrder(@RequestParam("userid") String userId){
       return orderService.initOrder(userId);
    }
}
