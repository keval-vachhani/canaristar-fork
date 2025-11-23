package com.canaristar.backend.service.products;

import com.canaristar.backend.entity.Products;
import com.canaristar.backend.enums.ProductCategory;
import com.canaristar.backend.enums.ProductSubCategory;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Products save(Products product);
    Optional<Products> findById(String id);
    List<Products> findAll();
    Optional<Products> findByProductName(String name);
    List<Products> findByProductCategory(ProductCategory category);
    List<Products> findByActive(boolean active);
    List<Products> findByFeatured(boolean featured);
    List<Products> findByActiveAndFeatured(boolean active, boolean featured);
    List<Products> findByProductCategoryAndActive(ProductCategory category, boolean active);
    void deleteProduct(String productId);
    List<Products> findByProductSubCategory(ProductSubCategory category);
}