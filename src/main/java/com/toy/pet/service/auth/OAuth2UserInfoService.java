package com.toy.pet.service.auth;

import com.toy.pet.domain.common.OAuth2UserInfo;
import com.toy.pet.domain.enums.OAuthProvider;

public interface OAuth2UserInfoService {

    OAuth2UserInfo getOAuth2UserInfo(String accessToken);

    boolean supports(OAuthProvider oAuthProvider);
}
