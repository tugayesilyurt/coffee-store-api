package com.bestseller.coffee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drinks_order")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkOrder extends BaseEntity{

    @Column(name = "drink_id")
    private Long drinkId;

    @Column(name = "drink_amount")
    private BigDecimal drinkAmount;

    @Column(name = "drink_name")
    private String drinkName;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="order_id",referencedColumnName="id",nullable=false)
    private Order order;

    @OneToMany(mappedBy = "drinkOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToppingOrder> toppingOrders;

}
