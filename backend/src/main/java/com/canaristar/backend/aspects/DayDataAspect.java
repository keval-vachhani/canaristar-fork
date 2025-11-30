package com.canaristar.backend.aspects;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.service.dayDataService.DayDataService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DayDataAspect {

    @Autowired
    private DayDataService dayDataService;

    @AfterReturning("execution(* com.canaristar.backend.controller.GeneralController.live(..))")
    public void trackVisitor() {
        dayDataService.addVisitor();
    }

    @AfterReturning(
            value = "execution(* com.canaristar.backend.service.orders.OrdersService.createOrder(..)) && args(order,..)",
            argNames = "order"
    )
    public void trackOrder(Orders order) {
        dayDataService.addOrder(order.getId(), order.getTotalPrice());
    }

    @AfterReturning("execution(* com.canaristar.backend.service.user.UserService.registerUser(..))")
    public void trackNewUser() {
        dayDataService.addNewUser();
    }

    @AfterReturning(
            value = "execution(* com.canaristar.backend.service.products.ProductService.findById(..)) && args(productId)",
            argNames = "productId"
    )
    public void trackProductView(String productId) {
        dayDataService.incrementProductView(productId);
    }
}

