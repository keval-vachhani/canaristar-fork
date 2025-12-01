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
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductDataController {

    @Autowired
    private ProductDataService productDataService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductData>> getAllAnalytics() {
        return ResponseEntity.ok(productDataService.getAll());
    }

    @GetMapping("/product")
    public ResponseEntity<ProductData> getAnalyticsByProduct(@RequestParam String productId) {
        return ResponseEntity.ok(productDataService.getByProductId(productId));
    }
}
