package com.bestseller.coffee.integration.controller;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.bestseller.coffee.repository.ToppingRepository;
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
public class ToppingControllerIntegrationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        toppingRepository.deleteAll();
    }

    @Test
    @DisplayName("integration test - create topping successfully")
    public void givenToppingObject_whenCreateTopping_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        CreateToppingDto toppingDto = CreateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.createdTopping)));

    }

    @Test
    @DisplayName("integration test - update topping successfully")
    public void givenToppingId_whenUpdateTopping_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        UpdateToppingDto toppingDto = UpdateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("20"))
                .build();

        Topping willSaveTopping = Topping.builder().name("Milk").amount(new BigDecimal("30"))
                .build();
        toppingRepository.save(willSaveTopping);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/toppings/{id}", willSaveTopping.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.updatedTopping)));

    }

    @Test
    @DisplayName("integration test - delete topping successfully")
    public void givenToppingId_whenDeleteTopping_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        Topping willSaveTopping = Topping.builder().name("Milk").amount(new BigDecimal("5"))
                .build();
        toppingRepository.save(willSaveTopping);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/toppings/{id}", willSaveTopping.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.deletedTopping)));

    }

}
