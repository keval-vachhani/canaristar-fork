package com.canaristar.backend.controller;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.enums.OrdersType;
import com.canaristar.backend.service.orders.OrdersService;
import com.canaristar.backend.service.razorpay.RazorpayPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RazorpayPaymentService razorpayPaymentService;


    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Orders orders) throws Exception {

        orders.setOrdersType(OrdersType.STARTED);

        Orders savedOrder = ordersService.createOrder(orders);

        if (savedOrder == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to create order"));
        }

        String razorpayOrderId = razorpayPaymentService.initializePayment(savedOrder);
        savedOrder.setRazorpayOrderId(razorpayOrderId);
        savedOrder.setOrdersType(OrdersType.PROCESSING);

        Orders updatedOrder = ordersService.updateOrder(savedOrder.getId(), savedOrder);

        if(updatedOrder == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to update order"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Order created and Razorpay order initialized",
                "order", updatedOrder,
                "razorpayOrderId", razorpayOrderId
        ));
    }


    @PostMapping("/{id}/payment/verify")
    public ResponseEntity<?> verifyPayment(@PathVariable String id,
                                           @RequestBody Map<String, String> body) throws Exception {

        Orders orders = ordersService.findOrderById(id);

        if (orders == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Order not found"));
        }

        String paymentId = body.get("razorpayPaymentId");

        if (paymentId == null || paymentId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Payment ID missing"));
        }

        String signature = body.get("razorpaySignature");

        if (signature == null || signature.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Signature missing"));
        }

        boolean valid = razorpayPaymentService.verifyPayment(
                orders.getRazorpayOrderId(),
                paymentId,
                signature
        );

        if (valid) {

            orders.setRazorpayPaymentId(paymentId);
            orders.setRazorpaySignature(signature);
            orders.setOrdersType(OrdersType.COMPLETED);

            ordersService.updateOrder(id, orders);

            return ResponseEntity.ok(Map.of(
                    "message", "Payment verified successfully",
                    "order", orders
            ));

        } else {

            orders.setOrdersType(OrdersType.CANCELED);
            ordersService.updateOrder(id, orders);

            return ResponseEntity.status(400).body(Map.of(
                    "message", "Payment verification failed",
                    "order", orders
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id) {
        Orders orders = ordersService.findOrderById(id);

        if (orders == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orders);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getUserOrders(@PathVariable String userId) {
        List<Orders> orders = ordersService.findOrdersByUserId(userId);

        return ResponseEntity.ok(orders);
    }


    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.findAllOrders();

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/between")
    public ResponseEntity<?> getOrdersBetween(@RequestParam String start, @RequestParam String end) {
        LocalDateTime startTime;
        LocalDateTime endTime;

        try {
            startTime = LocalDateTime.parse(start);
            endTime = LocalDateTime.parse(end);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid datetime format"));
        }

        List<Orders> orders = ordersService.findOrdersBetween(startTime, endTime);

        return ResponseEntity.ok(orders);
    }


    @GetMapping("/type/{ordersType}")
    public ResponseEntity<?> getOrdersByType(@PathVariable OrdersType ordersType) {
        List<Orders> orders = ordersService.findOrdersByOrderType(ordersType);

        return ResponseEntity.ok(orders);
    }
}