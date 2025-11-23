package com.canaristar.backend.repository;

import com.canaristar.backend.entity.Products;
import com.canaristar.backend.enums.ProductCategory;
import com.canaristar.backend.enums.ProductSubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends MongoRepository<Products, String> {
    Optional<Products> findById(String id);
    List<Products> findAll();
    Optional<Products> findByProductName(String name);
    List<Products> findByProductCategory(ProductCategory category);
    List<Products> findByActive(boolean active);
    List<Products> findByFeatured(boolean featured);
    List<Products> findByActiveAndFeatured(boolean active, boolean featured);
    List<Products> findByProductCategoryAndActive(ProductCategory category, boolean active);
    List<Products> findByProductSubCategory(ProductSubCategory category);
}
