package com.toy.pet.domain.common;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Long getId() {
        return Long.valueOf(String.valueOf(attributes.get("id")));
    }

    @Override
    public String getNickName() {
        Map<String, Object> profileMap = (Map<String, Object>) getKakaoAccount().get("profile");
        return profileMap.get("nickname").toString();
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> profileMap = (Map<String, Object>) getKakaoAccount().get("profile");
        return profileMap.get("profile_image_url").toString();
    }

    private Map<String, Object> getKakaoAccount(){
        return(Map<String, Object>) attributes.get("kakao_account");
    }
}
