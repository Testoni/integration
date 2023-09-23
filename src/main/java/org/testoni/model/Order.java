package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testoni.dto.UserIntegrationDto;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    public Order(UserIntegrationDto userIntegrationDto) {
        this.orderId = userIntegrationDto.getOrderId();
        this.total = userIntegrationDto.getValue();
        this.date = userIntegrationDto.getDate();
        this.products = new ArrayList<>();
        this.products.add(new Product(userIntegrationDto));
    }

    @Size(max = 10)
    private Long orderId;

    @Size(max = 12)
    private Double total;

    @Size(max = 8)
    private String date;

    private List<Product> products;

}
