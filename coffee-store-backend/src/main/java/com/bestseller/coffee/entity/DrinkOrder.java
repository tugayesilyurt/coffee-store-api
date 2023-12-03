package com.bestseller.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "drinks_order")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkOrder extends BaseEntity{

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "drink_id")
    private Long drinkId;

    @Column(name = "drink_amount")
    private BigDecimal drinkAmount;

    @Column(name = "drink_name")
    private String drinkName;

}
