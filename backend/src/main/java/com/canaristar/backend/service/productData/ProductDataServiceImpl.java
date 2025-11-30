package com.canaristar.backend.service.productData;

import com.canaristar.backend.entity.ProductData;
import com.canaristar.backend.repository.ProductDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDataServiceImpl implements ProductDataService {

    @Autowired
    private ProductDataRepository productDataRepository;

    public ProductData save(ProductData productData) {
        return productDataRepository.save(productData);
    }

    public Optional<ProductData> findById(String id) {
        return productDataRepository.findById(id);
    }

    public Optional<ProductData> findByProductId(String productId) {
        return productDataRepository.findByProductId(productId);
    }

    public ProductData setRating(ProductData productData) {
        float avg = productData.getTotalRatingSum() / productData.getTotalRatingCount();
        productData.setRating(avg);

        return productDataRepository.save(productData);
    }

    public float getRating(ProductData productData) {
        return productData.getRating();
    }

    public ProductData addProductView(String productId) {
        ProductData pd = productDataRepository.findByProductId(productId)
                .orElseGet(() -> create(productId));
        pd.setProductViews(pd.getProductViews() + 1);

        return productDataRepository.save(pd);
    }

    public ProductData addOrderId(String productId, String orderId) {
        ProductData pd = productDataRepository.findByProductId(productId)
                .orElseGet(() -> create(productId));
        pd.getOrdersId().add(orderId);

        return productDataRepository.save(pd);
    }

    public List<ProductData> getAll() {
        return productDataRepository.findAll();
    }

    public ProductData getByProductId(String productId) {
        return productDataRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product analytics not found"));
    }

    public List<ProductData> getTopViewed() {
        return productDataRepository.findAll().stream()
                .sorted(Comparator.comparingInt(ProductData::getProductViews).reversed())
                .toList();
    }

    public List<ProductData> getTopOrdered() {
        return productDataRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getOrdersId().size() - a.getOrdersId().size())
                .toList();
    }

    public List<ProductData> getTopRated() {
        return productDataRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(ProductData::getRating).reversed())
                .toList();
    }

    private ProductData create(String productId) {
        ProductData pd = new ProductData();
        pd.setProductId(productId);

        return productDataRepository.save(pd);
    }
}
