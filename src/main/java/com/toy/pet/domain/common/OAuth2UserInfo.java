package com.toy.pet.domain.common;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes(){
        return attributes;
    }

    public abstract Long getId();
    public abstract String getNickName();

    public abstract String getProfileImageUrl();
}
