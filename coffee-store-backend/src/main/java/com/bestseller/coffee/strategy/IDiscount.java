package com.bestseller.coffee.strategy;

import com.bestseller.coffee.dto.response.order.CreatedOrderDto;

public interface IDiscount {
     void execute(CreatedOrderDto createdOrderDto);
     String type();
}
