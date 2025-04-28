package com.example.myShop.service;

import com.example.myShop.dto.ProductDTO;
import com.example.myShop.entity.Image;
import com.example.myShop.entity.Product;
import com.example.myShop.entity.User;
import com.example.myShop.exeption.UserAlreadyExistExeption;
import com.example.myShop.repository.ImageRepository;
import com.example.myShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final MapperService mapperService;

    public ProductDTO createProduct(ProductDTO productDTO) throws IOException {

        if (productDTO.name().isEmpty() || productDTO.category().isEmpty() || productDTO.price() == null){
            System.out.println("Name, category and price can't be empty");
            throw new NullPointerException("Name, category and price can't be empty");
        }
        var entity = mapperService.productToEntity(productDTO);
        var product = productRepository.save(entity);
        imageService.saveImage(productDTO, product);
        productRepository.save(entity);
        log.info("Saving new Product. Title: {}; Author: {} Image: {}", productDTO.name(), productDTO.category(), product.getImages());

        return mapperService.productToDto(product, null);
    }

    public List<Product> getProductsByName(String name) {

        List<Product> productDb = productRepository.findByName(name);
        if (productDb.isEmpty()){
            System.out.println(productDb + " not create");
            throw new NullPointerException("Product with this name is not created");
        }
        return productDb;
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

}
