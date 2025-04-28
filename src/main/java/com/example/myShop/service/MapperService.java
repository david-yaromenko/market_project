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
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MapperService {

    private final PasswordEncoder passwordEncoder;

    public Product productToEntity(ProductDTO productDTO) throws IOException {
        var product = new Product();
        product.setId(productDTO.id());
        product.setName(productDTO.name());
        product.setCategory(productDTO.category());
        product.setPrice(productDTO.price());
        return product;
    }

    public ProductDTO productToDto(Product product, List<MultipartFile> files) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                files

        );
    }

    public User userToEntity(UserDTO userDTO) {
        var user = new User();
        user.setId(userDTO.id());
        user.setEmail(userDTO.email().toLowerCase());
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        return user;
    }

    public UserDTO userToDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public Image imageToEntity(String url, Product product){
       var image = new Image();
       image.setImageURL(url);
       image.setProduct(product);

       return image;
    }

}
