package com.bestseller.coffee.service;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;

public interface OrderService {

    CreatedOrderDto createOrder(CreateOrderDto createOrderDto);

    void executeAllDiscount(CreatedOrderDto createdOrderDto);

    void saveOrder(CreatedOrderDto createdOrderDto);
}
