package com.bestseller.coffee.unit.repository;

import com.bestseller.coffee.dto.response.topping.IMostUsedToppings;
import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.entity.Order;
import com.bestseller.coffee.entity.ToppingOrder;
import com.bestseller.coffee.repository.ToppingOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ToppingOrderRepositoryTests {

    @Autowired
    private ToppingOrderRepository toppingOrderRepository;

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
        ToppingOrder savedToppingOrder = toppingOrderRepository.save(toppingOrder);

        // then - verify the output
        assertThat(savedToppingOrder).isNotNull();
        assertThat(savedToppingOrder.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("DataJpaTest - find most used toppings test")
    public void givenNothing_whenFindMostUsedToppings_thenReturnMostUsedToppings(){

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

        ToppingOrder savedToppingOrder = toppingOrderRepository.save(toppingOrder);

        // when - action or the behaviour that we are going test
        List<IMostUsedToppings> mostUsedToppings = toppingOrderRepository.findMostUsedToppings();

        // then - verify the output
        assertThat(mostUsedToppings).isNotNull();
        assertThat(mostUsedToppings.size()).isGreaterThan(0);
    }

}
