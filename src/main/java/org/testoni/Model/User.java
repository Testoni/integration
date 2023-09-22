package org.testoni.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Size(max = 10)
    private Long userId;

    @Size(max = 45)
    private String name;

    private List<Order> orders;
}
