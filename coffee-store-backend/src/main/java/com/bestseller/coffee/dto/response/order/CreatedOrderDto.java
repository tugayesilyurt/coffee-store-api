package com.bestseller.coffee.dto.response.order;

import com.bestseller.coffee.dto.request.order.DrinkOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedOrderDto {

    private List<DrinkOrderDto> cartList;
    private BigDecimal totalAmount;
    private BigDecimal discountedAmount;
    private BigDecimal discount;
    private String discountType;
}
