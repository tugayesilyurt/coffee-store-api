package com.bestseller.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "toppings_order")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToppingOrder extends BaseEntity{

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "topping_id")
    private Long toppingId;

    @Column(name = "drink_id")
    private Long drinkId;

    @Column(name = "topping_amount")
    private BigDecimal toppingAmount;

    @Column(name = "topping_name")
    private String toppingName;

}
