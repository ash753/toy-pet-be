package com.toy.pet.domain.response;

import com.toy.pet.domain.entity.Enumeration;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.Gender;
import com.toy.pet.domain.enums.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PetDetailResponseDto {
    private String profileImageUrl;

    private String petTypeCode;

    private String petType;

    private String genderCode;

    private String gender;

    private boolean neutering;

    private String name;

    private String animalRegistrationNumber;

    private String breedCode;

    private String breed;

    private LocalDate birthday;

    private String sharingCode;

    public PetDetailResponseDto(String profileImageUrl, Pet pet, Enumeration breed) {

        PetType petType = pet.getPetType();
        Gender gender = pet.getGender();

        this.profileImageUrl = profileImageUrl;
        this.petTypeCode = petType.getCode();
        this.petType = petType.getName();
        this.genderCode = gender.getCode();
        this.gender = gender.getName();
        this.neutering = pet.isNeutering();
        this.name = pet.getName();
        this.animalRegistrationNumber = pet.getAnimalRegistrationNumber();
        this.breedCode = breed.getCode();
        this.breed = breed.getName();
        this.birthday = pet.getBirthday();
        this.sharingCode = pet.getSharingCode();
    }
}
