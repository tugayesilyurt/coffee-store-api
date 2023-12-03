package com.bestseller.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

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

}
