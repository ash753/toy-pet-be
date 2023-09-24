package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

public enum OAuthProvider {

    KAKAO("KAKAO","카카오");

    private String code;
    private String name;

    OAuthProvider(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "OAuthProvider{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static OAuthProvider findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002.getCode(), "oauthProvider가 비어있습니다.");
        }

        for (OAuthProvider oauthProvider : OAuthProvider.values()) {
            if (oauthProvider.code.equals(code)) {
                return oauthProvider;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002.getCode(), "올바르지 않은 OAuthProvider입니다.");
    }
}
