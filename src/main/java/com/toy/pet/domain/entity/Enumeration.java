package com.toy.pet.domain.entity;

import com.toy.pet.domain.enums.EnumerationCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Table(name = "enumerations")
@Getter
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enumeration extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumerationCategory enumerationCategory;

    private String code;

    private String name;

    private boolean deleteYn;

    public Enumeration(EnumerationCategory enumerationCategory, String code, String name) {
        this.enumerationCategory = enumerationCategory;
        this.code = code;
        this.name = name;
    }
}
