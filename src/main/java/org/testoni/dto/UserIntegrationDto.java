package org.testoni.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIntegrationDto {

    @Size(max = 10)
    private Long userId;

    @Size(max = 45)
    private String name;

    @Size(max = 10)
    private Long orderId;

    @Size(max = 8)
    private String date;

    @Size(max = 10)
    private Long productId;

    @Size(max = 12)
    private Double value;
}
