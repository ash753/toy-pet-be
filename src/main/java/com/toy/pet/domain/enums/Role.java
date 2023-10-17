package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
public enum Role {

    ROLE_USER("ROLE_USER", "일반 사용자");

    private String code;
    private String name;

    Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Role findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002.getCode(), "Role이 비어있습니다.");
        }

        for (Role role : Role.values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002.getCode(), "올바르지 않은 Role입니다.");
    }
}
