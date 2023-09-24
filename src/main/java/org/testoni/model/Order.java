package org.testoni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Order {

    public Order(UserIntegrationDto userIntegrationDto) {
        this.userId = userIntegrationDto.getUserId();
        this.orderId = userIntegrationDto.getOrderId();
        this.total = userIntegrationDto.getValue();
        this.date = userIntegrationDto.getDate();
        this.products = new ArrayList<>();
        this.products.add(new Product(userIntegrationDto));
    }

    @JsonIgnore
    private Long userId;
    private Long orderId;
    private Double total;
    private String date;
    private List<Product> products;

    public void addProduct(Product product) {
        this.products.add(product);
        Collections.sort(this.products, Comparator.comparingLong(Product::getProductId));
    }
}
