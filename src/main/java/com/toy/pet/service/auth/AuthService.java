package com.toy.pet.service.auth;


import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.response.LoginResponse;

public interface AuthService {
    LoginResponse issueJwtToken(OAuthProvider oAuthProvider, String accessToken);
}
