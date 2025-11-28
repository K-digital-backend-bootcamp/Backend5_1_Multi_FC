package com.multi.backend5_1_multi_fc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new IllegalStateException("S3 버킷 이름이 올바르지 않습니다. 환경설정을 확인하세요.");
        }

        // 1. 파일 이름이 겹치지 않도록 고유한 이름 생성 (영어/숫자/언더바만 남기기)
        String originalFilename = file.getOriginalFilename();
        String safeFilename = (originalFilename == null) ? "unknown" : originalFilename.replaceAll("[^a-zA-Z0-9.]", "_");
        String uniqueFilename = "profile/" + UUID.randomUUID() + "_" + safeFilename;

        // 2. S3에 업로드할 요청(Request) 객체 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFilename)
                .contentType(file.getContentType())
                .build();

        // 3. S3 업로드 실제 수행
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 4. 업로드된 파일의 S3 URL 반환
        // URL 인코딩을 적용하여 반환 (브라우저 호환성)
        String encodedKey = URLEncoder.encode(uniqueFilename, StandardCharsets.UTF_8).replace("+", "%20");
        String url = "https://" + bucketName + ".s3.amazonaws.com/" + encodedKey;

        return url;
    }
}