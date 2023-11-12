package com.toy.pet.service.filemanagement;

import com.toy.pet.domain.common.FileUploadResultVO;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Profile("!test")
@Slf4j
@Service
@RequiredArgsConstructor
public class FileManagementServiceImpl implements FileManagementService{

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public FileUploadResultVO uploadFile(MultipartFile multipartFile, String uploadFilePath) {

        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("there is no multipart file");
        }

        if (!StringUtils.hasText(uploadFilePath)) {
            throw new IllegalArgumentException("uploadFilePath must not be empty");
        }

        final String originalFileName = multipartFile.getOriginalFilename();
        final String uploadFileName = getUuidFileName(originalFileName);

        String key = uploadFilePath + "/" + uploadFileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        try {
            RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            log.error("", e);
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.CODE_0040, e);
        }

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        final String uploadFileUrl = s3Client.utilities().getUrl(getUrlRequest).toString();

        return new FileUploadResultVO(originalFileName, uploadFileName, uploadFilePath, uploadFileUrl);
    }

    @Override
    public void deleteFile(String uploadFilePath, String uploadFileName) {
        String key = uploadFilePath + "/" + uploadFileName;

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key).build();
        s3Client.deleteObject(deleteObjectRequest);
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
}
