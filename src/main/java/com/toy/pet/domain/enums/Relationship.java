package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
public enum Relationship {
    FATHER("FATHER", "아빠"),
    MOTHER("MOTHER", "엄마"),
    GRAND_FATHER("GRAND_FATHER", "할아버지"),
    GRAND_MOTHER("GRAND_MOTHER", "할머니"),
    SISTER("SISTER", "누나/언니"),
    BROTHER("BROTHER", "형/오빠"),
    ;


    private String code;
    private String name;

    Relationship(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Relationship findByCode(String code) {
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
        }

        for (Relationship relationship : Relationship.values()) {
            if (relationship.code.equals(code)) {
                return relationship;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
    }
}
