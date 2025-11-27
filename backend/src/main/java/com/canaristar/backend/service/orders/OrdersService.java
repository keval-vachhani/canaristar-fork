package com.canaristar.backend.service.orders;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.enums.OrdersType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface OrdersService {
    Orders createOrder(Orders orders);
    Orders updateOrder(String id, Orders orders);
    Orders findOrderById(String id);
    List<Orders> findOrdersByUserId(String userId);
    List<Orders> findAllOrders();
    List<Orders> findOrdersBetween(LocalDateTime start, LocalDateTime end);
    List<Orders> findOrdersByOrderType(OrdersType ordersType);
}
