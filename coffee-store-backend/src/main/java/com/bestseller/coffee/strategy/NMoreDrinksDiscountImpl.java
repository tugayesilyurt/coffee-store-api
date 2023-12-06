package com.bestseller.coffee.strategy;

import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

@Component
@Slf4j
public class NMoreDrinksDiscountImpl implements IDiscount{

    private static final int NOrMoreDrinks = 3;

    @Override
    public void execute(CreatedOrderDto createdOrderDto){

        if(createdOrderDto.getCartList().size() >= NOrMoreDrinks){
            log.info("n or more drinks discount execute!");
            //if toppinglist null --> set empty
            createdOrderDto.getCartList().stream().forEach(drink -> {
                if(Objects.isNull(drink.getToppingOrderList()))
                    drink.setToppingOrderList(Collections.emptyList());
            });

            // find the lowest amount
           BigDecimal minAmount = createdOrderDto.getCartList().stream().map(decimal -> decimal.getDrinkAmount().add(decimal.getToppingOrderList().stream()
                    .map(topping -> topping.getToppingAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)))
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            //check has already discount
            if(null != createdOrderDto.getDiscount()){
                if(minAmount.compareTo(createdOrderDto.getDiscount()) > 0){
                    createdOrderDto.setDiscount(minAmount);
                    createdOrderDto.setDiscountedAmount(createdOrderDto.getTotalAmount().subtract(minAmount));
                    createdOrderDto.setDiscountType(type());
                }
            }else{
                createdOrderDto.setDiscount(minAmount);
                createdOrderDto.setDiscountedAmount(createdOrderDto.getTotalAmount().subtract(minAmount));
                createdOrderDto.setDiscountType(type());
            }

        }

    }

    @Override
    public String type(){
        return "N or More Drinks Discount";
    }

}
