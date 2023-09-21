package com.toy.pet.service.auth;

import com.toy.pet.domain.common.Constant;
import com.toy.pet.domain.common.KakaoOAuth2UserInfo;
import com.toy.pet.domain.common.OAuth2UserInfo;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.service.WebclientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserInfoServiceImpl implements OAuth2UserInfoService {

    private final WebclientService webclientService;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String accessToken) {
        Map<String, Object> userAttributes = getUserAttributesByAccessToken(accessToken);
        return new KakaoOAuth2UserInfo(userAttributes);
    }

    private Map<String, Object> getUserAttributesByAccessToken(String accessToken) {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.put(Constant.AUTHORIZATION, Arrays.asList("Bearer "+accessToken));
        Map<String, Object> result = new HashMap<>();
        return webclientService.getRequest(kakaoUserInfoUri, headerMap, result.getClass());
    }

    @Override
    public boolean supports(OAuthProvider oAuthProvider) {
        return OAuthProvider.KAKAO.equals(oAuthProvider);
    }
}
