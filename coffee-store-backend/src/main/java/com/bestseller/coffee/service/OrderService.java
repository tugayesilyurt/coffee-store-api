package com.bestseller.coffee.service;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.ToppingOrder;
import com.bestseller.coffee.repository.DrinkOrderRepository;
import com.bestseller.coffee.repository.OrderRepository;
import com.bestseller.coffee.repository.ToppingOrderRepository;
import com.bestseller.coffee.strategy.DiscountExecute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DrinkOrderRepository drinkOrderRepository;
    private final ToppingOrderRepository toppingOrderRepository;
    private final DiscountExecute discountExecute;


    public CreatedOrderDto createOrder(CreateOrderDto createOrderDto){

        CreatedOrderDto createdOrderDto = new CreatedOrderDto();
        createdOrderDto.setCartList(createOrderDto.getCartList());

        // Discount Logic
        executeAllDiscount(createdOrderDto);

        //save order
        saveOrder(createdOrderDto);

        return createdOrderDto;
    }

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

    @Transactional
    public void saveOrder(CreatedOrderDto createdOrderDto){

        Order order = Order.builder()
                .drinksCount((int) createdOrderDto.getCartList().stream().count())
                .toppingsCount((int) createdOrderDto.getCartList().stream()
                        .filter(drink -> !CollectionUtils.isEmpty(drink.getToppingOrderList()))
                        .map(item -> item.getToppingOrderList() )
                        .flatMap(List::stream)
                        .count())
                .totalAmount(createdOrderDto.getTotalAmount())
                .discountedAmount(createdOrderDto.getDiscountedAmount())
                .discount(createdOrderDto.getDiscount()).build();

        orderRepository.save(order);

        createdOrderDto.getCartList().stream().forEach(drinkData -> {

            DrinkOrder drinkOrder = DrinkOrder.builder()
                    .drinkId(drinkData.getDrinkId())
                    .orderId(order.getId())
                    .drinkAmount(drinkData.getDrinkAmount())
                    .drinkName(drinkData.getDrinkName()).build();

            drinkOrderRepository.save(drinkOrder);

            if(Objects.nonNull(drinkData.getToppingOrderList())) {
                drinkData.getToppingOrderList().stream().forEach(toppingData -> {

                    ToppingOrder toppingOrder = ToppingOrder.builder()
                            .orderId(order.getId())
                            .toppingId(toppingData.getToppingId())
                            .toppingAmount(toppingData.getToppingAmount())
                            .toppingName(toppingData.getToppingName())
                            .drinkId(drinkOrder.getId())
                            .build();

                    toppingOrderRepository.save(toppingOrder);

                });
            }

        });
    }
}
