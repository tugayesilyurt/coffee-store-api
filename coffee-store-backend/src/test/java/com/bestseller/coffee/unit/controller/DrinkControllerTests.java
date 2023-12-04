package com.bestseller.coffee.unit.controller;

import com.bestseller.coffee.config.SecurityConfig;
import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.controller.DrinkController;
import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.drink.UpdateDrinkDto;
import com.bestseller.coffee.dto.response.drink.CreatedDrinkDto;
import com.bestseller.coffee.dto.response.drink.DeletedDrinkDto;
import com.bestseller.coffee.dto.response.drink.UpdatedDrinkDto;
import com.bestseller.coffee.exception.DrinkAlreadyExistException;
import com.bestseller.coffee.exception.DrinkNotFoundException;
import com.bestseller.coffee.service.DrinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DrinkController.class)
@Import(SecurityConfig.class)
public class DrinkControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrinkService drinkService;

    @Autowired
    private ObjectMapper objectMapper;

    // positive scenario - create drink
    @Test
    @DisplayName("unit test - create drink successfully")
    @WithMockUser
    public void givenDrinkObject_whenCreateDrink_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        CreateDrinkDto drinktDto = CreateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        CreatedDrinkDto createdDrinkDto = CreatedDrinkDto.builder().message(CoffeeConstants.createdDrink).build();

        given(drinkService.createDrink(drinktDto)).willReturn(createdDrinkDto);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinktDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.createdDrink)))
                .andExpect(status().isCreated());

    }

    // negative scenario - create drink
    @Test
    @DisplayName("unit test - create drink fail - already exist!")
    @WithMockUser
    public void givenAlreadyExistDrink_whenCreateDrink_thenDrinkAlreadyExistException () throws Exception {
        // given - precondition or setup
        CreateDrinkDto drinktDto = CreateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        willThrow(new DrinkAlreadyExistException(CoffeeConstants.drinkAlreadyExist)).given(drinkService).createDrink(drinktDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinktDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest());

    }

    // positive scenario - update drink
    @Test
    @DisplayName("unit test - update drink successfully")
    @WithMockUser
    public void givenUpdatedDrink_whenUpdateDrink_thenReturnSuccessfullyMessage() throws Exception{
        // given - precondition or setup
        Long id = 1l;
        UpdateDrinkDto drinktDto = UpdateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        UpdatedDrinkDto updateDrinkDto = UpdatedDrinkDto.builder().message(CoffeeConstants.updatedDrink).build();

        given(drinkService.updateDrink(id,drinktDto)).willReturn(updateDrinkDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/drinks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinktDto)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.updatedDrink)));

    }

    // negative scenario - update drink
    @Test
    @DisplayName("unit test - update drink fail - drink not found!")
    @WithMockUser
    public void givenNoExistDrink_whenUpdateDrink_thenDrinkNotFoundException() throws Exception {
        // given - precondition or setup
        Long id = 1l;
        UpdateDrinkDto drinktDto = UpdateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        willThrow(new DrinkNotFoundException(CoffeeConstants.drinkNotFound)).given(drinkService).updateDrink(id,drinktDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/drinks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinktDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is(CoffeeConstants.drinkNotFound)));

    }

    // positive scenario - delete drink
    @Test
    @DisplayName("unit test - delete drink successfully")
    @WithMockUser
    public void givenDrinkId_whenDeleteDrink_thenReturnSuccessfullyMessage() throws Exception{
        // given - precondition or setup
        long id = 1L;
        DeletedDrinkDto deletedDrinkDto = DeletedDrinkDto.builder().message(CoffeeConstants.deletedDrink).build();
        given(drinkService.deleteDrink(id)).willReturn(deletedDrinkDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/drinks/{id}", id));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.deletedDrink)));

    }

    // negative scenario - delete drink
    @Test
    @DisplayName("unit test - delete drink fail - drink not found!")
    @WithMockUser
    public void givenNoExistDrink_whenDeleteDrink_thenRuntimeException () throws Exception {
        // given - precondition or setup
        long id = 1L;
        DeletedDrinkDto deletedDrinkDto = DeletedDrinkDto.builder().message(CoffeeConstants.deletedDrink).build();

        willThrow(new DrinkNotFoundException(CoffeeConstants.drinkNotFound)).given(drinkService).deleteDrink(id);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/drinks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is(CoffeeConstants.drinkNotFound)));

    }

}
