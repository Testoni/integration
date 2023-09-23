package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testoni.dto.UserIntegrationDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public Product(UserIntegrationDto userIntegrationDto) {
        this.productId = userIntegrationDto.getProductId();
        this.value = userIntegrationDto.getValue();
    }

    private Long productId;
    private Double value;
}
