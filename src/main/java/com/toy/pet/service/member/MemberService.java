package com.toy.pet.service.member;

import com.toy.pet.domain.common.User;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberProfileImage;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.entity.PetProfileImage;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.Relationship;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.Role;
import com.toy.pet.domain.request.MemberRegisterRequest;
import com.toy.pet.domain.response.MemberDetailResponseDto;
import com.toy.pet.domain.response.MemberRegisterResponse;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.MemberRepository;
import com.toy.pet.service.pet.PetProfileImageService;
import com.toy.pet.service.pet.PetService;
import com.toy.pet.service.term.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TermService termService;
    private final PetService petService;
    private final MemberProfileImageService memberProfileImageService;
    private final PetProfileImageService petProfileImageService;

    /**
     * 회원 가입
     * 프로세스
     * 1. 이미 존재하는 회원인지 확인
     * 2. 회원 저장
     * 3. 프로필 이미지 저장
     */
    @Transactional
    public MemberRegisterResponse registerMember(MemberRegisterRequest memberRegisterRequest, Long oauthId,
                                                 MultipartFile memberProfileImage, MultipartFile petProfileImage) {
        OAuthProvider oAuthProvider = memberRegisterRequest.getProvider();
        if (existMember(oAuthProvider, oauthId)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0015);
        }
        Member savedMember = memberRepository.save(memberRegisterRequest.toEntity(oauthId, Role.ROLE_USER));

        termService.saveMemberTerm(memberRegisterRequest.getTermRegisterRequestList(), savedMember);

        Pet savedPet = petService.registerPetAndConnectMember(memberRegisterRequest.getSharingCode(),
                memberRegisterRequest.getPetRegisterRequest(), savedMember, memberRegisterRequest.getRelationship());



        memberProfileImageService.saveMemberProfileImage(savedMember, memberProfileImage);
        Optional<PetProfileImage> savedPetProfileImage = petProfileImageService.savePetProfileImage(
                memberRegisterRequest.getSharingCode(), savedPet, petProfileImage, savedMember.getId());

        return new MemberRegisterResponse(savedPet, savedPetProfileImage.orElse(null));
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

    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = getMember(memberId);
        List<MemberProfileImage> memberProfileImageList = memberProfileImageService.findMemberProfileImage(member);
        if (memberProfileImageList.size() > 1) {
            throw new IllegalStateException("member는 하나의 프로필 이미지만 가질 수 있습니다");
        }

        Pet pet = petService.findPetByMember(member);

        Relationship relationShip = petService.findRelationShip(member, pet);

        return new MemberDetailResponseDto(
                ObjectUtils.isEmpty(memberProfileImageList) ? null : memberProfileImageList.get(0).getImageUrl(),
                member.getName(),
                relationShip.getCode(), relationShip.getName());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteMember(Long id, Long deleteMemberId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0016));

        member.delete(deleteMemberId);
    }
}
