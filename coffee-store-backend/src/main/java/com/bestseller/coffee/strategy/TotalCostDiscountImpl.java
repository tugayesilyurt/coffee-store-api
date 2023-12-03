package com.bestseller.coffee.strategy;

import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TotalCostDiscountImpl implements IDiscount{

    private static int percentage = 25;
    private static BigDecimal minCost = new BigDecimal("12");

    @Override
    public void execute(CreatedOrderDto createdOrderDto){

        if(createdOrderDto.getTotalAmount().compareTo(minCost) > 0) {
            BigDecimal percentageAmount = createdOrderDto.getTotalAmount().multiply(new BigDecimal(percentage / 100.0));

            //check has already discounted
            if (null != createdOrderDto.getDiscount()) {
                if (percentageAmount.compareTo(createdOrderDto.getDiscount()) > 0) {
                    createdOrderDto.setDiscount(percentageAmount);
                    createdOrderDto.setDiscountedAmount(createdOrderDto.getTotalAmount().subtract(percentageAmount));
                    createdOrderDto.setDiscountType(type());
                }
            } else {
                createdOrderDto.setDiscount(percentageAmount);
                createdOrderDto.setDiscountedAmount(createdOrderDto.getTotalAmount().subtract(percentageAmount));
                createdOrderDto.setDiscountType(type());
            }
        }
    }

    @Override
    public String type(){
        return "Total Amount Percentage";
    }

}
