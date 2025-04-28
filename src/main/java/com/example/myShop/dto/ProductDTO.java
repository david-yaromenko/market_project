package com.example.myShop.dto;

import com.example.myShop.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductDTO(
        Integer id,
        String name,
        String category,
        Float price,
        List<MultipartFile> images
) {
}
