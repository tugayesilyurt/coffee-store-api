package com.bestseller.coffee.dto.request.topping;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateToppingDto {

    @NotEmpty
    @Length(min = 2, max = 64)
    private String name;

    private BigDecimal amount;
}
