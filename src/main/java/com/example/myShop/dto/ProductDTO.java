package com.example.myShop.dto;

import com.example.myShop.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductDTO(
        Integer id,
        String name,
        String category,
        Float price,
        String imagePath,
        List<Image> images,
        MultipartFile file1,
        MultipartFile file2,
        MultipartFile file3
) {
}
