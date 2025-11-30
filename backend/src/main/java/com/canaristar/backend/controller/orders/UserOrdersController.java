package com.canaristar.backend.controller.orders;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.entity.orders.UserOrders;
import com.canaristar.backend.service.userOrders.UserOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-orders")
public class UserOrdersController {

    @Autowired
    private UserOrdersService userOrdersService;

    @GetMapping("/{userId}")
    public UserOrders getUserOrders(@PathVariable String userId) {
        return userOrdersService.findByUserId(userId);
    }

    @GetMapping("/{userId}/{orderId}")
    public Orders getUserOrderById(@PathVariable String userId, @PathVariable String orderId) {
        return userOrdersService.findOrderById(userId, orderId);
    }

    @PostMapping("/{userId}")
    public UserOrders addOrderToUser(@PathVariable String userId, @RequestBody Orders order) {
        return userOrdersService.addOrderToUser(userId, order);
    }
}
