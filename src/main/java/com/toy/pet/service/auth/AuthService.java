package com.toy.pet.service.auth;


import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.domain.response.LoginResponse;

public interface AuthService {
    LoginResponse issueJwtToken(OAuthProvider oAuthProvider, String accessToken);

    LoginResponse reissueJwtToken(String refreshToken);

    void registerMember(OAuthProvider oAuthProvider, MemberRegisterRequest memberRegisterRequest);
}
