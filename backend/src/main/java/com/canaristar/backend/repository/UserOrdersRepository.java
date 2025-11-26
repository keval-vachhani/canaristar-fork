package com.canaristar.backend.repository;

import com.canaristar.backend.entity.orders.UserOrders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOrdersRepository extends MongoRepository<UserOrders, String> {
    Optional<UserOrders> findByUserId(String userId);
}
