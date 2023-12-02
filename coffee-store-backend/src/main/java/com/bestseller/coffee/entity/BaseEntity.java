package com.bestseller.coffee.entity;

import com.bestseller.coffee.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = null;

    @PrePersist
    private void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedDate = LocalDateTime.now();
    }

}