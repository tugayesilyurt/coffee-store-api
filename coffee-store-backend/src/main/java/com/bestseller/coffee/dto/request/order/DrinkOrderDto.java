package com.bestseller.coffee.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DrinkOrderDto {

    @NotEmpty
    private Long drinkId;

    @NotEmpty
    private String drinkName;

    @NotEmpty
    private BigDecimal drinkAmount;

    private List<ToppingOrderDto> toppingOrderList;
}
