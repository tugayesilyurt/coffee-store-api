package com.bestseller.coffee.service;

import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.drink.UpdateDrinkDto;
import com.bestseller.coffee.dto.response.drink.CreatedDrinkDto;
import com.bestseller.coffee.dto.response.drink.DeletedDrinkDto;
import com.bestseller.coffee.dto.response.drink.UpdatedDrinkDto;

public interface DrinkService {

    CreatedDrinkDto createDrink(CreateDrinkDto createDrinkDto);

    UpdatedDrinkDto updateDrink(Long id, UpdateDrinkDto updateDrinkDto);

    DeletedDrinkDto deleteDrink(Long id);
}
