package com.bestseller.coffee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "drinks")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drink extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

}
