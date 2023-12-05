package com.bestseller.coffee.service;

import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.CreatedToppingDto;
import com.bestseller.coffee.dto.response.topping.DeletedToppingDto;
import com.bestseller.coffee.dto.response.topping.MostUsedToppingsDto;
import com.bestseller.coffee.dto.response.topping.UpdatedToppingDto;

public interface ToppingService {

    CreatedToppingDto createTopping(CreateToppingDto createToppingDto);

    UpdatedToppingDto updateTopping(Long id, UpdateToppingDto updateToppingDto);

    DeletedToppingDto deleteTopping(Long id);

    MostUsedToppingsDto findMostUsedToppings();
}
