package com.canaristar.backend.controller.admin;

import com.canaristar.backend.entity.ProductData;
import com.canaristar.backend.service.productData.ProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product-data")
public class AdminProductDataController {

    @Autowired
    private ProductDataService productDataService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ProductData>> getAllAnalytics() {
        return ResponseEntity.ok(productDataService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product")
    public ResponseEntity<ProductData> getAnalyticsByProduct(@RequestParam String productId) {
        return ResponseEntity.ok(productDataService.getByProductId(productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-viewed")
    public ResponseEntity<List<ProductData>> getTopViewedProducts() {
        return ResponseEntity.ok(productDataService.getTopViewed());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-ordered")
    public ResponseEntity<List<ProductData>> getTopOrderedProducts() {
        return ResponseEntity.ok(productDataService.getTopOrdered());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-rated")
    public ResponseEntity<List<ProductData>> getTopRatedProducts() {
        return ResponseEntity.ok(productDataService.getTopRated());
    }
}
