package com.example.myShop.service;


import com.example.myShop.repository.ProductRepository;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisCommands<String, String> redisCommands;
    private final ProductRepository productRepository;

    public void saveToRedis(String title, String imageURL) {
        redisCommands.setex(title, 60, imageURL);
        System.out.println("redis save: " + title + " = " + imageURL);
    }

    public String getFromRedis(String title) {

        String nameProduct = redisCommands.get(title);

        if (nameProduct != null) {
            System.out.println("get form redis: " + title);
            return nameProduct;
        } else {
            var product = productRepository.findByTitle(title);
            System.out.println("get from postgres: " + title + " = " + product.getImagePreviewURL());
            saveToRedis(product.getName(), product.getImagePreviewURL());
            return product.getImagePreviewURL();
        }
    }
}