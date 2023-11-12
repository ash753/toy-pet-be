package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
public enum Gender {

    MALE("MALE", "수컷"),
    FEMALE("FEMALE", "암컷");

    private String code;
    private String name;

    Gender(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Gender findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
        }

        for (Gender gender : Gender.values()) {
            if (gender.code.equals(code)) {
                return gender;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
    }
}
