package com.toy.pet.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Column(updatable = false)
    protected Long createdBy;

    protected LocalDateTime updatedAt;

    protected Long updatedBy;
}
