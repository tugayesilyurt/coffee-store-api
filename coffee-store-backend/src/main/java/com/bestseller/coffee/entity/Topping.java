package com.bestseller.coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "toppings")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topping extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

}
