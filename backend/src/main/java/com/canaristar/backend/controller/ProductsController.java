package com.canaristar.backend.controller;

import com.canaristar.backend.entity.Products;
import com.canaristar.backend.enums.ProductCategory;
import com.canaristar.backend.enums.ProductSubCategory;
import com.canaristar.backend.service.cloudinary.CloudinaryService;
import com.canaristar.backend.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CloudinaryService cloudinaryService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<Products> products = productService.findAll();

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") String productId) {
        Optional<Products> product = productService.findById(productId);

        if (product.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }


    @GetMapping("/name/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName) {
        Optional<Products> product = productService.findByProductName(productName);

        if (product.isEmpty()) {
            return new ResponseEntity<>("No product found", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }


    @GetMapping("/active")
    public ResponseEntity<?> getActiveProducts() {
        List<Products> products = productService.findByActive(true);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedProducts() {
        List<Products> products = productService.findByFeatured(true);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/active-featured")
    public ResponseEntity<?> getActiveFeaturedProducts() {
        List<Products> products = productService.findByActiveAndFeatured(true, true);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/category/{productCategory}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String productCategory) {

        ProductCategory category;

        try {
            category = ProductCategory.valueOf(productCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("No such category exists", HttpStatus.BAD_REQUEST);
        }

        List<Products> products = productService.findByProductCategory(category);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/sub-category/{subCategory}")
    public ResponseEntity<?> getProductsBySubCategory(@PathVariable String subCategory) {

        ProductSubCategory category;

        try {
            category = ProductSubCategory.valueOf(subCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("No such sub category exists", HttpStatus.BAD_REQUEST);
        }

        List<Products> products = productService.findByProductSubCategory(category);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/category/{productCategory}/active")
    public ResponseEntity<?> getActiveByCategory(@PathVariable String productCategory) {

        ProductCategory category;

        try {
            category = ProductCategory.valueOf(productCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("No such category exists", HttpStatus.BAD_REQUEST);
        }

        List<Products> products = productService.findByProductCategoryAndActive(category, true);

        if (products.isEmpty()) {
            return new ResponseEntity<>("No products", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
