package com.example.myShop.service;

import com.example.myShop.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.MapUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.bucket}")
    private String BUCKET_NAME;

    private final S3Client s3Client;
    private String fileName;

    public List<String> uploadFile(List<MultipartFile> files) throws IOException {

        List<String> imageUrl = new ArrayList<>();

        if (files.isEmpty()){
            System.out.println("Not found image");
            throw new NullPointerException("Not found image");
        }

        for (MultipartFile file: files){
            fileName = file.getOriginalFilename();
            String folderName = "imageforproduct/";
            String keyFolder = folderName + fileName;
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(keyFolder)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));

            imageUrl.add("https://" + BUCKET_NAME + ".s3.us-west-2.amazonaws.com/imageforproduct/" + fileName);
        }

        return imageUrl;
    }
}


