package com.example.myShop.service;


import com.example.myShop.dto.ProductDTO;
import com.example.myShop.dto.UserDTO;
import com.example.myShop.entity.Image;
import com.example.myShop.entity.Product;
import com.example.myShop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class MapperService {

    private final PasswordEncoder passwordEncoder;

    public Product productToEntity(ProductDTO productDTO){
        var product = new Product();
        product.setId(productDTO.id());
        product.setName(productDTO.name());
        product.setCategory(productDTO.category());
        product.setPrice(productDTO.price());
        product.setImagePath(productDTO.imagePath());
        product.setImages(productDTO.images());

        return product;
    }

    public ProductDTO productToDto(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getImagePath(),
                product.getImages(),
                file1, file2,file3
        );
    }

    public User userToEntity(UserDTO userDTO){
        var user = new User();
        user.setId(userDTO.id());
        user.setEmail(userDTO.email().toLowerCase());
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        return user;
    }

    public UserDTO userToDTO(User user){

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public Image imageToEntity(MultipartFile file) throws IOException {

        Image image = new Image();

        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSizes(file.getSize());
        image.setBytes(file.getBytes());

        return image;

    }

}
