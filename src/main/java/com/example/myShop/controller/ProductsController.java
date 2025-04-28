package com.example.myShop.controller;

import com.example.myShop.dto.ProductDTO;
import com.example.myShop.entity.Image;
import com.example.myShop.entity.Product;
import com.example.myShop.repository.ImageRepository;
import com.example.myShop.service.ImageService;
import com.example.myShop.service.MapperService;
import com.example.myShop.service.ProductsService;
import com.example.myShop.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final RedisService redisService;

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
    public String createProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes) throws IOException {
        try {
            if (productDTO.images().size() > 3){
                redirectAttributes.addFlashAttribute("muchImg", "You can download maximum 3 images.");
                System.out.println("User trying download more 3 images");
                return "redirect:/";
            }
            productsService.createProduct(productDTO);
            return "redirect:/";
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("emptyFieldsProduct", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/product/")
    public String getProductByName(@RequestParam(value = "name") String name, Model model, RedirectAttributes redirectAttributes){
        try {
            List<Product> product = productsService.getProductsByName(name);
            product.forEach(img -> {
                model.addAttribute("imageFromRedis", redisService.getFromRedis(img.getName()));
            });
            model.addAttribute("product", product);
            return "product-preview";
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("notProduct", e.getMessage());
            return "redirect:/search";
        }
    }

    @GetMapping("/product/info/{id}")
    public String getProductInfo(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try {
            Product product = productsService.getProduct(id);
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImages());
            return "product-info";
        }catch (NullPointerException e){
            redirectAttributes.addFlashAttribute("notProduct", e.getMessage());
            redirectAttributes.addFlashAttribute("notImage", "No images");
            return "redirect:/search";
        }
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productsService.deleteProduct(id);
    }

}
