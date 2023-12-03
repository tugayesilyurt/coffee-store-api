package com.bestseller.coffee.dto.request.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ToppingOrderDto {

    private Long toppingId;

    private String toppingName;

    private BigDecimal toppingAmount;

}
