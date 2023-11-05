package com.toy.pet.testconfig;

import com.toy.pet.domain.common.FileUploadResultVO;
import com.toy.pet.service.filemanagement.FileManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;


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
        };
    }
}
