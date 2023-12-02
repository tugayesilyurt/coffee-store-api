package com.bestseller.coffee.repository;

import com.bestseller.coffee.entity.Drink;
import com.bestseller.coffee.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink,Long> {
    Optional<Drink> findByStatusAndName(Status status,String name);

    Optional<Drink> findByStatusAndId(Status status,Long id);

}
