package com.bestseller.coffee.repository;

import com.bestseller.coffee.dto.response.topping.IMostUsedToppings;
import com.bestseller.coffee.entity.ToppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToppingOrderRepository extends JpaRepository<ToppingOrder,Long> {

    @Query(value = "SELECT topping_id as toppingId, topping_name as toppingName, COUNT(*) AS count\n" +
            "FROM toppings_order\n" +
            "GROUP BY topping_id, topping_name\n" +
            "ORDER BY topping_id, topping_name DESC",nativeQuery = true)
    List<IMostUsedToppings> findMostUsedToppings();

}
