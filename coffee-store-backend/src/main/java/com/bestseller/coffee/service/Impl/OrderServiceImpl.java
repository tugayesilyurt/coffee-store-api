package com.bestseller.coffee.service.Impl;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.mapper.OrderConverter;
import com.bestseller.coffee.repository.OrderRepository;
import com.bestseller.coffee.service.OrderService;
import com.bestseller.coffee.strategy.DiscountExecute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DiscountExecute discountExecute;

    @Override
    public CreatedOrderDto createOrder(CreateOrderDto createOrderDto){

        CreatedOrderDto createdOrderDto = new CreatedOrderDto();
        createdOrderDto.setCartList(createOrderDto.getCartList());

        // Discount Logic
        executeAllDiscount(createdOrderDto);

        //save order
        saveOrder(createdOrderDto);

        return createdOrderDto;
    }

    @Override
    public void executeAllDiscount(CreatedOrderDto createdOrderDto){
        BigDecimal drinksAmount = createdOrderDto.getCartList().stream().map(amount -> amount.getDrinkAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal toppingsAmount = createdOrderDto.getCartList().stream().filter(drink -> !CollectionUtils.isEmpty(drink.getToppingOrderList())).map(toppings -> toppings.getToppingOrderList() )
                .flatMap(List::stream)
                .map(topping -> topping.getToppingAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //calculate cart total amount ( drinks + toppings )
        createdOrderDto.setTotalAmount(drinksAmount.add(toppingsAmount));

        discountExecute.execute(createdOrderDto);
    }

    @Override
    public void saveOrder(CreatedOrderDto createdOrderDto) {

        Order order = OrderConverter.createOrderFromDto(createdOrderDto);
        orderRepository.save(order);
        log.info("order created successfully!");
    }

}
