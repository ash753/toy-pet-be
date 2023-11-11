package com.toy.pet.service.auth;


import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.domain.response.LoginResponse;
import com.toy.pet.domain.response.OauthMemberInfoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    LoginResponse issueJwtToken(OAuthProvider oAuthProvider, String accessToken);

    LoginResponse reissueJwtToken(String refreshToken);

    void registerMember(OAuthProvider oAuthProvider, MemberRegisterRequest memberRegisterRequest,
                        MultipartFile memberProfileImage, MultipartFile petProfileImage);

    OauthMemberInfoResponse getOauthMemberInfoResponse(OAuthProvider oAuthProvider, String accessToken);
}
