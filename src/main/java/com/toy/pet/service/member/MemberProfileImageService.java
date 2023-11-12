package com.toy.pet.service.member;

import com.toy.pet.domain.common.FileUploadResultVO;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.entity.MemberProfileImage;
import com.toy.pet.repository.MemberProfileImageRepository;
import com.toy.pet.service.filemanagement.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileImageService {
    private final MemberProfileImageRepository memberProfileImageRepository;
    private final FileManagementService fileManagementService;

    public void saveMemberProfileImage(Member member, MultipartFile memberProfileImage) {
        if (ObjectUtils.isEmpty(memberProfileImage) || memberProfileImage.isEmpty()) {
            return;
        }

        fileManagementService.validationCheckForImageFile(memberProfileImage);

        FileUploadResultVO fileUploadResultVO = fileManagementService.uploadFile(
                memberProfileImage, "profile-image/member/" + member.getId() + "/" + LocalDate.now());


        MemberProfileImage savedMemberProfileImageEntity = new MemberProfileImage(member,
                fileUploadResultVO.getOriginalFileName(), fileUploadResultVO.getUploadFileName(),
                fileUploadResultVO.getUploadFilePath(), fileUploadResultVO.getUploadFileUrl());

        memberProfileImageRepository.save(savedMemberProfileImageEntity);
    }

    public List<MemberProfileImage> findMemberProfileImage(Member member) {
        return memberProfileImageRepository.findByMember(member);
    }

    public void deleteMemberProfileImage(Member member) {
        List<MemberProfileImage> memberProfileImageList = memberProfileImageRepository.findByMember(member);
        memberProfileImageList.forEach(memberProfileImageRepository::delete);
    }
}
