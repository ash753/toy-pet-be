package com.toy.pet.domain.request;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest {

    @NotNull
    @Schema(description = "OAuth provider 정보", example = "KAKAO")
    private OAuthProvider provider;

    @NotBlank
    @Schema(description = "OAuth provider에게 받은 Access Token")
    private String accessToken;

    @NotBlank
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @NotNull
    @Schema(description = "반려동물과의 관계", example = "BROTHER")
    private Relationship relationship;

    @Schema(description = "공유 코드")
    private String sharingCode;

    @Valid
    private PetRegisterRequest petRegisterRequest;

    @NotNull
    @Valid
    private List<TermRegisterRequest> termRegisterRequestList;

    public Member toEntity(Long OAuthId, Role role) {
        return new Member(name, provider, OAuthId, role);
    }
}
