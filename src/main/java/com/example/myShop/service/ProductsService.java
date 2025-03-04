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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductRepository productRepository;
    private final MapperService mapperService;
    private final ImageService imageService;

    //    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
//        Сохранение картинки в хранилище
//        String fileName = UUID.randomUUID() + file1.getOriginalFilename();
//        Path path = Paths.get(UPLOAD_DIR + fileName);
//        Files.createDirectories(path.getParent());
//        file1.transferTo(path.toFile());
//        String pathFile = "uploads/" + fileName;
//        entity.setImagePath(pathFile);

        if (productDTO.name().isEmpty() || productDTO.category().isEmpty() || productDTO.price() == null){
            System.out.println("Name, category and price can't be empty");
            throw new NullPointerException("Name, category and price can't be empty");
        }

        var entity = mapperService.productToEntity(productDTO);
        var product = productRepository.save(entity);
        imageService.createImage(file1, file2, file3, product);
        log.info("Saving new Product. Title: {}; Author: {} Image: {}", productDTO.name(), productDTO.category(), file1.getOriginalFilename()); //тут вешаем чтобы видеть логи. в фигурные скобки джава сама подставить строковое предсталение о добавленном товаре, потому что у нас есть метод toString
        return mapperService.productToDto(product, file1, file2, file3);

    }

    public ProductDTO getProductById(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapperService.productToDto(product, null, null, null);
    }

    public ProductDTO getProductByName(String name) {

        Optional<Product> productDb = Optional.ofNullable(productRepository.findByName(name));
        if (productDb.isEmpty()){
            System.out.println(productDb + " not create");
            throw new NullPointerException("Product with this name is not created");
        }
        var product = productRepository.findByName(name);
        return mapperService.productToDto(product, null, null, null);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

}
