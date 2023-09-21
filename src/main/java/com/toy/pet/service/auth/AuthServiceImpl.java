package com.toy.pet.service.auth;

import com.toy.pet.domain.common.OAuth2UserInfo;
import com.toy.pet.domain.common.User;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.response.LoginResponse;
import com.toy.pet.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final List<OAuth2UserInfoService> oAuth2UserInfoServiceList;
    private final JwtTokenService jwtTokenService;

    @Override
    public LoginResponse issueJwtToken(OAuthProvider oAuthProvider, String accessToken) {

        for (OAuth2UserInfoService oAuth2UserInfoService : oAuth2UserInfoServiceList) {
            if (oAuth2UserInfoService.supports(oAuthProvider)) {
                OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoService.getOAuth2UserInfo(accessToken);
                User user = oAuth2UserInfo.createUser();

                String serverAccessToken = jwtTokenService.createAccessToken(user);
                String serverRefreshToken = jwtTokenService.createRefreshToken(user);

                return new LoginResponse(serverAccessToken, serverRefreshToken);
            }
        }
        throw new IllegalStateException("No OAuth2UserInfoService for parameter OAuthProvider parameter : "+ oAuthProvider);
    }
}
