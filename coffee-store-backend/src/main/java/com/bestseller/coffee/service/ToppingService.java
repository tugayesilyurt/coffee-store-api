package com.bestseller.coffee.service;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.*;
import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.exception.ToppingAlreadyExistException;
import com.bestseller.coffee.exception.ToppingNotFoundException;
import com.bestseller.coffee.mapper.DtoToEntityMapper;
import com.bestseller.coffee.repository.ToppingOrderRepository;
import com.bestseller.coffee.repository.ToppingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingRepository toppingRepository;
    private final ToppingOrderRepository toppingOrderRepository;

    public CreatedToppingDto createTopping(CreateToppingDto createToppingDto) {
        Topping topping = DtoToEntityMapper.createToppingFromDto(createToppingDto);

        Optional<Topping> existingTopping = toppingRepository.findByStatusAndName(Status.ACTIVE, topping.getName());
        if (existingTopping.isPresent()) {
            throw new ToppingAlreadyExistException(CoffeeConstants.toppingAlreadyExist);
        }

        toppingRepository.save(topping);
        return CreatedToppingDto.builder().message(CoffeeConstants.createdTopping)
                .build();
    }

    public UpdatedToppingDto updateTopping(Long id, UpdateToppingDto updateToppingDto) {
        Optional<Topping> savedTopping = toppingRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedTopping.isEmpty())
            throw new ToppingNotFoundException(CoffeeConstants.toppingNotFound);

        savedTopping.get().setName(updateToppingDto.getName());
        savedTopping.get().setAmount(updateToppingDto.getAmount());

        toppingRepository.save(savedTopping.get());

        return UpdatedToppingDto.builder().message(CoffeeConstants.updatedTopping).build();

    }

    public DeletedToppingDto deleteTopping(Long id) {
        Optional<Topping> savedTopping = toppingRepository.findByStatusAndId(Status.ACTIVE, id);
        if(savedTopping.isEmpty())
            throw new ToppingNotFoundException(CoffeeConstants.toppingNotFound);

        savedTopping.get().setStatus(Status.PASSIVE);

        toppingRepository.save(savedTopping.get());
        return DeletedToppingDto.builder().message(CoffeeConstants.deletedTopping).build();
    }

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
