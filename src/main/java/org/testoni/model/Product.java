package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testoni.dto.UserIntegrationDto;

import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public Product(UserIntegrationDto userIntegrationDto) {
        this.productId = userIntegrationDto.getProductId();
        this.value = userIntegrationDto.getValue();
    }

    @Size(max = 10)
    private Long productId;

    @Size(max = 12)
    private Double value;
}
