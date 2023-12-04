package com.bestseller.coffee.unit.controller;

import com.bestseller.coffee.config.SecurityConfig;
import com.bestseller.coffee.constant.CoffeeConstants;
import com.bestseller.coffee.controller.ToppingController;
import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.*;
import com.bestseller.coffee.exception.ToppingAlreadyExistException;
import com.bestseller.coffee.exception.ToppingNotFoundException;
import com.bestseller.coffee.service.ToppingService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToppingController.class)
@Import(SecurityConfig.class)
public class ToppingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToppingService toppingService;

    @Autowired
    private ObjectMapper objectMapper;

    // positive scenario - create topping
    @Test
    @DisplayName("unit test - create topping successfully")
    @WithMockUser
    public void givenToppingObject_whenCreateToppings_thenReturnSuccessfullyMessage() throws Exception{

        // given - precondition or setup
        CreateToppingDto toppingDto = CreateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        CreatedToppingDto createdToppingDto = CreatedToppingDto.builder()
                .message(CoffeeConstants.createdTopping).build();

        given(toppingService.createTopping(toppingDto)).willReturn(createdToppingDto);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.createdTopping)));


    }

    // negative scenario - create topping
    @Test
    @DisplayName("unit test - create topping fail - already exist!")
    @WithMockUser
    public void givenAlreadyExistTopping_whenCreateTopping_thenToppingAlreadyExistException () throws Exception {
        // given - precondition or setup
        CreateToppingDto toppingDto = CreateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        willThrow(new ToppingAlreadyExistException(CoffeeConstants.toppingAlreadyExist)).given(toppingService)
                .createTopping(toppingDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/v1/toppings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest());

    }


    // positive scenario - update topping
    @Test
    @DisplayName("unit test - update topping successfully")
    @WithMockUser
    public void givenUpdatedTopping_whenUpdateTopping_thenReturnSuccessfullyMessage() throws Exception{
        // given - precondition or setup
        Long id = 1l;
        UpdateToppingDto toppingDto = UpdateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        UpdatedToppingDto updatedToppingDto = UpdatedToppingDto.builder().message(CoffeeConstants.updatedTopping).build();

        given(toppingService.updateTopping(id,toppingDto)).willReturn(updatedToppingDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/toppings/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.updatedTopping)));

    }

    // negative scenario - update topping
    @Test
    @DisplayName("unit test - update topping fail - topping not found!")
    @WithMockUser
    public void givenNoExistTopping_whenUpdateTopping_thenToppingNotFoundException () throws Exception {
        // given - precondition or setup
        Long id = 1l;
        UpdateToppingDto toppingDto = UpdateToppingDto.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        willThrow(new ToppingNotFoundException(CoffeeConstants.toppingNotFound)).given(toppingService).updateTopping(id,toppingDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/v1/toppings/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toppingDto)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest());

    }

    // positive scenario - delete topping
    @Test
    @DisplayName("unit test - delete topping successfully")
    @WithMockUser
    public void givenToppingId_whenDeleteTopping_thenReturnSuccessfullyDeleted() throws Exception{
        // given - precondition or setup
        long id = 1L;
        DeletedToppingDto deletedToppingDto = DeletedToppingDto.builder()
                .message(CoffeeConstants.deletedTopping).build();

        given(toppingService.deleteTopping(id)).willReturn(deletedToppingDto);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/toppings/{id}", id));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(CoffeeConstants.deletedTopping)))
                .andDo(print());
    }

    // negative scenario - delete topping
    @Test
    @DisplayName("unit test - delete topping fail - topping not found!")
    @WithMockUser
    public void givenNoExistTopping_whenDeleteTopping_thenToppingNotFoundException () throws Exception {
        // given - precondition or setup
        long id = 1L;
        DeletedToppingDto deletedToppingDto = DeletedToppingDto.builder().message(CoffeeConstants.deletedTopping).build();

        willThrow(new ToppingNotFoundException(CoffeeConstants.toppingNotFound)).given(toppingService).deleteTopping(id);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/v1/toppings/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is(CoffeeConstants.toppingNotFound)));

    }

    @Test
    @DisplayName("unit test - get most used toppings successfully")
    @WithMockUser
    public void givenNothing_whenGetMostUsedToppings_thenReturnMostUsedToppingsList() throws Exception {

        // given - precondition or setup
        MostUsedToppingsDto mostUsedToppingsDto = getMostUsedToppingsDto();

        given(toppingService.findMostUsedToppings()).willReturn(mostUsedToppingsDto);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/v1/toppings/most-used")
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mostUsedToppings", hasSize(1)));

    }

    private static MostUsedToppingsDto getMostUsedToppingsDto() {
        MostUsedToppingsDto mostUsedToppingsDto = new MostUsedToppingsDto();
        List<MostUsedToppings> mostUsedToppingsList = new ArrayList<>();
        MostUsedToppings mostUsedToppings = new MostUsedToppings();
        mostUsedToppings.setToppingId(1l);
        mostUsedToppings.setToppingName("Milk");
        mostUsedToppings.setCount(5);
        mostUsedToppingsList.add(mostUsedToppings);
        mostUsedToppingsDto.setMostUsedToppings(mostUsedToppingsList);
        return mostUsedToppingsDto;
    }
}
