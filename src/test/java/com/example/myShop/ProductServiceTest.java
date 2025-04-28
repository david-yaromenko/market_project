package com.example.myShop;

import com.example.myShop.dto.ProductDTO;
import com.example.myShop.entity.Product;
import com.example.myShop.repository.ProductRepository;
import com.example.myShop.service.ImageService;
import com.example.myShop.service.MapperService;
import com.example.myShop.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Подключаем Mockito
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private MapperService mapperService;

    @InjectMocks
    private ProductsService productService; // Тестируемый сервис

    private ProductDTO productDTO;
    private Product product;
    private MockMultipartFile file1;
    private MockMultipartFile file2;
    private MockMultipartFile file3;

    @BeforeEach
    void setUp() {
        // Создаем тестовый DTO
        productDTO = new ProductDTO(1, "Test product", "Test category", 9F, null, null);

        // Создаем тестовый продукт (entity)
        product = new Product();
        product.setName("Test Product");
        product.setCategory("Test category");
        product.setPrice(9F);

        // Моки изображений
        file1 = new MockMultipartFile("file1", "image1.jpg", "image/jpeg", new byte[10]);
        file2 = new MockMultipartFile("file2", "image2.jpg", "image/jpeg", new byte[10]);
        file3 = new MockMultipartFile("file3", "image3.jpg", "image/jpeg", new byte[10]);
    }

    @Test
    void createProduct_ShouldSaveProductAndImages() throws IOException {
        // Настроим поведение маппера
        when(mapperService.productToEntity(productDTO)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(mapperService.productToDto(any(Product.class), any())).thenReturn(productDTO);

        // Вызываем тестируемый метод
        ProductDTO savedProduct = productService.createProduct(productDTO);

        // Проверяем, что продукт был сохранен
        verify(productRepository, times(1)).save(any(Product.class));

        // Проверяем, что изображения были сохранены
//        verify(imageService, times(1)).createImage(product);

        // Проверяем, что возвращаемый DTO не null
        assertNotNull(savedProduct);
        assertEquals("Test product", savedProduct.name());
    }


//    @Test
//    void createProduct_ShouldThrowException_WhenCategoryIsEmpty() {
//        ProductDTO invalidDTO = new ProductDTO("Product", "", new BigDecimal("10.00"));
//
//        Exception exception = assertThrows(NullPointerException.class, () -> {
//            productService.createProduct(invalidDTO, file1, file2, file3);
//        });
//
//        assertEquals("Name, category and price can't be empty", exception.getMessage());
//    }
}

