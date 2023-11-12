package com.toy.pet.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class EnumerationResponseListDto {
    private final List<EnumerationResponseDto> enumerationResponseDtoList;

    public EnumerationResponseListDto(List<EnumerationResponseDto> enumerationResponseDtoList) {
        this.enumerationResponseDtoList = enumerationResponseDtoList;
    }
}
