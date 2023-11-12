package com.toy.pet.api;

import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.EnumerationCategory;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.response.EnumerationResponseDto;
import com.toy.pet.domain.response.EnumerationResponseListDto;
import com.toy.pet.service.enumeration.EnumerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enumerations")
@RequiredArgsConstructor
public class EnumerationApiController {

    private final EnumerationService enumerationService;

    @Operation(summary = "ENUM 조회 API", description = "전달 받은 카테고리에 해당하는 ENUM 목록을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EnumerationResponseListDto.class))})
            }
    )
    @GetMapping
    public Result findEnumerationList(
            @Parameter(name = "enumerationCategory",
            schema = @Schema(allowableValues = {"DOG_BREED", "CAT_BREED"}), in = ParameterIn.QUERY)
            @RequestParam("enumerationCategory") String enumerationCategory){

        List<EnumerationResponseDto> enumerationResponseDtoList = enumerationService
                .findEnumerationResponseDtoList(EnumerationCategory.findByCode(enumerationCategory));
        return new Result(StatusCode.SUCCESS, new EnumerationResponseListDto(enumerationResponseDtoList));
    }
}
