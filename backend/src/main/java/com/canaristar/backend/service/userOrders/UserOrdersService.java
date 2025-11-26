package com.canaristar.backend.service.userOrders;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.entity.orders.UserOrders;

public interface UserOrdersService {
    UserOrders save(UserOrders userOrders);
    UserOrders findByUserId(String userId);
    Orders findOrderById(String userId, String orderId);
    UserOrders addOrderToUser(String userId, Orders order);
}
