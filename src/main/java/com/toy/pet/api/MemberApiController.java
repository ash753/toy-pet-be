package com.toy.pet.api;

import com.toy.pet.domain.common.PrincipalDetails;
import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.service.auth.AuthService;
import com.toy.pet.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final AuthService authService;
    private final MemberService memberService;

    // 회원 가입
    @Operation(summary = "OAuth 회원가입 API",
            description = "Provider에서 발급받은 access token과 추가 정보를 받아 회원가입을 진행",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))})
            }
    )
    @PostMapping("/register/oauth")
    public Result registerMember(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        authService.registerMember(OAuthProvider.findByCode(memberRegisterRequest.getProvider()), memberRegisterRequest);
        return new Result(StatusCode.SUCCESS);
    }

    @Operation(summary = "내 정보 조회 - 테스트용",
            description = "Access token에 담겨있는 id로 내 정보를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))})
            }
    )
    @GetMapping("/my-info")
    public Result getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        Member member = memberService.getMember(memberId);
        return new Result(StatusCode.SUCCESS, member);
    }

    @Operation(summary = "회원 탈퇴",
            description = "회원 탈퇴",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))})
            }
    )
    @DeleteMapping
    public Result deleteMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        memberService.deleteMember(memberId, memberId);
        return new Result(StatusCode.SUCCESS);
    }
}
