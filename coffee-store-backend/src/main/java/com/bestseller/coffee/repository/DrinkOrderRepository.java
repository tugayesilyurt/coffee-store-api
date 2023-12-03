package com.bestseller.coffee.repository;

import com.bestseller.coffee.entity.DrinkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkOrderRepository extends JpaRepository<DrinkOrder,Long> {
}
