package com.bestseller.coffee.unit.controller;


import com.bestseller.coffee.config.SecurityConfig;
import com.bestseller.coffee.controller.OrderController;
import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.request.order.DrinkOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.service.Impl.OrderServiceImpl;
import com.bestseller.coffee.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("unit test - create order successfully")
    public void givenOrderObject_whenCreateOrder_thenReturnCreatedOrderObject() throws Exception{

        // given - precondition or setup
        CreateOrderDto createOrderDto = new CreateOrderDto();
        DrinkOrderDto drinkOrderDto = DrinkOrderDto.builder()
                .drinkAmount(new BigDecimal("20"))
                .drinkName("Black Coffee")
                .build();
        createOrderDto.setCartList(List.of(drinkOrderDto));

        CreatedOrderDto createdOrderDto = CreatedOrderDto.builder()
                .cartList(createOrderDto.getCartList())
                .totalAmount(new BigDecimal("20"))
                .discountedAmount(new BigDecimal("15"))
                .discount(new BigDecimal("5"))
                .discountType("Total Amount Percentage")
                .build();

        given(orderService.createOrder(createOrderDto)).willReturn(createdOrderDto);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated());

    }

}
