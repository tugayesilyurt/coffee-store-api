package com.bestseller.coffee.service.Impl;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.drink.UpdateDrinkDto;
import com.bestseller.coffee.dto.response.drink.CreatedDrinkDto;
import com.bestseller.coffee.dto.response.drink.DeletedDrinkDto;
import com.bestseller.coffee.dto.response.drink.UpdatedDrinkDto;
import com.bestseller.coffee.entity.Drink;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.exception.DrinkAlreadyExistException;
import com.bestseller.coffee.exception.DrinkNotFoundException;
import com.bestseller.coffee.mapper.DrinkConverter;
import com.bestseller.coffee.repository.DrinkRepository;
import com.bestseller.coffee.service.DrinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;

    @Override
    public CreatedDrinkDto createDrink(CreateDrinkDto createDrinkDto) {
        Drink drink = DrinkConverter.createDrinkFromDto(createDrinkDto);

        Optional<Drink> existingDrink = drinkRepository.findByStatusAndName(Status.ACTIVE, drink.getName());
        if (existingDrink.isPresent()) {
            throw new DrinkAlreadyExistException(CoffeeConstants.drinkAlreadyExist);
        }

        drinkRepository.save(drink);
        return CreatedDrinkDto.builder().message(CoffeeConstants.createdDrink).build();
    }

    @Override
    public UpdatedDrinkDto updateDrink(Long id, UpdateDrinkDto updateDrinkDto) {
        Optional<Drink> savedDrink = drinkRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedDrink.isEmpty())
            throw new DrinkNotFoundException(CoffeeConstants.drinkNotFound);

        savedDrink.get().setName(updateDrinkDto.getName());
        savedDrink.get().setAmount(updateDrinkDto.getAmount());

        drinkRepository.save(savedDrink.get());

        return UpdatedDrinkDto.builder().message(CoffeeConstants.updatedDrink).build();

    }

    @Override
    public DeletedDrinkDto deleteDrink(Long id) {
        Optional<Drink> savedDrink = drinkRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedDrink.isEmpty())
            throw new DrinkNotFoundException(CoffeeConstants.drinkNotFound);

        savedDrink.get().setStatus(Status.PASSIVE);

        drinkRepository.save(savedDrink.get());

        return DeletedDrinkDto.builder().message(CoffeeConstants.deletedDrink).build();
    }

}
