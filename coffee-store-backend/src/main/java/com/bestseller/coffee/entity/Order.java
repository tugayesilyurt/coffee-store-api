package com.bestseller.coffee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity{

    @Column(name = "drinks_count")
    private Integer drinksCount;

    @Column(name = "toppings_count")
    private Integer toppingsCount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "discounted_amount")
    private BigDecimal discountedAmount;

    @Column(name = "discount")
    private BigDecimal discount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DrinkOrder> drinkOrders;

}
