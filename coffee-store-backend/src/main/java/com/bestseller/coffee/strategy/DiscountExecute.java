package com.bestseller.coffee.strategy;

import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountExecute {

    private final List<IDiscount> listOfDiscount = new ArrayList<>();

    // Add all Impl of discount
    public DiscountExecute(ListableBeanFactory listOfBean) {
        listOfBean.getBeansOfType(IDiscount.class)
                .values()
                .forEach(listOfDiscount::add);
    }

    //execute all
    public void execute(CreatedOrderDto createdOrderDto){
        listOfDiscount.stream().forEach(discount -> {
            discount.execute(createdOrderDto);
        });
    }
}
