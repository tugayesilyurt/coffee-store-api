package com.bestseller.coffee.integration.repository;

import com.bestseller.coffee.entity.Drink;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.bestseller.coffee.repository.DrinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DrinkRepositoryIT extends AbstractContainerBaseTest {

    @Autowired
    private DrinkRepository drinkRepository;

    @BeforeEach
    public void setup(){
        drinkRepository.deleteAll();
    }

    @Test
    @DisplayName("mysql container test - save drink test")
    public void givenDrinkObject_whenSave_thenReturnSavedDrink(){

        //given - precondition or setup
        Drink drink = Drink.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();
        // when - action or the behaviour that we are going test
        Drink savedDrink = drinkRepository.save(drink);

        // then - verify the output
        assertThat(savedDrink).isNotNull();
        assertThat(savedDrink.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("mysql container test - findByStatusAndName drink test")
    public void givenDrinkId_whenFindByName_thenReturnDrinkObject(){

        //given - precondition or setup
        Drink drink = Drink.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        drinkRepository.save(drink);

        // when - action or the behaviour that we are going test
        Drink savedDrink = drinkRepository.findByStatusAndName(Status.ACTIVE,"Black Coffee").get();

        // then - verify the output
        assertThat(savedDrink).isNotNull();
        assertThat(savedDrink.getId()).isEqualTo(drink.getId());
    }

    @Test
    @DisplayName("mysql container test - findByStatusAndId drink test")
    public void givenDrinkId_whenFindById_thenReturnDrinkObject(){

        //given - precondition or setup
        Drink drink = Drink.builder()
                .name("Black Coffee")
                .amount(new BigDecimal("5"))
                .build();

        drinkRepository.save(drink);

        // when - action or the behaviour that we are going test
        Drink savedDrink = drinkRepository.findByStatusAndId(Status.ACTIVE,drink.getId()).get();

        // then - verify the output
        assertThat(savedDrink).isNotNull();
        assertThat(savedDrink.getId()).isEqualTo(drink.getId());
    }
}
