package com.bestseller.coffee.unit.repository;

import com.bestseller.coffee.entity.DrinkOrder;
import com.bestseller.coffee.repository.DrinkOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DrinkOrderRepositoryTests {

    @Autowired
    private DrinkOrderRepository drinkOrderRepository;

    @Test
    @DisplayName("DataJpaTest - save order test")
    public void givenOrderObject_whenSave_thenReturnSavedOrder(){

        //given - precondition or setup
        DrinkOrder drinkOrder = DrinkOrder.builder()
                .drinkId(1l)
                .orderId(1l)
                .drinkAmount(new BigDecimal("5"))
                .drinkName("Black Coffee").build();

        // when - action or the behaviour that we are going test
        DrinkOrder savedDrinkOrder = drinkOrderRepository.save(drinkOrder);

        // then - verify the output
        assertThat(savedDrinkOrder).isNotNull();
        assertThat(savedDrinkOrder.getId()).isGreaterThan(0);
    }

}