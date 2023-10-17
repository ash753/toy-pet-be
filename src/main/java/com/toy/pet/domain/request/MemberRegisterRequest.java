package com.toy.pet.domain.request;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest {

    @NotBlank
    @Schema(description = "OAuth provider 정보", allowableValues = {"KAKAO"})
    private String provider;

    @NotBlank
    @Schema(description = "OAuth provider에게 받은 Access Token")
    private String accessToken;

    @NotBlank
    @Schema(description = "email 주소", example = "gildong@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "이름", example = "홍길동")
    private String name;

    public Member toEntity(String nickName, Long OAuthId, Role role) {
        return new Member(nickName, email, name, OAuthProvider.findByCode(provider), OAuthId, role);
    }
}
