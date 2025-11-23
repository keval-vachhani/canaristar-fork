package com.canaristar.backend.service.products;

import com.canaristar.backend.entity.Products;
import com.canaristar.backend.enums.ProductCategory;
import com.canaristar.backend.enums.ProductSubCategory;
import com.canaristar.backend.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSericeImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Products save(Products product) {
        return productsRepository.save(product);
    }

    @Override
    public Optional<Products> findById(String id) {
        return productsRepository.findById(id);
    }

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Optional<Products> findByProductName(String name) {
        return productsRepository.findByProductName(name);
    }

    @Override
    public List<Products> findByProductCategory(ProductCategory category) {
        return productsRepository.findByProductCategory(category);
    }

    @Override
    public List<Products> findByActive(boolean active) {
        return productsRepository.findByActive(active);
    }

    @Override
    public List<Products> findByFeatured(boolean featured) {
        return productsRepository.findByFeatured(featured);
    }

    @Override
    public List<Products> findByActiveAndFeatured(boolean active, boolean featured) {
        return productsRepository.findByActiveAndFeatured(active, featured);
    }

    @Override
    public List<Products> findByProductCategoryAndActive(ProductCategory category, boolean active) {
        return productsRepository.findByProductCategoryAndActive(category, active);
    }

    @Override
    public void deleteProduct(String productId) {
        productsRepository.deleteById(productId);
    }

    @Override
    public List<Products> findByProductSubCategory(ProductSubCategory category) {
        return productsRepository.findByProductSubCategory(category);
    }
}