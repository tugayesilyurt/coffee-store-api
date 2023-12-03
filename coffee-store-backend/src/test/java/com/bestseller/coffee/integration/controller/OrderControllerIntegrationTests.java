package com.bestseller.coffee.integration.controller;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.request.order.DrinkOrderDto;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("create order successfully")
    public void givenOrderObject_whenCreateOrder_thenReturnCreatedOrderObject() throws Exception{

        // given - precondition or setup
        CreateOrderDto createOrderDto = new CreateOrderDto();
        DrinkOrderDto drinkOrderDto = DrinkOrderDto.builder()
                .drinkAmount(new BigDecimal("20"))
                .drinkName("Black Coffee")
                .build();
        createOrderDto.setCartList(List.of(drinkOrderDto));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated());

    }

}
