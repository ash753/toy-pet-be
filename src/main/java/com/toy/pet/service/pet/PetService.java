package com.toy.pet.service.pet;

import com.toy.pet.domain.entity.*;
import com.toy.pet.domain.enums.EnumerationCategory;
import com.toy.pet.domain.enums.PetType;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.request.PetRegisterRequest;
import com.toy.pet.domain.response.EnumerationResponseDto;
import com.toy.pet.domain.response.PetDetailResponseDto;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.PetRepository;
import com.toy.pet.service.enumeration.EnumerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    private final MemberPetService memberPetService;

    private final EnumerationService enumerationService;

    private final PetProfileImageService petProfileImageService;

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
        EnumerationCategory enumerationCategory = getEnumerationCategoryWithPetType(pet.getPetType());


        Set<String> codeSet = enumerationService.findEnumerationResponseDtoList(enumerationCategory)
                .stream()
                .map(EnumerationResponseDto::getCode).collect(Collectors.toSet());

        if (!codeSet.contains(pet.getBreed())) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0083);
        }
    }

    private static EnumerationCategory getEnumerationCategoryWithPetType(PetType petType) {
        EnumerationCategory enumerationCategory = null;
        switch (petType) {
            case CAT -> {
                enumerationCategory = EnumerationCategory.CAT_BREED;
            }
            case DOG -> {
                enumerationCategory = EnumerationCategory.DOG_BREED;
            }
        }
        return enumerationCategory;
    }

    public Pet findPetByMember(Member member) {
        List<MemberPet> memberPetList = memberPetService.findMemberPet(member);
        if (ObjectUtils.isEmpty(memberPetList) || memberPetList.size() > 1) {
            throw new IllegalStateException("멤버는 하나의 반려동물 만을 가져야만 합니다.");
        }

        return memberPetList.get(0).getPet();
    }

    public Relationship findRelationShip(Member member, Pet pet) {
        List<MemberPet> memberPetList = memberPetService.findRelationShip(member, pet);
        if (ObjectUtils.isEmpty(memberPetList) || memberPetList.size() > 1) {
            throw new IllegalStateException("멤버와 반려동물의 관계는 없거나 여러개가 있어서는 안됩니다");
        }
        return memberPetList.get(0).getRelationship();
    }

    public PetDetailResponseDto findPetDetailResponseDto(Member member) {
        Pet pet = findPetByMember(member);

        List<PetProfileImage> petProfileImageList = petProfileImageService.findPetProfileImageByPet(pet);
        if (petProfileImageList.size() > 1) {
            throw new IllegalStateException("반려동물은 하나 이상의 프로필을 가질 수 없습니다.");
        }

        List<Enumeration> enumerationList = enumerationService
                .findEnumerationList(getEnumerationCategoryWithPetType(pet.getPetType()));

        Enumeration breed = enumerationList.stream()
                .filter(enumeration -> {
                    return pet.getBreed().equals(enumeration.getCode());
                }).findAny().orElseThrow(()->{
                    return new IllegalStateException("품종 code에 해당하는 품종 enum이 DB에 존재하지 않습니다");
                });


        return new PetDetailResponseDto(
                ObjectUtils.isEmpty(petProfileImageList) ? null : petProfileImageList.get(0).getImageUrl(),
                pet, breed);
    }

    public void deleteMemberPet(Member member) {
        memberPetService.deleteMemberPet(member);
    }
}
