package com.canaristar.backend.entity.orders;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "user-orders")
@Data
public class UserOrders {

    @Id
    private String userId;
    private List<Orders> orders = new LinkedList<>();
}
