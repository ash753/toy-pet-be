package com.toy.pet.domain.response;

import lombok.Getter;

@Getter
public class MemberDetailResponseDto {

    private String profileImageUrl;
    private String name;
    private String relationshipCode;
    private String relationshipName;

    public MemberDetailResponseDto(String profileImageUrl, String name, String relationshipCode,
                                   String relationshipName) {
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.relationshipCode = relationshipCode;
        this.relationshipName = relationshipName;
    }
}
