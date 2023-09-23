package org.testoni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Size(max = 10)
    private Long orderId;

    @Size(max = 12)
    private Double total;

    @Size(max = 8)
    private String date;

    private List<Product> products;

}
