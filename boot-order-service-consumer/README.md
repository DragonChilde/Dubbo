**boot-order-service-consumer消费端**

Controller

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

服务层(这里使用了@Service和@Reference的Dubbo注解，标注Dubbo的服务进行注入)

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

