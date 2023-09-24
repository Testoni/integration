package org.testoni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testoni.dto.UserIntegrationDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public Product(UserIntegrationDto userIntegrationDto) {
        this.orderId = userIntegrationDto.getOrderId();
        this.productId = userIntegrationDto.getProductId();
        this.value = userIntegrationDto.getValue();
    }

    @JsonIgnore
    private Long orderId;
    private Long productId;
    private Double value;
}
