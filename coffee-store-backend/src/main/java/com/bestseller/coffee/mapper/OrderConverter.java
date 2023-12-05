package com.bestseller.coffee.mapper;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.ToppingOrder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderConverter {

    public static Order createOrderFromDto(CreatedOrderDto createdOrderDto){

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

        List<DrinkOrder> drinkOrders = new ArrayList<>();
        createdOrderDto.getCartList().stream().forEach(drinkData -> {

            DrinkOrder drinkOrder = DrinkOrder.builder()
                    .order(order)
                    .drinkId(drinkData.getDrinkId())
                    .drinkAmount(drinkData.getDrinkAmount())
                    .drinkName(drinkData.getDrinkName()).build();

            List<ToppingOrder> toppingOrders = new ArrayList<>();

            if(Objects.nonNull(drinkData.getToppingOrderList())) {
                drinkData.getToppingOrderList().stream().forEach(toppingData -> {

                    ToppingOrder toppingOrder = ToppingOrder.builder()
                            .drinkOrder(drinkOrder)
                            .toppingId(toppingData.getToppingId())
                            .toppingAmount(toppingData.getToppingAmount())
                            .toppingName(toppingData.getToppingName())
                            .build();

                    toppingOrders.add(toppingOrder);

                });
            }

            drinkOrder.setToppingOrders(toppingOrders);
            drinkOrders.add(drinkOrder);

        });
        order.setDrinkOrders(drinkOrders);
        return order;
    }
}
