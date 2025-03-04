package com.example.myShop.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final MapperService mapperService;
    private final ImageRepository imageRepository;
    public void createImage(MultipartFile file1, MultipartFile file2, MultipartFile file3, Product product) throws IOException {

        if (file1.getSize() != 0) {
            Image image1 = mapperService.imageToEntity(file1);
            addImageToProduct(image1, product);
        }
        if (file2.getSize() != 0) {
            Image image2 = mapperService.imageToEntity(file2);
            addImageToProduct(image2, product);
        }
        if (file3.getSize() != 0) {
            Image image3 = mapperService.imageToEntity(file3);
            addImageToProduct(image3, product);
        }
    }

    public void addImageToProduct(Image image, Product product) {
        image.setProduct(product);
        imageRepository.save(image);
    }
}
