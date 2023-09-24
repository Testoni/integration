package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testoni.dto.UserIntegrationDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(UserIntegrationDto userIntegrationDto) {
        this.userId = userIntegrationDto.getUserId();
        this.name = userIntegrationDto.getName();
        this.orders = new ArrayList<>();
        this.orders.add(new Order(userIntegrationDto));
    }

    private Long userId;
    private String name;
    private List<Order> orders;

    public Order getOrderById(Long orderId) {
        return orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        Collections.sort(this.orders, Comparator.comparingLong(Order::getOrderId));
    }
}
