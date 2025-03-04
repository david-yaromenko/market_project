package com.example.myShop.controller;

import com.example.myShop.dto.ProductDTO;
import com.example.myShop.entity.Image;
import com.example.myShop.entity.Product;
import com.example.myShop.repository.ImageRepository;
import com.example.myShop.service.ImageService;
import com.example.myShop.service.MapperService;
import com.example.myShop.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html";
    }

    @GetMapping("/product")
    public String allProducts() {
        return "index.html";
    }

    @GetMapping("/search")
    public String searchProduct() {
        return "search.html";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2, @RequestParam MultipartFile file3, ProductDTO productDTO, RedirectAttributes redirectAttributes) throws IOException {
        try {
            productsService.createProduct(productDTO,file1, file2, file3);
            return "redirect:/";
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("emptyFieldsProduct", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/product/id")
    public String getProduct(@RequestParam(value = "id") Integer id, Model model) {
        ProductDTO product = productsService.getProductById(id);
        model.addAttribute("product", productsService.getProductById(id));
        model.addAttribute("images", product.images());
        return "product-info";
    }

    @GetMapping("/product/")
    public String getProductByName(@RequestParam(value = "name") String name, Model model, RedirectAttributes redirectAttributes){
        try {
            ProductDTO product = productsService.getProductByName(name);
            model.addAttribute("product", productsService.getProductByName(name));
            model.addAttribute("images", product.images());
            return "product-info";
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("notProduct", e.getMessage());
            return "redirect:/search";
        }
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productsService.deleteProduct(id);
    }

}
