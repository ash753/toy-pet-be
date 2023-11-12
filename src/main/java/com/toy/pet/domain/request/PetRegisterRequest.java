package com.toy.pet.domain.request;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.Gender;
import com.toy.pet.domain.enums.PetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PetRegisterRequest {

    @NotNull
    @Schema(description = "반려동물 종류")
    private PetType petType;

    @NotNull
    @Schema(description = "성별")
    private Gender gender;

    @NotNull
    @Schema(description = "중성화 여부")
    private Boolean neutering;

    @NotBlank
    @Schema(description = "이름", example = "탄이")
    private String name;

    @Schema(description = "동물 등록 번호")
    private String animalRegistrationNumber;

    @NotNull
    @Schema(description = "생일")
    private LocalDate birthday;

    @NotBlank
    @Schema(description = "품종", example = "POMERANIAN")
    private String breed;

    public Pet toEntity(Member member) {
        return new Pet(petType, gender, neutering, name, animalRegistrationNumber, breed, birthday,member);
    }
}
