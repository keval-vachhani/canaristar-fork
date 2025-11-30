package com.canaristar.backend.service.userOrders;

import com.canaristar.backend.entity.orders.Orders;
import com.canaristar.backend.entity.orders.UserOrders;
import com.canaristar.backend.repository.UserOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrdersServiceImpl implements UserOrdersService {

    @Autowired
    private UserOrdersRepository userOrdersRepository;

    @Override
    public UserOrders save(UserOrders userOrders) {
        return userOrdersRepository.save(userOrders);
    }

    @Override
    public UserOrders findByUserId(String userId) {
        return userOrdersRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public Orders findOrderById(String userId, String orderId) {
        UserOrders orders = findByUserId(userId);

        if (orders == null) {
            return null;
        }

        return orders.getOrders()
                .stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserOrders addOrderToUser(String userId, Orders order) {
        UserOrders userOrders = findByUserId(userId);

        if (userOrders == null) {
            userOrders = new UserOrders();
            userOrders.setUserId(userId);
        }

        userOrders.getOrders().add(order);

        return userOrdersRepository.save(userOrders);
    }

    @Override
    public void updateUserOrder(String userId, Orders updatedOrder) {

        UserOrders userOrders = userOrdersRepository.findByUserId(userId).orElse(null);

        if (userOrders == null) return;

        userOrders.setOrders(
                userOrders.getOrders()
                        .stream()
                        .map(o -> o.getId().equals(updatedOrder.getId()) ? updatedOrder : o)
                        .toList()
        );

        userOrdersRepository.save(userOrders);
    }
}
