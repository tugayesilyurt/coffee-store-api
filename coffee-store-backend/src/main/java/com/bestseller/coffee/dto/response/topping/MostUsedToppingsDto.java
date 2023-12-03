package com.bestseller.coffee.dto.response.topping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MostUsedToppingsDto {

    private List<MostUsedToppings> mostUsedToppings;
}
