package com.canaristar.backend.repository;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.enums.OrdersType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends MongoRepository<Orders, String> {
    List<Orders> findByUserId(String userId);
    List<Orders> findByOrdersType(OrdersType ordersType);
    List<Orders> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
