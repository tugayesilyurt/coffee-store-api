package com.bestseller.coffee.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {

    @NotEmpty
    private List<DrinkOrderDto> cartList;
}
