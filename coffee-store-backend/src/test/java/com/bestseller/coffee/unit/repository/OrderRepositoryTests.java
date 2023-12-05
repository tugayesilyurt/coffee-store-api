package com.bestseller.coffee.unit.repository;

import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.ToppingOrder;
import com.bestseller.coffee.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("DataJpaTest - save order test")
    public void givenOrderObject_whenSave_thenReturnSavedOrder(){

        //given - precondition or setup
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

        // when - action or the behaviour that we are going test
        Order savedOrder = orderRepository.save(order);

        // then - verify the output
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }

}
