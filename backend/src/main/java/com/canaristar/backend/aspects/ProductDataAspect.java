package com.canaristar.backend.aspects;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.service.productData.ProductDataService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductDataAspect {

    @Autowired
    private ProductDataService productDataService;

    @AfterReturning(
            value = "execution(* com.canaristar.backend.service.orders.OrdersService.createOrder(..)) && args(order,..)",
            argNames = "order"
    )
    public void trackOrder(Orders order) {

        String orderId = order.getId();

        order.getCartItems().forEach(item -> {
            String productId = item.getProductId();

            productDataService.addOrderId(productId, orderId);
        });
    }


    @AfterReturning(
            value = "execution(* com.canaristar.backend.service.products.ProductService.findById(..)) && args(productId)",
            argNames = "productId"
    )
    public void trackProductView(String productId) {
        productDataService.addProductView(productId);
    }
}
