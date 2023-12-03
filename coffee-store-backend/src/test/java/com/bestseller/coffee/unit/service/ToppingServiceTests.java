package com.bestseller.coffee.unit.service;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.CreatedToppingDto;
import com.bestseller.coffee.dto.response.topping.DeletedToppingDto;
import com.bestseller.coffee.dto.response.topping.UpdatedToppingDto;
import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.exception.ToppingAlreadyExistException;
import com.bestseller.coffee.exception.ToppingNotFoundException;
import com.bestseller.coffee.mapper.DtoToEntityMapper;
import com.bestseller.coffee.repository.ToppingRepository;
import com.bestseller.coffee.service.ToppingService;
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
public class ToppingServiceTests {

    @Mock
    private ToppingRepository toppingRepository;

    @InjectMocks
    private ToppingService toppingService;

    private CreateToppingDto createToppingDto;
    private UpdateToppingDto updateToppingDto;

    @BeforeEach
    public void setup(){

        createToppingDto = CreateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        updateToppingDto = UpdateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("10"))
                .build();

    }

    @DisplayName("unit service test - create topping successfully")
    @Test
    public void givenToppingDtoObject_whenCreateTopping_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Topping topping = DtoToEntityMapper.createToppingFromDto(createToppingDto);

        given(toppingRepository.findByStatusAndName(Status.ACTIVE,topping.getName()))
                .willReturn(Optional.empty());

        given(toppingRepository.save(any(Topping.class))).willReturn(topping);

        // when -  action or the behaviour that we are going test
        CreatedToppingDto response = toppingService.createTopping(createToppingDto);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.createdTopping);
        verify(toppingRepository,times(1)).findByStatusAndName(Status.ACTIVE,topping.getName());
    }

    @DisplayName("unit service test - create topping fail - topping already exist!")
    @Test
    public void givenExistingTopping_whenCreateTopping_thenThrowToppingAlreadyExistException(){
        // given - precondition or setup
        Topping topping = DtoToEntityMapper.createToppingFromDto(createToppingDto);

        given(toppingRepository.findByStatusAndName(Status.ACTIVE,topping.getName()))
                .willReturn(Optional.of(topping));

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ToppingAlreadyExistException.class, () -> {
            toppingService.createTopping(createToppingDto);
        });

        // then
        verify(toppingRepository, never()).save(any(Topping.class));
    }

    @DisplayName("unit service test - update topping successfully")
    @Test
    public void givenToppingDtoObject_whenUpdateTopping_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Topping savedTopping = DtoToEntityMapper.createToppingFromDto(createToppingDto);

        Long id = 1l;
        given(toppingRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.of(savedTopping));

        savedTopping.setAmount(new BigDecimal("10"));

        given(toppingRepository.save(any(Topping.class))).willReturn(savedTopping);

        // when -  action or the behaviour that we are going test
        UpdatedToppingDto response = toppingService.updateTopping(id,updateToppingDto);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.updatedTopping);
        verify(toppingRepository,times(1)).findByStatusAndId(Status.ACTIVE,id);
    }

    @DisplayName("unit service test - update topping fail - topping not found!")
    @Test
    public void givenNonExistTopping_whenUpdateTopping_thenThrowToppingNotFound(){
        // given - precondition or setup

        Long id = 1l;
        given(toppingRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ToppingNotFoundException.class, () -> {
            toppingService.updateTopping(id,updateToppingDto);
        });

        // then
        verify(toppingRepository, never()).save(any(Topping.class));
    }

    @DisplayName("unit service test - delete topping successfully")
    @Test
    public void givenToppingId_whenDeleteTopping_thenReturnSuccessfullyMessage(){
        // given - precondition or setup
        Long id = 1l;
        Topping savedTopping = DtoToEntityMapper.createToppingFromDto(createToppingDto);

        given(toppingRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.of(savedTopping));

        savedTopping.setStatus(Status.PASSIVE);

        given(toppingRepository.save(any(Topping.class))).willReturn(savedTopping);

        // when -  action or the behaviour that we are going test
        DeletedToppingDto response = toppingService.deleteTopping(id);

        //verify method
        assertThat(response.getMessage()).isEqualTo(CoffeeConstants.deletedTopping);
        verify(toppingRepository,times(1)).save(any(Topping.class));

    }

    @DisplayName("unit service test - delete topping fail - topping not found!")
    @Test
    public void givenNonExistTopping_whenDeleteTopping_thenThrowToppingNotFoundException(){
        // given - precondition or setup

        Long id = 1l;
        given(toppingRepository.findByStatusAndId(Status.ACTIVE,id))
                .willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ToppingNotFoundException.class, () -> {
            toppingService.deleteTopping(id);
        });

        // then
        verify(toppingRepository, never()).save(any(Topping.class));
    }
}
