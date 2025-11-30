package com.canaristar.backend.repository;

import com.canaristar.backend.entity.ProductData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDataRepository extends MongoRepository<ProductData, String> {
    Optional<ProductData> findByProductId(String productId);
}
