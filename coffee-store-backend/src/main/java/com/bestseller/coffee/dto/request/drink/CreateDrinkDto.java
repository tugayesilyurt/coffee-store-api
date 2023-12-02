package com.bestseller.coffee.dto.request.drink;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateDrinkDto {

    @NotEmpty
    private String name;

    private BigDecimal amount;

}
