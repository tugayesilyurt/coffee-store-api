package com.bestseller.coffee.service.Impl;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.*;
import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.exception.ToppingAlreadyExistException;
import com.bestseller.coffee.exception.ToppingNotFoundException;
import com.bestseller.coffee.mapper.ToppingConverter;
import com.bestseller.coffee.repository.ToppingOrderRepository;
import com.bestseller.coffee.repository.ToppingRepository;
import com.bestseller.coffee.service.ToppingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToppingServiceImpl implements ToppingService {

    private final ToppingRepository toppingRepository;
    private final ToppingOrderRepository toppingOrderRepository;

    @Override
    public CreatedToppingDto createTopping(CreateToppingDto createToppingDto) {
        Topping topping = ToppingConverter.createToppingFromDto(createToppingDto);

        Optional<Topping> existingTopping = toppingRepository.findByStatusAndName(Status.ACTIVE, topping.getName());
        if (existingTopping.isPresent()) {
            throw new ToppingAlreadyExistException(CoffeeConstants.toppingAlreadyExist);
        }

        toppingRepository.save(topping);
        log.info("topping created successfully!");
        return CreatedToppingDto.builder().message(CoffeeConstants.createdTopping)
                .build();
    }

    @Override
    public UpdatedToppingDto updateTopping(Long id, UpdateToppingDto updateToppingDto) {
        Optional<Topping> savedTopping = toppingRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedTopping.isEmpty())
            throw new ToppingNotFoundException(CoffeeConstants.toppingNotFound);

        savedTopping.get().setName(updateToppingDto.getName());
        savedTopping.get().setAmount(updateToppingDto.getAmount());

        toppingRepository.save(savedTopping.get());
        log.info("topping updated successfully!");
        return UpdatedToppingDto.builder().message(CoffeeConstants.updatedTopping).build();

    }

    @Override
    public DeletedToppingDto deleteTopping(Long id) {
        Optional<Topping> savedTopping = toppingRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedTopping.isEmpty())
            throw new ToppingNotFoundException(CoffeeConstants.toppingNotFound);

        savedTopping.get().setStatus(Status.PASSIVE);

        toppingRepository.save(savedTopping.get());
        log.info("topping deleted successfully!");
        return DeletedToppingDto.builder().message(CoffeeConstants.deletedTopping).build();
    }

    @Override
    public MostUsedToppingsDto findMostUsedToppings(){
        MostUsedToppingsDto mostUsedToppingsDto = new MostUsedToppingsDto();
        List<MostUsedToppings> mostUsedToppingsList = new ArrayList<>();

        List<IMostUsedToppings> mostUsedToppings = toppingOrderRepository.findMostUsedToppings();

        mostUsedToppings.stream().forEach(topping -> {
            MostUsedToppings mUsedTopping = new MostUsedToppings();
            mUsedTopping.setToppingId(topping.getToppingId());
            mUsedTopping.setToppingName(topping.getToppingName());
            mUsedTopping.setCount(topping.getCount());
            mostUsedToppingsList.add(mUsedTopping);
        });
        mostUsedToppingsDto.setMostUsedToppings(mostUsedToppingsList);

        return mostUsedToppingsDto;
    }

}
