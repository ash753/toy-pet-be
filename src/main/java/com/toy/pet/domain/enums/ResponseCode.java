package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum ResponseCode {
    CODE_0001("0001", "system error"),
    CODE_0002("0002", "잘못된 enum code입니다."),

    // 인증, 인가
    CODE_0011("0011", "잘못된 JWT 서명입니다."),
    CODE_0012("0012", "JWT 토큰이 잘못되었습니다."),
    CODE_0013("0013", "JWT 토큰이 만료되었습니다.");


    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCode findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(ResponseCode.CODE_0002);
        }

        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.code.equals(code)) {
                return responseCode;
            }
        }

        throw new CommonException(ResponseCode.CODE_0002);
    }
}