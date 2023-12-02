package com.bestseller.coffee.mapper;

import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.entity.Drink;

public class DtoToEntityMapper {

    public static Drink createDrinkFromDto(CreateDrinkDto createDrinkDto){
        return Drink.builder()
                .name(createDrinkDto.getName())
                .amount(createDrinkDto.getAmount()).build();
    }

}
