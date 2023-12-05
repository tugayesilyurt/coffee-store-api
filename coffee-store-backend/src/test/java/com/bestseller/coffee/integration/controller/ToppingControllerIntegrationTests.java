package com.bestseller.coffee.integration.controller;

import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.entity.ToppingOrder;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.bestseller.coffee.repository.ToppingOrderRepository;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
    private ToppingOrderRepository toppingOrderRepository;

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
                .with(httpBasic("admin","coffee"))
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
                .with(httpBasic("admin","coffee"))
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
                .with(httpBasic("admin","coffee"))
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(CoffeeConstants.deletedTopping)));

    }

    @Test
    @DisplayName("integration test - find most used toppings successfully")
    public void givenNothing_whenGetMostUsedToppings_thenReturnMostUsedToppingsDto() throws Exception{

        // given - precondition or setup
        Order order = Order.builder()
                .drinksCount(1)
                .toppingsCount(0)
                .totalAmount(new BigDecimal("20"))
                .discountedAmount(new BigDecimal("15"))
                .discount(new BigDecimal("5")).build();

        DrinkOrder drinkOrder = DrinkOrder.builder()
                .drinkId(1l)
                .drinkAmount(new BigDecimal("5"))
                .drinkName("Black Coffee")
                .order(order).build();

        ToppingOrder toppingOrder = ToppingOrder.builder()
                .toppingId(1l)
                .toppingAmount(new BigDecimal("2"))
                .toppingName("Milk")
                .drinkOrder(drinkOrder)
                .build();

        toppingOrderRepository.save(toppingOrder);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/v1/toppings/most-used")
                .with(httpBasic("admin","coffee"))
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mostUsedToppings", hasSize(1)));;

    }

}
