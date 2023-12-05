package com.bestseller.coffee.unit.service;

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
import com.bestseller.coffee.mapper.DtoToEntityMapper;
import com.bestseller.coffee.repository.DrinkRepository;
import com.bestseller.coffee.service.DrinkService;
import com.bestseller.coffee.service.Impl.DrinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTests {

    @Mock
    private DrinkRepository drinkRepository;

    @InjectMocks
    private DrinkServiceImpl drinkService;

    private CreateDrinkDto createDrinkDto;
    private UpdateDrinkDto updateDrinkDto;

    @BeforeEach
    public void setup(){

        createDrinkDto = CreateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        updateDrinkDto = UpdateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("10"))
                .build();

    }

    @DisplayName("unit service test - create drink successfully")
    @Test
    public void givenDrinkDtoObject_whenCreateDrink_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Drink drink = DtoToEntityMapper.createDrinkFromDto(createDrinkDto);

        given(drinkRepository.findByStatusAndName(Status.ACTIVE,drink.getName()))
                .willReturn(Optional.empty());

        given(drinkRepository.save(any(Drink.class))).willReturn(drink);

        // when -  action or the behaviour that we are going test
        CreatedDrinkDto response = drinkService.createDrink(createDrinkDto);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.createdDrink);
        verify(drinkRepository,times(1)).findByStatusAndName(Status.ACTIVE,drink.getName());
    }

    @DisplayName("unit service test - create drink fail - drink already exist!")
    @Test
    public void givenExistingDrink_whenCreateDrink_thenThrowRuntimeException(){
        // given - precondition or setup
        Drink drink = DtoToEntityMapper.createDrinkFromDto(createDrinkDto);

        given(drinkRepository.findByStatusAndName(Status.ACTIVE,drink.getName()))
                .willReturn(Optional.of(drink));

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(DrinkAlreadyExistException.class, () -> {
            drinkService.createDrink(createDrinkDto);
        });

        // then
        verify(drinkRepository, never()).save(any(Drink.class));
    }

    @DisplayName("unit service test - update drink successfully")
    @Test
    public void givenDrinkDtoObject_whenUpdateDrink_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Drink savedDrink = Drink.builder().name("Black Coffee").amount(new BigDecimal("5"))
                .build();

        Long id = 1l;
        given(drinkRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.of(savedDrink));

        savedDrink.setAmount(new BigDecimal("10"));

        given(drinkRepository.save(any(Drink.class))).willReturn(savedDrink);

        // when -  action or the behaviour that we are going test
        UpdatedDrinkDto response = drinkService.updateDrink(id,updateDrinkDto);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.updatedDrink);
        verify(drinkRepository,times(1)).findByStatusAndId(Status.ACTIVE,id);
    }

    @DisplayName("unit service test - update drink fail - drink not found!")
    @Test
    public void givenNonExistDrink_whenUpdateDrink_thenThrowRuntimeException(){
        // given - precondition or setup

        Long id = 1l;
        given(drinkRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(DrinkNotFoundException.class, () -> {
            drinkService.updateDrink(id,updateDrinkDto);
        });

        // then
        verify(drinkRepository, never()).save(any(Drink.class));
    }

    @DisplayName("unit service test - delete drink successfully")
    @Test
    public void givenDrinkId_whenDeleteDrink_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Long id = 1l;
        Drink savedDrink = Drink.builder().name("Black Coffee").amount(new BigDecimal("5"))
                .build();

        given(drinkRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.of(savedDrink));

        savedDrink.setStatus(Status.PASSIVE);

        given(drinkRepository.save(any(Drink.class))).willReturn(savedDrink);

        // when -  action or the behaviour that we are going test
        DeletedDrinkDto response = drinkService.deleteDrink(id);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.deletedDrink);
        verify(drinkRepository,times(1)).save(any(Drink.class));

    }

    @DisplayName("unit service test - delete drink fail - drink not found!")
    @Test
    public void givenNonExistDrink_whenDeleteDrink_thenThrowRuntimeException(){
        // given - precondition or setup

        Long id = 1l;
        given(drinkRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(DrinkNotFoundException.class, () -> {
            drinkService.deleteDrink(id);
        });

        // then
        verify(drinkRepository, never()).save(any(Drink.class));
    }

}
