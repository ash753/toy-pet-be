package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
public enum PetType {

    CAT("CAT", "고양이"),
    DOG("DOG", "강아지");

    private String code;
    private String name;

    PetType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PetType findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
        }

        for (PetType petType : PetType.values()) {
            if (petType.code.equals(code)) {
                return petType;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
    }
}
