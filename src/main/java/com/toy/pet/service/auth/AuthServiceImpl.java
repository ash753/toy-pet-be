package com.toy.pet.service.auth;

import com.toy.pet.domain.common.OAuth2UserInfo;
import com.toy.pet.domain.common.User;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.domain.response.LoginResponse;
import com.toy.pet.exception.CommonException;
import com.toy.pet.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final List<OAuth2UserInfoService> oAuth2UserInfoServiceList;
    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    @Override
    public LoginResponse issueJwtToken(OAuthProvider oAuthProvider, String accessToken) {

        for (OAuth2UserInfoService oAuth2UserInfoService : oAuth2UserInfoServiceList) {
            if (oAuth2UserInfoService.supports(oAuthProvider)) {
                OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoService.getOAuth2UserInfo(accessToken);
                Optional<Member> memberOptional = memberService.getMember(oAuthProvider, oAuth2UserInfo.getId());
                if (memberOptional.isEmpty()) {
                    throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0017);
                }
                User user = new User(memberOptional.get());
                String serverAccessToken = jwtTokenService.createAccessToken(user);
                String serverRefreshToken = jwtTokenService.createRefreshToken(user);

                return new LoginResponse(serverAccessToken, serverRefreshToken);
            }
        }
        throw new IllegalStateException("No OAuth2UserInfoService for parameter OAuthProvider parameter : "+ oAuthProvider);
    }

    @Override
    public LoginResponse reissueJwtToken(String refreshToken) {
        Long memberId = jwtTokenService.getMemberId(refreshToken);
        Member member = memberService.getMember(memberId);
        User user = new User(member);

        String serverAccessToken = jwtTokenService.createAccessToken(user);
        String serverRefreshToken = jwtTokenService.createRefreshToken(user);

        return new LoginResponse(serverAccessToken, serverRefreshToken);
    }

    @Override
    public void registerMember(OAuthProvider oAuthProvider, MemberRegisterRequest memberRegisterRequest) {
        for (OAuth2UserInfoService oAuth2UserInfoService : oAuth2UserInfoServiceList) {
            if (oAuth2UserInfoService.supports(oAuthProvider)) {
                OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoService.getOAuth2UserInfo(memberRegisterRequest.getAccessToken());
                memberService.registerMember(memberRegisterRequest, oAuth2UserInfo.getNickName(), oAuth2UserInfo.getId(),
                        oAuth2UserInfo.getProfileImageUrl());
                return;
            }
        }
        throw new IllegalStateException("No OAuth2UserInfoService for parameter OAuthProvider parameter : "+ oAuthProvider);
    }
}
