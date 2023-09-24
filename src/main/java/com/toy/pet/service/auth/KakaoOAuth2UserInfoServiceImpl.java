package com.toy.pet.service.auth;

import com.toy.pet.domain.common.Constant;
import com.toy.pet.domain.common.KakaoOAuth2UserInfo;
import com.toy.pet.domain.common.OAuth2UserInfo;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.exception.CommonException;
import com.toy.pet.service.WebclientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
        try {
            ResponseEntity<? extends Map> response = webclientService.getRequest(kakaoUserInfoUri, headerMap, result.getClass());
            return (Map<String, Object>) response.getBody();
        } catch (WebClientResponseException e) {
            if(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()).equals(e.getStatusCode())){
                throw new CommonException(HttpStatus.UNAUTHORIZED, ResponseCode.CODE_0014);
            }else{
                throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.CODE_0001);
            }
        }
    }

    @Override
    public boolean supports(OAuthProvider oAuthProvider) {
        return OAuthProvider.KAKAO.equals(oAuthProvider);
    }
}
