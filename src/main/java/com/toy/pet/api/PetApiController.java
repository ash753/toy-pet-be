package com.toy.pet.api;

import com.toy.pet.domain.common.PrincipalDetails;
import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.response.MemberDetailResponseDto;
import com.toy.pet.domain.response.PetDetailResponseDto;
import com.toy.pet.service.member.MemberService;
import com.toy.pet.service.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetApiController {

    private final PetService petService;
    private final MemberService memberService;

    @Operation(summary = "반려동물 정보 상세 조회", description = "access token에 해당하는 반려동물의 정보 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetDetailResponseDto.class))})
            })
    @GetMapping("/detail")
    public Result getMemberDetail(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = memberService.getMember(principalDetails.getMemberId());
        PetDetailResponseDto petDetailResponseDto = petService.findPetDetailResponseDto(member);
        return new Result(StatusCode.SUCCESS, petDetailResponseDto);
    }
}
