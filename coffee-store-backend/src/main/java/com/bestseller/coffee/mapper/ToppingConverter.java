package com.bestseller.coffee.mapper;

import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.entity.Topping;

public class ToppingConverter {

    public static Topping createToppingFromDto(CreateToppingDto createToppingDto){
        return Topping.builder()
                .name(createToppingDto.getName())
                .amount(createToppingDto.getAmount()).build();
    }
}
