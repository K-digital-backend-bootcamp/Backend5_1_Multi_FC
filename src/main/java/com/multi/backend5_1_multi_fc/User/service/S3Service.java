package com.multi.backend5_1_multi_fc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName; // application.properties의 버킷 이름

    public String uploadFile(MultipartFile file) throws IOException {
        // 파일이 없거나 비어있으면 null 반환
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 1. 파일 이름이 겹치지 않도록 고유한 이름 생성
        String originalFilename = file.getOriginalFilename();
        // 예: "profile/랜덤UUID_son.jpg"
        String uniqueFilename = "profile/" + UUID.randomUUID().toString() + "_" + originalFilename;

        // 2. S3에 업로드할 요청(Request) 객체 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFilename) // S3에 저장될 파일명
                .contentType(file.getContentType()) // 파일 타입 (예: image/jpeg)
                .build();

        // 3. 파일 업로드 실행
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 4. 업로드된 파일의 S3 URL 반환 (이 URL을 DB에 저장함)
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(uniqueFilename)).toExternalForm();
    }
}