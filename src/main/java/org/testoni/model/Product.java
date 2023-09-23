package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Size(max = 10)
    private Long productId;

    @Size(max = 12)
    private Double value;
}
