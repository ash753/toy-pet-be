package com.toy.pet.service.pet;

import com.toy.pet.domain.common.FileUploadResultVO;
import com.toy.pet.domain.entity.Pet;
import com.toy.pet.domain.entity.PetProfileImage;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.exception.CommonException;
import com.toy.pet.repository.PetProfileImageRepository;
import com.toy.pet.service.filemanagement.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetProfileImageService {
    private final PetProfileImageRepository petProfileImageRepository;
    private final FileManagementService fileManagementService;

    public Optional<PetProfileImage> savePetProfileImage(String sharingCode, Pet pet, MultipartFile petProfileImage, Long memberId) {
        if (ObjectUtils.isEmpty(petProfileImage) || petProfileImage.isEmpty()) {
            return Optional.empty();
        }

        // 반려동물 코드가 없는 경우만 반려동물의 프로필을 등록할 수 있다.
        if (StringUtils.hasText(sharingCode) && !petProfileImage.isEmpty()) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0082);
        }

        fileManagementService.validationCheckForImageFile(petProfileImage);

        FileUploadResultVO fileUploadResultVO = fileManagementService.uploadFile(
                petProfileImage, "profile-image/pet/" + pet.getId() + "/" + LocalDate.now());

        PetProfileImage savedPetProfileImageEntity = new PetProfileImage(pet, fileUploadResultVO.getOriginalFileName(),
                fileUploadResultVO.getUploadFileName(), fileUploadResultVO.getUploadFilePath(),
                fileUploadResultVO.getUploadFileUrl(), memberId);

        petProfileImageRepository.save(savedPetProfileImageEntity);

        return Optional.of(savedPetProfileImageEntity);
    }
}
