package com.bestseller.coffee.repository;

import com.bestseller.coffee.entity.Topping;
import com.bestseller.coffee.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ToppingRepository extends JpaRepository<Topping,Long> {
    Optional<Topping> findByStatusAndName(Status status, String name);
    Optional<Topping> findByStatusAndId(Status status, Long id);

}
