package com.bestseller.coffee.integration.repository;

import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.enums.Status;
import com.bestseller.coffee.integration.AbstractContainerBaseTest;
import com.bestseller.coffee.repository.ToppingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToppingRepositoryIntegrationTests extends AbstractContainerBaseTest {

    @Autowired
    private ToppingRepository toppingRepository;

    @Test
    @DisplayName("DataJpaTest - save topping test")
    public void givenToppingObject_whenSave_thenReturnSavedTopping(){

        //given - precondition or setup
        Topping topping = Topping.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        // when - action or the behaviour that we are going test
        Topping savedTopping = toppingRepository.save(topping);

        // then - verify the output
        assertThat(savedTopping).isNotNull();
        assertThat(savedTopping.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("DataJpaTest - findByStatusAndName topping test")
    public void givenDrinkId_whenFindByName_thenReturnDrinkObject(){

        //given - precondition or setup
        Topping topping = Topping.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        toppingRepository.save(topping);

        // when - action or the behaviour that we are going test
        Topping savedTopping = toppingRepository.findByStatusAndName(Status.ACTIVE,"Milk").get();

        // then - verify the output
        assertThat(savedTopping).isNotNull();
        assertThat(savedTopping.getId()).isEqualTo(topping.getId());
    }

    @Test
    @DisplayName("DataJpaTest - findByStatusAndId topping test")
    public void givenToppingId_whenFindById_thenReturnToppingObject(){

        //given - precondition or setup
        Topping topping = Topping.builder()
                .name("Milk")
                .amount(new BigDecimal("5"))
                .build();

        toppingRepository.save(topping);

        // when - action or the behaviour that we are going test
        Topping savedTopping = toppingRepository.findByStatusAndId(Status.ACTIVE,topping.getId()).get();

        // then - verify the output
        assertThat(savedTopping).isNotNull();
        assertThat(savedTopping.getId()).isEqualTo(topping.getId());
    }
}

