package com.toy.pet.domain.response;

import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.entity.PetProfileImage;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class MemberRegisterResponse {
    private final Long petId;
    private final String petProfileImageUrl;
    private final String petName;
    private final String sharingCode;


    public MemberRegisterResponse(Pet pet, PetProfileImage petProfileImage) {
        this.petId = pet.getId();
        this.petProfileImageUrl = ObjectUtils.isEmpty(petProfileImage) ? null : petProfileImage.getImageUrl();
        this.petName = pet.getName();
        this.sharingCode = pet.getSharingCode();
    }
}
