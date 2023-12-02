package com.bestseller.coffee.integration.controller;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.drink.UpdateDrinkDto;
import com.bestseller.coffee.entity.Drink;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.bestseller.coffee.repository.DrinkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DrinkControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        drinkRepository.deleteAll();
    }

    @Test
    @DisplayName("integration test - create drink successfully")
    public void givenDrinkObject_whenCreateDrink_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        CreateDrinkDto drinkDto = CreateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinkDto)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.createdDrink)));

    }

    @Test
    @DisplayName("integration test - update drink successfully")
    public void givenDrinkId_whenUpdateDrink_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        UpdateDrinkDto drinkDto = UpdateDrinkDto.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("10"))
                .build();

        Drink willSaveDrink = Drink.builder().name("Black Coffee").amount(new BigDecimal("5"))
                .build();
        drinkRepository.save(willSaveDrink);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/drinks/{id}", willSaveDrink.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drinkDto)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.updatedDrink)));

    }

    @Test
    @DisplayName("integration test - delete drink successfully")
    public void givenDrinkId_whenDeleteDrink_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        Drink willSaveDrink = Drink.builder().name("Black Coffee").amount(new BigDecimal("5"))
                .build();
        drinkRepository.save(willSaveDrink);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/admin/drinks/{id}", willSaveDrink.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.deletedDrink)));

    }
}
