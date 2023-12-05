package com.bestseller.coffee.entity;

import jakarta.persistence.*;
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

    @Column(name = "topping_id")
    private Long toppingId;

    @Column(name = "topping_amount")
    private BigDecimal toppingAmount;

    @Column(name = "topping_name")
    private String toppingName;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="drink_order_id",referencedColumnName="id", nullable=false)
    private DrinkOrder drinkOrder;

}
