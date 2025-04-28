package com.example.myShop.service;

import com.example.myShop.dto.ProductDTO;
import com.example.myShop.entity.Image;
import com.example.myShop.entity.Product;
import com.example.myShop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final MapperService mapperService;
    private final S3Service s3Service;
    private final RedisService redisService;
    private final ImageRepository imageRepository;

    public void saveImage(ProductDTO productDTO, Product product) throws IOException {

        var url = s3Service.uploadFile(productDTO.images());
        url.forEach(img -> {

            imageRepository.save(mapperService.imageToEntity(img, product));
            product.setImagePreviewURL(url.getFirst());

            redisService.saveToRedis(product.getName(), product.getImagePreviewURL());
        });
    }
}
