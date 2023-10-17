package com.toy.pet.api;

import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.domain.request.LoginRequest;
import com.toy.pet.domain.request.ReissuingTokenRequest;
import com.toy.pet.domain.response.LoginResponse;
import com.toy.pet.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @Operation(summary = "OAuth 로그인 API",
            description = "Provider에서 발급받은 access token을 받아 서버 JWT 토큰을 발행",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))})
            }
    )
    @PostMapping("/login/oauth")
    public Result oauthLogin(
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.issueJwtToken(OAuthProvider.findByCode(loginRequest.getProvider()),
                loginRequest.getAccessToken());
        return new Result(StatusCode.SUCCESS, loginResponse);
    }


    @Operation(summary = "토큰 신규 발급",
            description = "refresh 토큰으로 토큰 신규 발급",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))})
            }
    )
    @PostMapping("/reissue")
    public Result reissueToken(@Valid @RequestBody ReissuingTokenRequest reissuingTokenRequest) {
        LoginResponse loginResponse = authService.reissueJwtToken(reissuingTokenRequest.getRefreshToken());
        return new Result(StatusCode.SUCCESS, loginResponse);
    }
}
