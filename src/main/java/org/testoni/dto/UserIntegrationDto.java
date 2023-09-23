package org.testoni.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIntegrationDto {
    private Long userId;
    private String name;
    private Long orderId;
    private String date;
    private Long productId;
    private Double value;
}
