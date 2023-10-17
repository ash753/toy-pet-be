package com.toy.pet.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReissuingTokenRequest {
    private String refreshToken;
}
