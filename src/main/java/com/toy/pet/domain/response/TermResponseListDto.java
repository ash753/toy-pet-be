package com.toy.pet.domain.response;

import lombok.Getter;

import java.util.List;

@Getter
public class TermResponseListDto {
    private final List<TermResponseDto> termResponseDtoList;

    public TermResponseListDto(List<TermResponseDto> termResponseDtoList) {
        this.termResponseDtoList = termResponseDtoList;
    }
}
