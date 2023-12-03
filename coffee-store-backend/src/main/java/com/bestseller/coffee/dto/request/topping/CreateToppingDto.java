package com.bestseller.coffee.dto.request.topping;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateToppingDto {

    @NotEmpty
    private String name;

    private BigDecimal amount;

}
