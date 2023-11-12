package com.toy.pet.service.filemanagement;

import com.toy.pet.domain.common.FileUploadResultVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {
    FileUploadResultVO uploadFile(MultipartFile multipartFile, String uploadFilePath);

    void deleteFile(String uploadFilePath, String uploadFileName);

    void validationCheckForImageFile(MultipartFile multipartFile);
}
