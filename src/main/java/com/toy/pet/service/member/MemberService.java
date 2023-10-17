package com.toy.pet.service.member;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.Role;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileImageService profileImageService;

    /**
     * 회원 가입
     * 프로세스
     * 1. 이미 존재하는 회원인지 확인
     * 2. 회원 저장
     * 3. 프로필 이미지 저장
     */
    @Transactional
    public void registerMember(MemberRegisterRequest memberRegisterRequest, String nickName, Long oauthId,
                               String profileImageUrl) {
        OAuthProvider oAuthProvider = OAuthProvider.findByCode(memberRegisterRequest.getProvider());

        if (existMember(oAuthProvider, oauthId)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0015);
        }

        Member member = memberRegisterRequest.toEntity(nickName, oauthId, Role.ROLE_USER);
        memberRepository.save(member);

        profileImageService.saveProfileImage(member, profileImageUrl);
    }

    /**
     * 회원 조회
     */
    public Optional<Member> getMember(OAuthProvider oAuthProvider, Long OAuthId) {
        return memberRepository.findMemberByOauthProviderAndOauthId(oAuthProvider, OAuthId);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0016));
    }

    public boolean existMember(OAuthProvider oAuthProvider, Long OAuthId) {
        return memberRepository.findMemberByOauthProviderAndOauthId(oAuthProvider, OAuthId).isPresent();
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteMember(Long id, Long deleteMemberId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0016));

        member.delete(deleteMemberId);
        profileImageService.deleteMemberProfileImageList(member, deleteMemberId);
    }
}
