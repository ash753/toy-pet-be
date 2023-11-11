package com.toy.pet.api;

import com.toy.pet.domain.common.PrincipalDetails;
import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.request.OauthMemberInfoRequest;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.domain.response.OauthMemberInfoResponse;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final AuthService authService;
    private final MemberService memberService;

    @Operation(summary = "oauth 회원 정보 조회 API", description = "회원가입 전 사용자 정보 조회에 사용하는 API 입니다. " +
            "OAuth access token을 입력 받으면, 회원 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OauthMemberInfoResponse.class))})
            }
    )
    @PostMapping("/oauth-info")
    public Result getOauthMemberInfo(@Valid @RequestBody OauthMemberInfoRequest oauthMemberInfoRequest) {
        OauthMemberInfoResponse oauthMemberInfoResponse = authService.getOauthMemberInfoResponse(oauthMemberInfoRequest.getProvider(),
                oauthMemberInfoRequest.getAccessToken());
        return new Result(StatusCode.SUCCESS, oauthMemberInfoResponse);
    }


    // 회원 가입
    @Operation(summary = "OAuth 회원가입 API",
            description = "Provider에서 발급받은 access token과 추가 정보를 받아 회원가입을 진행",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Result.class))})
            }
    )
    @PostMapping(value = "/register/oauth", consumes =  {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result registerMember(@Valid @RequestPart MemberRegisterRequest memberRegisterRequest,
                                 @RequestPart(required = false) MultipartFile memberProfileImage,
                                 @RequestPart(required = false) MultipartFile petProfileImage) {
        authService.registerMember(memberRegisterRequest.getProvider(), memberRegisterRequest,
                memberProfileImage, petProfileImage);
        return new Result(StatusCode.SUCCESS);
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
