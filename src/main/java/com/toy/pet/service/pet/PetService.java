package com.toy.pet.service.pet;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.enums.EnumerationCategory;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.request.PetRegisterRequest;
import com.toy.pet.domain.response.EnumerationResponseDto;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.PetRepository;
import com.toy.pet.service.enumeration.EnumerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    private final MemberPetService memberPetService;

    private final EnumerationService enumerationService;

    public Pet registerPetAndConnectMember(String sharingCode, PetRegisterRequest petRegisterRequest,
                                            Member member, Relationship relationship) {
        Pet pet = null;
        if (StringUtils.hasText(sharingCode)) { // 공유 코드가 있다면? 이미 있는 애완동물과 연결한다.
            pet = getPetWithSharingCode(sharingCode);
        } else {
            pet = createAndSavePet(petRegisterRequest, member);
        }

        memberPetService.saveMemberPet(member, pet, relationship);
        return pet;
    }

    private Pet createAndSavePet(PetRegisterRequest petRegisterRequest, Member member) {
        Pet pet;
        if (ObjectUtils.isEmpty(petRegisterRequest)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0081);
        }

        pet = petRegisterRequest.toEntity(member);
        checkPetBreed(pet);
        petRepository.save(pet);
        pet.generateSharingCode();
        return pet;
    }

    public Pet getPetWithSharingCode(String sharingCode) {
        if (!StringUtils.hasText(sharingCode)) {
            throw new IllegalArgumentException("sharingCode must not be empty");
        }

        Optional<Pet> petOptional = petRepository.findPetBySharingCode(sharingCode);

        return petOptional.orElseThrow(() ->{
            return new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0080);
        });
    }

    public void checkPetBreed(Pet pet) {
        EnumerationCategory enumerationCategory = null;
        switch (pet.getPetType()) {
            case CAT -> {
                enumerationCategory = EnumerationCategory.CAT_BREED;
            }
            case DOG -> {
                enumerationCategory = EnumerationCategory.DOG_BREED;
            }
        }


        Set<String> codeSet = enumerationService.findEnumerationResponseDtoList(enumerationCategory)
                .stream()
                .map(EnumerationResponseDto::getCode).collect(Collectors.toSet());

        if (!codeSet.contains(pet.getBreed())) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0083);
        }
    }
}
