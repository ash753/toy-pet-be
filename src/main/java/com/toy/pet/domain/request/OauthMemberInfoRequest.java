package com.toy.pet.domain.request;

import com.toy.pet.domain.enums.OAuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OauthMemberInfoRequest {
    @NotNull
    @Schema(description = "OAuth provider 정보", example = "KAKAO")
    private OAuthProvider provider;

    @NotBlank
    @Schema(description = "OAuth provider에게 받은 Access Token")
    private String accessToken;
}
