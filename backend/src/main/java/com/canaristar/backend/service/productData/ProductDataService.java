package com.canaristar.backend.service.productData;

import com.canaristar.backend.entity.ProductData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductDataService {
    ProductData save(ProductData productData);
    Optional<ProductData> findById(String id);
    Optional<ProductData> findByProductId(String productId);
    ProductData setRating(ProductData productData);
    float getRating(ProductData productData);
    ProductData addProductView(String productId);
    ProductData addOrderId(String productId, String orderId);
    List<ProductData> getAll();
    ProductData getByProductId(String productId);
    List<ProductData> getTopViewed();
    List<ProductData> getTopOrdered();
    List<ProductData> getTopRated();
}
