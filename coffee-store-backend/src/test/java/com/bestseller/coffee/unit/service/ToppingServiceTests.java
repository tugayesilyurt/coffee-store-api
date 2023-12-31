package com.bestseller.coffee.unit.service;

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
import com.bestseller.coffee.service.Impl.ToppingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToppingServiceTests {

    @Mock
    private ToppingRepository toppingRepository;

    @Mock
    private ToppingOrderRepository toppingOrderRepository;

    @InjectMocks
    private ToppingServiceImpl toppingService;

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
        Topping topping = ToppingConverter.createToppingFromDto(createToppingDto);

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
        Topping topping = ToppingConverter.createToppingFromDto(createToppingDto);

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
        Topping savedTopping = ToppingConverter.createToppingFromDto(createToppingDto);

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
        Topping savedTopping = ToppingConverter.createToppingFromDto(createToppingDto);

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

    @DisplayName("unit service test - find most used toppings successfully")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){

        // given - precondition or setup
        List<IMostUsedToppings> mostUsedToppings = new ArrayList<>();

        Map<String, Object> backingMap = new HashMap<>();
        backingMap.put("toppingName", "Milk");
        backingMap.put("toppingId", 1l);
        backingMap.put("count", 1);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        IMostUsedToppings iMostUsedToppings = factory.createProjection(IMostUsedToppings.class,backingMap);
        System.out.println(iMostUsedToppings);
        mostUsedToppings.add(iMostUsedToppings);

        given(toppingOrderRepository.findMostUsedToppings()).willReturn(mostUsedToppings);

        // when -  action or the behaviour that we are going test
        MostUsedToppingsDto lMostUsedToppings = toppingService.findMostUsedToppings();

        // then - verify the output
        assertThat(lMostUsedToppings).isNotNull();
        assertThat(lMostUsedToppings.getMostUsedToppings()).hasSizeGreaterThan(0);
        assertThat(lMostUsedToppings.getMostUsedToppings().stream().findFirst().get().getToppingName()).isEqualTo("Milk");
    }
}
