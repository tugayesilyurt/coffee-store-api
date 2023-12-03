package com.bestseller.coffee.dto.response.topping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MostUsedToppings {

    private Long toppingId;
    private String toppingName;
    private Integer count;

}
