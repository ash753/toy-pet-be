package com.toy.pet.domain.entity;

import com.toy.pet.domain.common.Constant;
import com.toy.pet.domain.enums.Gender;
import com.toy.pet.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "pets")
@Getter
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean neutering;

    private String name;

    private String animalRegistrationNumber;

    private String breed;

    private LocalDate birthday;

    private String sharingCode;

    private boolean deleteYn;

    public Pet(PetType petType, Gender gender, boolean neutering, String name, String animalRegistrationNumber,
               String breed, LocalDate birthday, Member member) {
        this.petType = petType;
        this.gender = gender;
        this.neutering = neutering;
        this.name = name;
        this.animalRegistrationNumber = animalRegistrationNumber;
        this.breed = breed;
        this.birthday = birthday;
        this.createdBy = member.getId();
        this.updatedBy = member.getId();
    }

    public void generateSharingCode(){
        if (ObjectUtils.isEmpty(this.id)) {
            throw new IllegalStateException("id must not be null");
        }

        this.sharingCode = Constant.PET + "-" + String.format("%07d", this.id) + "-" +
                UUID.randomUUID().toString().substring(0, 7);
    }
}
