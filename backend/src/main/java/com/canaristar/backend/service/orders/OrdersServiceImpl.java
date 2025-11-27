package com.canaristar.backend.service.orders;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.enums.OrdersType;
import com.canaristar.backend.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders createOrder(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public Orders updateOrder(String id, Orders req) {
        Orders old = findOrderById(id);

        if (old == null) {
            return null;
        }

        old.setRemarks(req.getRemarks());
        old.setOrdersType(req.getOrdersType());
        old.setUpdatedAt(LocalDateTime.now());

        return ordersRepository.save(old);
    }

    @Override
    public Orders findOrderById(String id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> findOrdersByUserId(String userId) {
        return ordersRepository.findByUserId(userId);
    }

    @Override
    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> findOrdersBetween(LocalDateTime start, LocalDateTime end) {
        return ordersRepository.findByCreatedAtBetween(start, end);
    }


    @Override
    public List<Orders> findOrdersByOrderType(OrdersType ordersType) {
        return ordersRepository.findByOrdersType(ordersType);
    }
}
