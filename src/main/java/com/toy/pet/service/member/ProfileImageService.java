package com.toy.pet.service.member;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.ProfileImage;
import com.toy.pet.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileImageService {
    private final ProfileImageRepository profileImageRepository;

    @Transactional
    public void saveProfileImage(Member member, String imageUrl) {
        ProfileImage profileImage = new ProfileImage(member, imageUrl);
        profileImageRepository.save(profileImage);
    }

    @Transactional
    public void deleteMemberProfileImageList(Member member, Long deleteMemberId) {
        List<ProfileImage> profileImageList = profileImageRepository.findByMember(member);
        for (ProfileImage profileImage : profileImageList) {
            profileImage.delete(deleteMemberId);
        }
    }
}
