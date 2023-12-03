package com.bestseller.coffee.mapper;

import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.entity.Drink;
import com.bestseller.coffee.entity.Topping;

public class DtoToEntityMapper {

    public static Drink createDrinkFromDto(CreateDrinkDto createDrinkDto){
        return Drink.builder()
                .name(createDrinkDto.getName())
                .amount(createDrinkDto.getAmount()).build();
    }

    public static Topping createToppingFromDto(CreateToppingDto createToppingDto){
        return Topping.builder()
                .name(createToppingDto.getName())
                .amount(createToppingDto.getAmount()).build();
    }

}
