package com.bestseller.coffee.dto.request.drink;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateDrinkDto {

    @NotEmpty
    @Length(min = 2, max = 64)
    private String name;

    private BigDecimal amount;
}
