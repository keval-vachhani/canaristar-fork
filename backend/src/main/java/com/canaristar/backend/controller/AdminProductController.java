package com.canaristar.backend.controller;

import com.canaristar.backend.entity.Products;
import com.canaristar.backend.service.cloudinary.CloudinaryService;
import com.canaristar.backend.service.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Products product) {

        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is null");
        }

        Products saved = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Products product) {

        Optional<Products> existing = productService.findById(product.getId());

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Products old = existing.get();

        product.setId(old.getId());
        product.setImageUrls(old.getImageUrls());

        productService.save(product);

        return ResponseEntity.ok(product);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {

        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product id must not be null");
        }

        if (productService.findById(productId).isPresent()) {
            productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{productId}/upload-image")
    public ResponseEntity<?> uploadImage(
            @PathVariable String productId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String uploadedUrl = cloudinaryService.uploadProductImage(file);

            Optional<Products> optProduct = productService.findById(productId);

            if (optProduct.isEmpty()) {
                return ResponseEntity.badRequest().body("Product not found");
            }

            Products product = optProduct.get();

            product.getImageUrls().add(uploadedUrl);
            productService.save(product);

            return ResponseEntity.ok(product);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}/delete-image")
    public ResponseEntity<?> deleteProductImage(
            @PathVariable String productId,
            @RequestBody String imageUrl
    ) {
        try {
            imageUrl = imageUrl.replace("\"", "");

            if (imageUrl.isEmpty()) {
                return ResponseEntity.badRequest().body("Image URL is required");
            }

            Optional<Products> optProduct = productService.findById(productId);

            if (optProduct.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            Products product = optProduct.get();

            if (!product.getImageUrls().contains(imageUrl)) {
                return ResponseEntity.badRequest().body("Image not found in product list");
            }

            String publicId = cloudinaryService.extractPublicId(imageUrl);
            String deleteResult = cloudinaryService.deleteImage(publicId);

            if (!deleteResult.equals("true")) {
                return ResponseEntity.internalServerError().body(deleteResult);
            }

            product.getImageUrls().remove(imageUrl);
            productService.save(product);

            return ResponseEntity.ok(product);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Delete failed: " + e.getMessage());
        }
    }
}
