package com.toy.pet.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank
    @Schema(description = "OAuth provider 정보", allowableValues = {"KAKAO"})
    private String provider;

    @NotBlank
    @Schema(description = "OAuth provider에게 받은 Access Token")
    private String accessToken;
}
