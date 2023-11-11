package com.toy.pet.domain.response;

import lombok.Getter;

@Getter
public class OauthMemberInfoResponse {
    private final String profileImageLink;
    private final String nickName;

    public OauthMemberInfoResponse(String profileImageLink, String nickName) {
        this.profileImageLink = profileImageLink;
        this.nickName = nickName;
    }
}
