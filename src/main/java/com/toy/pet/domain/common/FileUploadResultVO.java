package com.toy.pet.domain.common;

import lombok.Getter;

@Getter
public class FileUploadResultVO {
    private final String originalFileName;
    private final String uploadFileName;
    private final String uploadFilePath;
    private final String uploadFileUrl;

    public FileUploadResultVO(String originalFileName, String uploadFileName, String uploadFilePath, String uploadFileUrl) {
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFilePath = uploadFilePath;
        this.uploadFileUrl = uploadFileUrl;
    }
}
