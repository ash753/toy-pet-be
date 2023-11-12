package com.toy.pet.api;

import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.response.LoginResponse;
import com.toy.pet.domain.response.TermResponseDto;
import com.toy.pet.domain.response.TermResponseListDto;
import com.toy.pet.service.term.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terms")
@RequiredArgsConstructor
public class TermApiController {

    private final TermService termService;

    @Operation(summary = "회원 가입용 약관 조회 API", description = "회원 가입용 약관 목록을 조회 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TermResponseListDto.class))})
            }
    )
    @GetMapping("/member-register")
    public Result findTermList(){
        TermResponseListDto termResponseListDto = termService.findAllTermList();
        return new Result(StatusCode.SUCCESS, termResponseListDto);
    }
}
