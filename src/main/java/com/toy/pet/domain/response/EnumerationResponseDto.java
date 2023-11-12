package com.toy.pet.domain.response;

import com.toy.pet.domain.entity.Enumeration;
import lombok.Getter;

@Getter
public class EnumerationResponseDto {
    private final String code;
    private final String name;

    public EnumerationResponseDto(Enumeration enumeration) {
        this.code = enumeration.getCode();
        this.name = enumeration.getName();
    }
}
