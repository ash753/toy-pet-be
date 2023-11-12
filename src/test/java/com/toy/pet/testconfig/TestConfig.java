package com.toy.pet.testconfig;

import com.toy.pet.domain.common.FileUploadResultVO;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.exception.CommonException;
import com.toy.pet.service.filemanagement.FileManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@TestConfiguration
public class TestConfig {

    @Bean
    public FileManagementService fileManagementService(){
        return new FileManagementService() {
            @Override
            public FileUploadResultVO uploadFile(MultipartFile multipartFile, String uploadFilePath) {
                return null;
            }

            @Override
            public void deleteFile(String uploadFilePath, String uploadFileName) {

            }

            @Override
            public void validationCheckForImageFile(MultipartFile multipartFile) {
                String filenameExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
                if (StringUtils.hasText(filenameExtension) &&
                        (filenameExtension.startsWith("image") || filenameExtension.startsWith("png"))) {
                    return;
                }

                throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0041);
            }

            /**
             * UUID 파일명 반환
             */
            public String getUuidFileName(String fileName) {
                String ext = fileName.substring(fileName.indexOf(".") + 1);
                return fileName + "_" + UUID.randomUUID().toString() + "." + ext;
            }
        };
    }
}
